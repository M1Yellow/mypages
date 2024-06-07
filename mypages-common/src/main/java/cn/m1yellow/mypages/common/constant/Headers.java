package cn.m1yellow.mypages.common.constant;

/**
 * 请求、响应头部属性封装枚举
 * 各个属性详细信息，可以参考：https://blog.csdn.net/java_green_hand0909/article/details/78740765
 */
public enum Headers {

    /** 允许跨域的访问源 */
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-control-Allow-Origin", "*"),
    // 固定 ip 访问源没有验证，可能写法错误
    //ACCESS_CONTROL_ALLOW_ORIGIN("Access-control-Allow-Origin", "127.0.0.1, 192.168.3.151, 192.168.3.100, 8.129.220.131, 172.23.199.172"),
    /** 允许跨越发送 cookie */
    ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials", "true"),
    /** 允许访问方法 */
    ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods", "GET, POST, OPTIONS"),
    /** 允许带的请求头属性字段 */
    //ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers", "accept,x-requested-with,Content-Type"),
    /** 允许请求访问的有效时间 */
    ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age", "600"),
    /** 缓存控制，不使用缓存。Cache-Control: no-cache, no-store, max-age=0, must-revalidate */
    CACHE_CONTROL_NOT_ALLOW_CACHED("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate"),
    /** 缓存控制，使用自定义缓存 */
    CACHE_CONTROL_ALLOW_CACHED("Cache-Control", "public, max-age=600"),
    CACHE_CONTROL_PUBLIC("Cache-Control", "public"),
    /** 冗余字段，非请求头属性，自定义的默认缓存时间，单位：秒。后续在数据库配置表 sys_config 配置 cache_control_max_age */
    CACHE_CONTROL_MAX_AGE("max-age", "86400"), // 一天
    /** 用于指定无缓存 */
    PRAGMA("Pragma", "no-cache"),
    ;

    private String headName;
    private String headValues;

    Headers(String headName, String headValues) {
        this.headName = headName;
        this.headValues = headValues;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadValues() {
        return headValues;
    }

    public void setHeadValues(String headValues) {
        this.headValues = headValues;
    }
}
