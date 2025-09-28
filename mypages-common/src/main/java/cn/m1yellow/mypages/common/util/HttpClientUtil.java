package cn.m1yellow.mypages.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
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
     * 字节流数组大小（1MB）
     */
    public final static int BYTE_ARRAY_LENGTH = 1024 * 1024;
    /**
     * 连接池最大连接数量，默认20，根据测试、生产环境的业务量，适当扩大
     */
    public static final int DEFAULT_POOL_MAX_TOTAL = 1000;
    /**
     * 每个路由（ip+port）最大连接数量，默认2，根据测试、生产环境的业务量，适当扩大
     */
    public static final int DEFAULT_POOL_MAX_PER_ROUTE = 100;
    /**
     * tcp connect的超时时间，单位：毫秒
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    /**
     * tcp 建立连接后，从请求网页获取响应内容超时时间，单位：毫秒
     */
    public static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    /**
     * 从连接池获取连接的超时时间，就是连接池只有10个连接，来了12个请求，多的两个要等待的时间，单位：毫秒
     */
    public static final int DEFAULT_CONNECT_REQUEST_TIMEOUT = 10000;
    /**
     * 重试次数，默认3
     */
    public static final int DEFAULT_MAX_RETRIES = -1;
    /**
     * 清理闲置连接时间间隔，单位：毫秒
     * 业务量大的，可以减小清理周期
     */
    public static final int DEFAULT_CLEAN_IDLE_CONN_TIME = 5 * 60 * 1000;
    /**
     * 指定清理连接空闲时间（就是这个连接空闲了多久会被清理），单位：毫秒
     * 如果请求路由（域名+端口）不多，固定几个，闲置时间可以长一点，如果大都是随机路由，则配置小一点
     */
    public static final int DEFAULT_CONN_IDLE_TIME = 30 * 60 * 1000;
    /**
     * 请求头参数
     */
    public static final Map<String, String> HEADERS = new HashMap<>();
    /**
     * 自定义 HttpClients 构建器
     */
    private static final HttpClientBuilder httpClientBuilder = HttpClients.custom();
    /**
     * 全局唯一连接管理器
     */
    private static final PoolingHttpClientConnectionManager CONN_MANAGER;
    /**
     * 闲置连接监控线程
     */
    private static HttpClientUtil.IdleConnectionMonitorThread idleThread = null;
    /**
     * 整个项目全局共用一个 httpClient
     */
    public static final CloseableHttpClient HTTPCLIENT;


    // 静态代码块初始化配置
    static {
        // 绕过不安全的 https 请求的证书认证
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                //.register("https", SSLConnectionSocketFactory.getSocketFactory())
                .register("https", trustHttpsCertificates()) // 自定义
                .build();

        // 创建连接池
        CONN_MANAGER = new PoolingHttpClientConnectionManager(registry);
        CONN_MANAGER.setMaxTotal(DEFAULT_POOL_MAX_TOTAL);
        CONN_MANAGER.setDefaultMaxPerRoute(DEFAULT_POOL_MAX_PER_ROUTE);
        httpClientBuilder.setConnectionManager(CONN_MANAGER);

        // 设置请求默认配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)                     // 设置连接超时
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)                       // 设置读取超时
                .setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)   // 设置从连接池获取连接实例的超时
                .setMaxRedirects(DEFAULT_MAX_RETRIES)                           // 设置默认重试次数
                .build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        // 设置默认 header 参数
        List<Header> defaultHeaders = new ArrayList<>();
        BasicHeader userAgentHeader = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36");
        defaultHeaders.add(userAgentHeader);
        httpClientBuilder.setDefaultHeaders(defaultHeaders);

        // 多个 client 共享配置，全局就一个 ConnectionManager，可以避免资源重复
        httpClientBuilder.setConnectionManagerShared(true);

        // 创建全局 httpClient
        HTTPCLIENT = httpClientBuilder.build();

        // TODO 启动闲置连接管理线程。内部是 while 循环，还是比较耗费性能的。可以在使用 getxxx、postxxx方法之前进行连接释放
        //idleThread = new IdleConnectionMonitorThread(cm);
        //idleThread.start();

        // 初始化请求头参数
        //HEADERS.put("Content-Type", "application/json;charset=UTF-8");
        HEADERS.put("User-Agent", HeaderUtil.getOneHeaderRandom());

    }


    public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("test1", 1111);
        params.put("test2", "hhhh");
        String url = "https://m.weibo.cn/api/container/getIndex?type=uid&value=5873597305&containerid=1005055873597305";
        url = "https://weibo.com/ajax/profile/info?uid=2846104240";
        HEADERS.put("Referer", "https://weibo.com/");
        HEADERS.put("Cookie", "XSRF-TOKEN=vIAWGR9TrFeKd_p3n94afTD3; SUB=_2AkMfjl_Qf8NxqwFRmvsQzWzmbIlywgnEieKp0q4LJRMxHRl-yT9yqkMFtRB6NA5xPyN_FCKYgJOEMBFY-418yQuWKBu0; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WFmb98cJhraZckPik_hObi9; WBPSESS=VFkNPEb725ruVm0iDC4EQKtmuEb9BDZo4Vs9Xp8VJ_mRdPqK2C_bfR-3eJWw27UmnULjJxmLGvM9nkuf2MISn_92bO-ez2LUCUHTIxA5K-bLmqZOeXPbxVi1GO0X2yHlBa2-ysZNNoyGPutHeoJt32sc2OylA49Mcs6bPQgf3_s=");
        //url = "https://api.bilibili.com/x/web-interface/card?mid=8366990";
        //HEADERS.put("Referer", "https://www.bilibili.com/");
        //HEADERS.put("Cookie", "");
        String result = doGet(url, params);
        System.out.println(result);
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
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                // 信任所有证书
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            // 绕过主机名验证
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            return new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        } catch (Exception e) {
            log.error("创建安全连接工厂失败: {}", e.getMessage());
            throw new RuntimeException("创建安全连接工厂失败");
        }
    }


    /**
     * 获取一个新的 httpClient，注意，每次执行完单个或批量请求之后，记得关闭 httpClient，
     * 调用 HttpClientUtil.shutdown(httpClient)
     * TODO 建议项目使用一个 httpClient 单例即可，httpClient 是线程安全的。
     * 官网介绍：https://hc.apache.org/httpcomponents-client-4.5.x/current/tutorial/html/fundamentals.html#d5e213
     */
    public static CloseableHttpClient getHttpClient() {
        //return httpClientBuilder.build();
        return HTTPCLIENT;
    }


    /**
     * 项目使用非单例 httpClient，需要关闭 httpClient。
     * 这种写法比较 ugly，可以考虑自己封装一个像 OSSClient 的 HttpClient
     */
    public static void shutdown(CloseableHttpClient httpClient) {
        if (null != httpClient) {
            try {
                httpClient.close();
            } catch (Exception e) {
                log.error(">>>> httpClient close error: {}", e.getMessage());
            }
        }
        if (null != idleThread) {
            idleThread.shutdown();
        }
    }


    /**
     * 闲置连接监控管理线程类
     * 连接管理线程源码：https://hc.apache.org/httpcomponents-client-4.5.x/current/tutorial/html/connmgmt.html#d5e418
     */
    private static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean exitFlag = false;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super("Idle-Conn-Clean");
            this.connMgr = connMgr;
            setDaemon(true);
        }

        @Override
        public void run() {
            log.debug(">>>> IdleConnectionMonitorThread is running...");
            try {
                while (!exitFlag) {
                    synchronized (this) {
                        // wait 不会释放对象监视锁，sleep 会释放
                        wait(DEFAULT_CLEAN_IDLE_CONN_TIME);
                        // 关闭失效的连接
                        connMgr.closeExpiredConnections();
                        // 可选的，关闭指定时间内空闲（不活动的）连接
                        connMgr.closeIdleConnections(DEFAULT_CONN_IDLE_TIME, TimeUnit.MILLISECONDS);
                    }
                }
            } catch (Exception e) {
                log.error(">>>> IdleConnectionMonitorThread clean connection error: {}", e.getMessage());
            }
            log.debug(">>>> IdleConnectionMonitorThread stopped.");
        }

        public void shutdown() {
            this.exitFlag = true;
            synchronized (this) {
                notifyAll();
            }
        }

    }


    /**
     * 手动清理闲置连接方法。
     * TODO 确保每次调用 httpClient 的 execute 方法之前，先执行，就不用单独启用一个线程循环清理了
     */
    private static void idleConnHandler() {
        if (CONN_MANAGER.getTotalStats().getMax() >= 0) {
            // 用于测试关闭 connection manager
            //return;
        }
        // 关闭失效的连接，方法内有 debug 日志，不用再打印
        CONN_MANAGER.closeExpiredConnections();
        // 可选的，关闭指定时间内空闲（不活动的）连接
        CONN_MANAGER.closeIdleConnections(DEFAULT_CONN_IDLE_TIME, TimeUnit.MILLISECONDS);
    }


    public static String doGet(String url) {
        return doGet(url, HEADERS, null);
    }

    public static String doGet(String url, Map<String, Object> params) {
        return doGet(url, HEADERS, params);
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
        // 执行方法之前，手动执行清理闲置连接
        idleConnHandler();

        // 从 HttpClients 构建器获取可关闭的 HttpClients 实例
        //CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        // 是否需要拼接get参数，1-需要
        int addFlag = 0;
        // 设置 header 信息
        if (null != params) {
            if (params.containsKey("Cookie")) {
                String cookie = ObjectUtil.getString(params.get("Cookie"));
                params.remove("Cookie");
                headers.put("Cookie", cookie);
            }
            if (params.containsKey("Referer")) {
                String referer = ObjectUtil.getString(params.get("Referer"));
                params.remove("Referer");
                headers.put("Referer", referer);
            }
            if (params.containsKey("addFlag")) {
                addFlag = (int) params.get("addFlag");
            }
        }

        // 构建请求头
        String apiUrl = url;
        if (1 == addFlag) apiUrl = getUrlWithParams(url, params);
        log.info(">>>> doGet url: {}", apiUrl);
        HttpGet httpGet = new HttpGet(apiUrl);

        if (null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
            log.info(">>>> doGet headers: {}", Arrays.toString(httpGet.getAllHeaders()));
        }

        // 处理响应
        HttpEntity entity = null;
        try (CloseableHttpResponse response = HTTPCLIENT.execute(httpGet)) {
            if (null == response || null == response.getStatusLine()) {
                return null;
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
            } else {
                log.error(">>>> doGet failure, url: {}, code: {}", url, statusCode);
            }
        } catch (Exception e) {
            log.error(">>>> doGet error, url: {}, e: {}", url, e.getMessage());
        } finally {
            try {
                // 关闭实体，HttpEntity 关闭资源
                // 其实上面的 EntityUtils.toString 内部已经关闭了流，重复写是因为，如果上面不用 EntityUtils.toString
                EntityUtils.consume(entity);
            } catch (Exception e) {
                log.error(">>>> doGet entity close error: {}", e.getMessage());
            }
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
        // 执行方法之前，手动执行清理闲置连接
        idleConnHandler();

        // 从 HttpClients 构建器获取可关闭的 HttpClients 实例
        //CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        // 构建请求头
        HttpPost httpPost = new HttpPost(apiUrl);

        // 配置请求headers
        if (null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 配置请求参数
        if (null != params && !params.isEmpty()) {
            HttpEntity entityReq = getUrlEncodedFormEntity(params);
            httpPost.setEntity(entityReq);
        }

        // 处理响应
        HttpEntity entity = null;
        try (CloseableHttpResponse response = HTTPCLIENT.execute(httpPost)) {
            if (null == response || null == response.getStatusLine()) {
                return null;
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                entity = response.getEntity();
                if (null != entity) {
                    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
            } else {
                log.error(">>>> doPost failure, url: {}, code: {}", apiUrl, statusCode);
            }
        } catch (Exception e) {
            log.error(">>>> doPost error, url: {}, e: {}", apiUrl, e.getMessage());
        } finally {
            try {
                // 关闭实体，HttpEntity 关闭资源
                EntityUtils.consume(entity);
            } catch (Exception e) {
                log.error(">>>> doPost entity close error: {}", e.getMessage());
            }
        }
        return null;

    }


    /**
     * 执行post请求获取响应（请求体为JOSN数据）
     *
     * @param url  请求地址
     * @param json 请求的JSON数据
     * @return 响应内容
     */
    public static String postJson(String url, String json) {
        return postJson(url, null, json);
    }


    /**
     * 执行post请求获取响应（请求体为JOSN数据）
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @param json    请求的JSON数据
     * @return 响应内容
     */
    public static String postJson(String url, Map<String, String> headers, String json) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json");
        post.setEntity(new StringEntity(json, DEFAULT_CHARSET));
        return getRespString(post, headers);
    }

    /**
     * 执行post请求获取响应（请求体包含文件）
     *
     * @param url    请求地址
     * @param params 请求参数（文件对应的value传File对象）
     * @return 响应内容
     */
    public static String postFile(String url, Map<String, Object> params) {
        return postFile(url, null, params);
    }

    /**
     * 执行post请求获取响应（请求体包含文件）
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @param params  请求参数（文件对应的value传File对象）
     * @return 响应内容
     */
    public static String postFile(String url, Map<String, String> headers, Map<String, Object> params) {
        HttpPost post = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (Objects.nonNull(params) && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (Objects.isNull(value)) {
                    builder.addPart(key, new StringBody("", ContentType.TEXT_PLAIN));
                } else {
                    if (value instanceof File) {
                        builder.addPart(key, new FileBody((File) value));
                    } else {
                        builder.addPart(key, new StringBody(value.toString(), ContentType.TEXT_PLAIN));
                    }
                }
            }
        }
        HttpEntity entity = builder.build();
        post.setEntity(entity);
        return getRespString(post, headers);
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param path     保存路径（如：D:/images，不传默认当前工程根目录）
     * @param fileName 文件名称（如：hello.jpg）
     */
    public static void download(String url, String path, String fileName) {
        HttpGet get = new HttpGet(url);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = null;
        if (Objects.isNull(path) || path.isEmpty()) {
            filePath = fileName;
        } else {
            if (path.endsWith("/")) {
                filePath = path + fileName;
            } else {
                filePath += path + "/" + fileName;
            }
        }
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error("download createNewFile error: {}", e.getMessage());
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file); InputStream in = getRespInputStream(get, null)) {
            if (Objects.isNull(in)) {
                return;
            }
            byte[] bytes = new byte[BYTE_ARRAY_LENGTH];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (Exception e) {
            log.error("download save file error: {}", e.getMessage());
        }
    }


    /**
     * 执行请求，获取响应流
     *
     * @param request 请求对象
     * @return 响应内容
     */
    private static InputStream getRespInputStream(HttpUriRequest request, Map<String, String> headers) {
        // 设置请求头
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            // 请求头不为空，则设置对应请求头
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        // 获取响应对象
        HttpResponse response = null;
        try {
            // 执行方法之前，手动执行清理闲置连接
            idleConnHandler();
            response = HTTPCLIENT.execute(request);
        } catch (Exception e) {
            log.error("getRespInputStream create default httpClient error: {}", e.getMessage());
            return null;
        }
        // 获取Entity对象
        HttpEntity entity = response.getEntity();
        // 获取响应信息流【外层方法有关闭流，不用担心】
        InputStream in = null;
        if (Objects.nonNull(entity)) {
            try {
                in = entity.getContent();
            } catch (Exception e) {
                log.error("getRespInputStream entity.getContent() error: {}", e.getMessage());
            }
        }
        return in;
    }


    /**
     * 执行请求，获取响应内容
     *
     * @param request 请求对象
     * @return 响应内容
     */
    private static String getRespString(HttpUriRequest request, Map<String, String> headers) {
        byte[] bytes = new byte[BYTE_ARRAY_LENGTH];
        int len = 0;
        try (InputStream in = getRespInputStream(request, headers);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            if (Objects.isNull(in)) {
                return "";
            }
            while ((len = in.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            return bos.toString(DEFAULT_CHARSET);
        } catch (Exception e) {
            log.error("getRespString error: {}", e.getMessage());
        }
        return "";
    }


    /**
     * 请求参数编码
     *
     * @param params
     * @return
     */
    private static HttpEntity getUrlEncodedFormEntity(Map<String, Object> params) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                    .getValue().toString());
            pairList.add(pair);
        }
        return new UrlEncodedFormEntity(pairList, StandardCharsets.UTF_8);
    }


    /**
     * get 请求拼接参数
     *
     * @param url
     * @param params
     * @return
     */
    private static String getUrlWithParams(String url, Map<String, Object> params) {
        if (null == params || params.size() < 1) return url;
        boolean first = !url.contains("?"); // 别太相信网上的代码！有的连最基本的验证可能都过不了
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
                log.error(">>>> getUrlWithParams url params encode error: {}", e.getMessage());
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
        // 执行方法之前，手动执行清理闲置连接
        idleConnHandler();

        //CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
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
        try (CloseableHttpResponse response = HTTPCLIENT.execute(get)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                entity = response.getEntity();
                if (null != entity) {
                    // TODO 重复使用 response entity
                    entity = new BufferedHttpEntity(entity);

                    // TODO 校验 MD5
                    String newFileMd5 = DigestUtils.md5Hex(entity.getContent());
                    if (StringUtils.isNotBlank(newFileMd5) && StringUtils.isNotBlank(originalFileMd5)
                            && newFileMd5.equals(originalFileMd5)) {
                        // 文件 MD5 相同，直接返回，不再继续下载
                        log.debug(">>>> 头像文件MD5相同，不再重复下载。newFileMd5 = originalFileMd5: " + newFileMd5);
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
                        log.debug(">>>> 用户头像下载完毕：" + file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            log.error(">>>> singleFileDownload response error: {}", e.getMessage());
        } finally {
            try {
                // 关闭实体，HttpEntity 关闭资源
                EntityUtils.consume(entity);
            } catch (Exception e) {
                log.error(">>>> singleFileDownload entity close error: {}", e.getMessage());
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
            con.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
        } catch (Exception e) {
            log.error(">>>> singleFileDownloadByNet create connection error: {}", e.getMessage());
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
            log.error(">>>> singleFileDownloadByNet save file error: {}", e.getMessage());
        }
    }

}
