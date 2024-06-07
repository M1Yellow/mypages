package cn.m1yellow.mypages.common.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.DefaultServiceClient;
import com.aliyun.oss.common.comm.ServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "aliyun.oss.enable", havingValue = "1") // 如果缺少 aliyun.oss.enable 配置项，则不会初始化这个类实例
public class OssConfig {

    @Value("${aliyun.oss.endpoint}")
    private String ALIYUN_OSS_ENDPOINT;
    @Value("${aliyun.oss.accessKeyId}")
    private String ALIYUN_OSS_ACCESSKEYID;
    @Value("${aliyun.oss.accessKeySecret}")
    private String ALIYUN_OSS_ACCESSKEYSECRET;


    @Bean
    public CredentialsProvider defaultCredentialProvider() {
        return new DefaultCredentialProvider(ALIYUN_OSS_ACCESSKEYID, ALIYUN_OSS_ACCESSKEYSECRET);
    }


    @Bean
    public ClientBuilderConfiguration clientBuilderConfiguration() {
        // 创建ClientConfiguration。ClientConfiguration是OSSClient的配置类，可配置代理、连接超时、最大连接数等参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();

        // 设置OSSClient允许打开的最大HTTP连接数，默认为1024个。
        //conf.setMaxConnections(500);
        // 设置Socket层传输数据的超时时间，默认为50000毫秒。
        conf.setSocketTimeout(20000);
        // 设置建立连接的超时时间，默认为50000毫秒。
        conf.setConnectionTimeout(20000);
        // 设置从连接池中获取连接的超时时间（单位：毫秒），默认 5 * 60 * 1000。
        //conf.setConnectionRequestTimeout(2000);
        // 设置连接空闲超时时间。超时则关闭连接，默认为60000毫秒。
        conf.setIdleConnectionTime(30 * 60 * 1000);
        // 设置失败请求重试次数，默认为3次。
        conf.setMaxErrorRetry(-1);
        // TODO 关闭闲置连接清理线程，改为手动，在执行请求的方法手动调用清理
        conf.setUseReaper(false);
        // 设置是否支持将自定义域名作为Endpoint，默认支持。
        // TODO 私有云一定要关闭 CNAME，才能正常认证通过！！！
        //conf.setSupportCname(false);
        // 设置是否开启二级域名的访问方式，默认不开启。
        //conf.setSLDEnabled(true);
        // 设置连接OSS所使用的协议（HTTP或HTTPS），默认为HTTP。
        //conf.setProtocol(Protocol.HTTP);
        // 设置用户代理，指HTTP的User-Agent头，默认为aliyun-sdk-java。
        //conf.setUserAgent("aliyun-sdk-java");
        // 设置代理服务器端口。
        //conf.setProxyHost("<yourProxyHost>");
        // 设置代理服务器验证的用户名。
        //conf.setProxyUsername("<yourProxyUserName>");
        // 设置代理服务器验证的密码。
        //conf.setProxyPassword("<yourProxyPassword>");
        // 设置是否开启HTTP重定向，默认开启。
        //conf.setRedirectEnable(true);
        // 设置是否开启SSL证书校验，默认开启。
        //conf.setVerifySSLEnable(true);

        return conf;
    }


    /**
     * 创建 ossClient
     * TODO
     * 注意：单例模式，调用 ossClient.shutdown() 之后，OSSClient 报错：com.aliyun.oss.ClientException: Connection error due to: Connection pool shut down
     * 需要配置多例模式，或者需要使用 ossClient 的时候，每次创建一个新的对象。
     * OSSClient 目前的实现，只能用于局部服用，就是一个方法内使用多次，全局 OSSClient 连接池式复用，貌似没找到可行案例，官方文档目前也是局部复用案例
     */
    @Bean
    //@Scope("prototype") // 多例
    public OSS ossClient() {
        // 使用默认连接配置
        //return new OSSClientBuilder().build(ALIYUN_OSS_ENDPOINT, ALIYUN_OSS_ACCESSKEYID, ALIYUN_OSS_ACCESSKEYSECRET);
        // 使用自定义连接配置
        return new OSSClientBuilder().build(ALIYUN_OSS_ENDPOINT, defaultCredentialProvider(), clientBuilderConfiguration());
    }


    /**
     * 利用发射获取 OSSClient 默认配置的 PoolingHttpClientConnectionManager，用于手动调用闲置连接清理方法
     *
     * @return
     */
    @Bean
    public PoolingHttpClientConnectionManager defaultConnManager() {
        // 从 ossClient 获取 DefaultServiceClient
        OSS ossClient = ossClient();
        Class ossClass = ossClient.getClass();
        ServiceClient serviceClient = null;
        try {
            Field serviceClientField = ossClass.getDeclaredField("serviceClient");
            serviceClientField.setAccessible(true);
            serviceClient = (DefaultServiceClient) serviceClientField.get(ossClient);
        } catch (Exception e) {
            log.error("从 ossClient 获取 DefaultServiceClient 异常：{}", e.getMessage());
        }

        if (null == serviceClient) {
            log.error("从 ossClient 获取 DefaultServiceClient 为 null");
            return null;
        }

        // 从 DefaultServiceClient 获取 connectionManager
        Class serviceClientClass = serviceClient.getClass();
        PoolingHttpClientConnectionManager connectionManager = null;
        try {
            Field connectionManagerField = serviceClientClass.getDeclaredField("connectionManager");
            connectionManagerField.setAccessible(true);
            connectionManager = (PoolingHttpClientConnectionManager) connectionManagerField.get(serviceClient);
            if (null == connectionManager) {
                log.error("从 DefaultServiceClient 获取 connectionManager 为 null");
                return null;
            }
            return connectionManager;
        } catch (Exception e) {
            log.error("从 DefaultServiceClient 获取 connectionManager 异常：{}", e.getMessage());
        }

        return null;
    }

}
