package cn.m1yellow.mypages.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * HttpClient 封装工具类，使用了连接池管理
 * <br>
 * 为什么使用连接池？
 * 连接池和线程池优点类似，连接主要是创建、销毁会消耗资源和时间，及tcp创建需要经过三次握手，销毁需要经过四次挥手。消耗的资源主要包括CPU、内存
 * 需要避免创建和销毁是针对同一个路由（域名+端口）的，连接池是每个host的连接池。使用连接池能避免和同一个路由频繁建立和销毁连接。
 * <br>
 * 什么时候需要使用httpclient连接池？
 * 请求路由基本不会，且请求量大的场景，比如，抓取网页数据
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 默认字符编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 连接池最大连接数量，默认20，按需扩大
     */
    public static final int DEFAULT_POOL_MAX_TOTAL = 1000;
    /**
     * 每个路由（ip+port）最大连接数量，默认2
     */
    public static final int DEFAULT_POOL_MAX_PER_ROUTE = 100;
    /**
     * tcp connect的超时时间，单位：毫秒
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 20000;
    /**
     * tcp io的读写超时时间，也就是从请求网页获取响应内容超时时间，单位：毫秒
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 20000;
    /**
     * 从连接池获取连接的超时时间，单位：毫秒
     */
    public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 12000;
    /**
     * 清理闲置连接时间间隔，单位：毫秒
     * 业务量大的，可以减小清理周期
     */
    public static final int DEFAULT_CLEAN_IDLE_CONN_TIME = 5 * 60 * 1000;
    /**
     * 指定清理连接空闲时间（就是这个连接空闲了多久会被清理），单位：毫秒
     * 如果请求路由（域名+端口）不多，固定几个，闲置时间可以长一点，如果大都是随机路由，则配置小一点
     */
    public static final int DEFAULT_CONN_IDLE_TIME = 5 * 60 * 1000;


    /**
     * 自定义 HttpClients 构建器
     */
    private static final HttpClientBuilder httpClientBuilder = HttpClients.custom();

    /**
     * 闲置连接监控线程
     */
    private static HttpClientUtil.IdleConnectionMonitorThread idleThread = null;


    // 静态代码块初始化配置
    static {
        // 绕过不安全的 https 请求的证书认证
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                //.register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("https", trustHttpsCertificates()) // 自定义
                .build();

        // 创建连接池
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(DEFAULT_POOL_MAX_TOTAL);
        cm.setDefaultMaxPerRoute(DEFAULT_POOL_MAX_PER_ROUTE);
        httpClientBuilder.setConnectionManager(cm);

        // 设置请求默认配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)                     // 设置连接超时
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)                       // 设置读取超时
                .setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)   // 设置从连接池获取连接实例的超时
                .build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        // 设置默认 header 参数
        List<Header> defaultHeaders = new ArrayList<>();
        BasicHeader userAgentHeader = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36");
        defaultHeaders.add(userAgentHeader);
        httpClientBuilder.setDefaultHeaders(defaultHeaders);

        // 多个 client 共享配置，有助于多线程稳定执行
        httpClientBuilder.setConnectionManagerShared(true);

        // 启动闲置连接管理线程
        idleThread = new IdleConnectionMonitorThread(cm);
        idleThread.start();

    }


    /**
     * 创建安全连接工厂<br>
     * Https请求验证证书失败解决方法！(信任所有证书)
     * 报错：unable to find valid certification path to requested target
     * 解决办法：
     * 1、导入证书到本地证书库
     * 2、信任所有SSL证书
     * <p>
     * 最好的解决办法或许是信任所有SSL证书，因为某些时候不能每次都手动的导入证书非常麻烦。现在封装了个方法，在连接openConnection的时候忽略掉证书就行了。
     *
     * @return SSLConnectionSocketFactory
     */
    private static ConnectionSocketFactory trustHttpsCertificates() {
        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
                // 判断是否信任 url
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            });
            SSLContext sslContext = sslContextBuilder.build();
            return new SSLConnectionSocketFactory(sslContext,
                    new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"},
                    null, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
            log.error("创建安全连接工厂失败: {}", e.getMessage());
            throw new RuntimeException("创建安全连接工厂失败");
        }
    }


    /**
     * 关闭闲置连接管理线程
     */
    public static void shutdown() {
        idleThread.shutdown();
    }


    /**
     * 闲置连接监控管理线程类
     */
    private static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean exitFlag = false;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            this.connMgr = connMgr;
            setDaemon(true);
        }

        @Override
        public void run() {
            log.info(">>>> IdleConnectionMonitorThread is running...");
            try {
                while (!exitFlag) {
                    synchronized (this) {
                        wait(DEFAULT_CLEAN_IDLE_CONN_TIME);
                        // 关闭失效的连接
                        connMgr.closeExpiredConnections();
                        // 可选的, 关闭指定时间内空闲（不活动的）连接
                        connMgr.closeIdleConnections(DEFAULT_CONN_IDLE_TIME, TimeUnit.MILLISECONDS);
                    }
                }
            } catch (Exception e) {
                log.error(">>>> IdleConnectionMonitorThread clean connection error: {}", e.getMessage());
            }
            log.info(">>>> IdleConnectionMonitorThread stopped.");
        }

        public void shutdown() {
            this.exitFlag = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }


    public static String doGet(String url) {
        return doGet(url, Collections.EMPTY_MAP, Collections.EMPTY_MAP);
    }

    public static String doGet(String url, Map<String, Object> params) {
        return doGet(url, Collections.EMPTY_MAP, params);
    }


    /**
     * 发送 get 请求
     *
     * @param url     请求地址，特殊字符参数须经过 URLEncode 编码处理
     * @param headers 自定义请求头
     * @param params  请求参数
     * @return 请求响应结果
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, Object> params) {

        // 从 HttpClients 构建器获取可关闭的 HttpClients 实例
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        // 构建请求头
        String apiUrl = getUrlWithParams(url, params);
        HttpGet httpGet = new HttpGet(apiUrl);

        // 设置 header 信息
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }


        // 可关闭的响应
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpGet);
            if (response == null || response.getStatusLine() == null) {
                return null;
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entityRes = response.getEntity();
                if (entityRes != null) {
                    return EntityUtils.toString(entityRes, StandardCharsets.UTF_8);
                }
            } else {
                log.error(">>>> doGet failure, url: {}, code: {}", url, statusCode);
            }
        } catch (Exception e) {
            log.error(">>>> doGet error, url: {}, e: {}", url, e.getMessage());
        } finally {
            // 关闭响应资源
            HttpClientUtils.closeQuietly(response);
        }
        return null;
    }

    public static String doPost(String apiUrl, Map<String, Object> params) {
        return doPost(apiUrl, Collections.EMPTY_MAP, params);
    }

    /**
     * 发送 post 请求
     *
     * @param apiUrl  请求地址
     * @param headers 请求头参数
     * @param params  请求参数
     * @return 请求响应结果
     */
    public static String doPost(String apiUrl, Map<String, String> headers, Map<String, Object> params) {

        // 从 HttpClients 构建器获取可关闭的 HttpClients 实例
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        // 构建请求头
        HttpPost httpPost = new HttpPost(apiUrl);

        // 配置请求headers
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 配置请求参数
        if (params != null && params.size() > 0) {
            HttpEntity entityReq = getUrlEncodedFormEntity(params);
            httpPost.setEntity(entityReq);
        }


        // 可关闭的响应
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpPost);
            if (response == null || response.getStatusLine() == null) {
                return null;
            }

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entityRes = response.getEntity();
                if (entityRes != null) {
                    return EntityUtils.toString(entityRes, StandardCharsets.UTF_8);
                }
            } else {
                log.error(">>>> doPost failure, url: {}, code: {}", apiUrl, statusCode);
            }
        } catch (Exception e) {
            log.error(">>>> doPost error, url: {}, e: {}", apiUrl, e.getMessage());
        } finally {
            // 关闭响应资源
            HttpClientUtils.closeQuietly(response);
        }
        return null;

    }

    private static HttpEntity getUrlEncodedFormEntity(Map<String, Object> params) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
        return new UrlEncodedFormEntity(pairList, StandardCharsets.UTF_8);
    }

    private static String getUrlWithParams(String url, Map<String, Object> params) {
        boolean first = true;
        StringBuilder sb = new StringBuilder(url);
        for (String key : params.keySet()) {
            char ch = '&';
            if (first == true) {
                ch = '?';
                first = false;
            }
            String value = params.get(key).toString();
            try {
                // TODO 对请求 url 中的特殊字符进行转码
                String sval = URLEncoder.encode(value, DEFAULT_CHARSET);
                sb.append(ch).append(key).append("=").append(sval);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    /**
     * 单文件下载（httpclient 方式）
     *
     * @param fileUrl  文件下载地址
     * @param fileName 文件名称
     * @param saveDir  保存路径目录
     * @param params   其他参数
     */
    public static void singleFileDownload(String fileUrl, String fileName, String saveDir, Map<String, Object> params) {

        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet get = new HttpGet(fileUrl);

        String userAgent = ObjectUtil.getString(params.get("userAgent"));
        String referer = ObjectUtil.getString(params.get("referer"));
        String originalFileMd5 = ObjectUtil.getString(params.get("originalFileMd5"));

        // 设置请求头
        get.setHeader("User-Agent", userAgent);
        get.setHeader("referer", referer);

        /**
         * response.getEntity().getContent() 返回实体的内容流。
         * 每次此方法的调用，可重复的实体都将创建一个InputStream的新实例，因此可以多次使用。
         * 不可重复的实体应返回相同的InputStream实例，因此消耗的次数不得超过一次。
         * HttpEntity 的 isRepeatable 为 false，不可重复，导致内容流使用一次之后就关闭了！
         */

        HttpEntity entity = null;
        try (CloseableHttpResponse response = closeableHttpClient.execute(get)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                entity = response.getEntity();
                if (entity != null) {
                    // TODO 重复使用 response entity
                    entity = new BufferedHttpEntity(entity);

                    // TODO 校验 MD5
                    String newFileMd5 = DigestUtils.md5Hex(entity.getContent());
                    if (StringUtils.isNotBlank(newFileMd5) && StringUtils.isNotBlank(originalFileMd5)
                            && newFileMd5.equals(originalFileMd5)) {
                        // 文件 MD5 相同，直接返回，不再继续下载
                        log.info(">>>> 头像文件MD5相同，不再重复下载。newFileMd5 = originalFileMd5: " + newFileMd5);
                        return;
                    }

                    File filePath = new File(saveDir);
                    File file = new File(saveDir, fileName);

                    if (!filePath.exists()) {
                        filePath.mkdirs();
                        //创建文件
                        //file.createNewFile();
                    }

                    try (
                            OutputStream out = new BufferedOutputStream(new FileOutputStream(file))
                    ) {
                        // 写入本地文件
                        entity.writeTo(out);
                        log.info(">>>> 用户头像下载完毕：" + file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭实体，HttpEntity 关闭资源
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 单文件下载（Java net 方式）
     *
     * @param fileUrl
     * @param fileName
     * @param saveDir
     * @param params
     */
    public static void singleFileDownloadByNet(String fileUrl, String fileName, String saveDir, Map<String, Object> params) {

        // 构造URL
        URL url = null;
        // 打开连接
        URLConnection con = null;

        try {
            url = new URL(fileUrl);
            con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 输出的文件流
        File sf = new File(saveDir);
        if (!sf.exists()) {
            sf.mkdirs();
        }

        try (
                // 输入流
                InputStream is = con.getInputStream();
                OutputStream os = new FileOutputStream(sf.getPath() + "\\" + fileName);
        ) {

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取网页
     *
     * @param url
     * @param userAgent
     * @return
     */
    public static String getHtml(String url, String userAgent) {

        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", userAgent);
        get.setHeader("referer", url);

        String html = null;
        HttpEntity entity = null;
        try (CloseableHttpResponse response = closeableHttpClient.execute(get)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity, DEFAULT_CHARSET);
                }
            } else {
                log.debug(">>>> getHtml statusCode: {}", statusCode);
            }
        } catch (Exception e) {
            log.error(">>>> getHtml error: {}", e.getMessage());
        } finally {
            try {
                EntityUtils.consume(entity);
            } catch (Exception e) {
                log.error(">>>> getHtml release entity error: {}", e.getMessage());
            }
        }

        return html;
    }


}
