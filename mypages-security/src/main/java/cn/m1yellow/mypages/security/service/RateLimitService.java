package cn.m1yellow.mypages.security.service;

import cn.m1yellow.mypages.common.aspect.RateLimit;

public interface RateLimitService {

    boolean limit(String ip, String uri, RateLimit rateLimit);

}
