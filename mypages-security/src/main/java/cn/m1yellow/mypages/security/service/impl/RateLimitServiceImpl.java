package cn.m1yellow.mypages.security.service.impl;

import cn.m1yellow.mypages.common.aspect.RateLimit;
import cn.m1yellow.mypages.common.util.ObjectUtil;
import cn.m1yellow.mypages.common.util.RedisUtil;
import cn.m1yellow.mypages.security.service.RateLimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public boolean limit(String ip, String uri, RateLimit rateLimit) {
        String key = "rate:" + ip + ":" + uri;
        // 缓存中存在key，在限定访问周期内已经调用过当前接口
        if (redisUtil.hasKey(key)) {
            // 访问次数自增1
            redisUtil.incr(key, 1);
            // 超出访问次数限制
            int count = Integer.parseInt(ObjectUtil.getString(redisUtil.get(key)));
            return count <= rateLimit.number();
            // 未超出访问次数限制，不进行任何操作，返回true
        } else {
            // 第一次设置数据，过期时间为注解配置的访问周期
            redisUtil.set(key, 1, rateLimit.cycle());
        }
        return true;
    }
}
