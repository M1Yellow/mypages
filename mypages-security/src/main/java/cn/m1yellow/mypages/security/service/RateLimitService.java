package cn.m1yellow.mypages.security.service;

import cn.m1yellow.mypages.common.aspect.RateLimit;

public interface RateLimitService {

    /**
     * limit 校验是否通过
     *
     * @param ip        访问IP
     * @param uri       访问路径
     * @param rateLimit RateLimit 参数配置
     * @return boolean
     */
    boolean limit(String ip, String uri, RateLimit rateLimit);

}
