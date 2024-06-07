package cn.m1yellow.mypages.common.constant;

/**
 * 缓存类型枚举，用来根据不同的类型，设置不同的缓存时间
 */
public enum CacheType {

    CACHE_2MIN("CACHE_2MIN", 2*60L),
    CACHE_10MIN("CACHE_10MIN", 10*60L),
    CACHE_30MIN("CACHE_30MIN", 30*60L),
    CACHE_2HOURS("CACHE_2HOURS", 2*60*60L),
    CACHE_12HOURS("CACHE_12HOURS", 12*60*60L),
    CACHE_24HOURS("CACHE_24HOURS", 24*60*60L),
    CACHE_2DAYS("CACHE_2DAYS", 2*24*60*60L),
    CACHE_7DAYS("CACHE_7DAYS", 7*24*60*60L),
    CACHE_15DAYS("CACHE_15DAYS", 15*24*60*60L),
    CACHE_30DAYS("CACHE_15DAYS", 30*24*60*60L),
    /** 永久缓存 */
    CACHE_ETERNAL("CACHE_ETERNAL", 0L),
    CACHE_USER_FOLLOWING_2HOURS("CACHE_USER_FOLLOWING_2HOURS", 2*60*60L),
    CACHE_USER_FOLLOWING("CACHE_USER_FOLLOWING", 24*60*60L),
    ;

    private String name;
    private Long ttl;

    CacheType(String name, Long ttl) {
        this.name = name;
        this.ttl = ttl;
    }

    public static Long getTtlByName(String name) {
        for (CacheType type : CacheType.values()) {
            if (type.name.equals(name)) {
                return type.ttl;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }
}
