package cn.m1yellow.mypages.security.config;

import cn.m1yellow.mypages.common.constant.CacheType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

@Configuration
@CacheConfig
@EnableCaching
public class RedisConfig {

    @Bean
    //@SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 设置通用的 <String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);

        // 对象映射处理模型
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // enableDefaultTyping 的作用，保留类型，便于反序列化识别类型
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); // enableDefaultTyping 过时了
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        // 解决jackson2无法反序列化LocalDateTime的问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());

        // 序列化的对象
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(Object.class);
        valueSerializer.setObjectMapper(objectMapper);

        // key采用String的序列化方式
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);

        // value序列化方式采用jackson
        template.setValueSerializer(valueSerializer);
        template.setHashValueSerializer(valueSerializer);

        //设置
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 初始化一个 RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        // 初始化一个 RedisCacheConfiguration
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        // 返回一个自定义的 CacheManager
        return new CustomizeTtlRedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

    @Bean
    public KeyGenerator simpleKeyGenerator() {
        return (o, method, objects) -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getSimpleName());
            stringBuilder.append(".");
            stringBuilder.append(method.getName());
            stringBuilder.append("[");
            for (Object obj : objects) {
                stringBuilder.append(obj.toString());
            }
            stringBuilder.append("]");

            return stringBuilder.toString();
        };
    }


    /**
     * 重载 redisCacheManager，实现从 cacheName 中提取过期时间进行配置
     */
    class CustomizeTtlRedisCacheManager extends RedisCacheManager {

        /**
         * 缓存参数的分隔符
         * 数组元素0=缓存的名称
         * 数组元素1=缓存过期时间TTL
         */
        private static final String DEFAULT_SEPARATOR = "#";

        /**
         * 默认缓存失效时间，10分钟
         */
        private static final long DEFAULT_TTL = 10 * 60;


        public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
            super(cacheWriter, defaultCacheConfiguration);
        }

        public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, String... initialCacheNames) {
            super(cacheWriter, defaultCacheConfiguration, initialCacheNames);
        }

        public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, boolean allowInFlightCacheCreation, String... initialCacheNames) {
            super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheNames);
        }

        public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
            super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations);
        }

        public CustomizeTtlRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration, Map<String, RedisCacheConfiguration> initialCacheConfigurations, boolean allowInFlightCacheCreation) {
            super(cacheWriter, defaultCacheConfiguration, initialCacheConfigurations, allowInFlightCacheCreation);
        }

        @Override
        protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
            // 获取指定的过期时间
            Duration ttl = getTtlByName(name);
            if (null != ttl) {
                cacheConfig = cacheConfig.entryTtl(ttl);
            }
            // 修改缓存key
            //cacheConfig = cacheConfig.computePrefixWith(cacheName -> cacheName + ":");

            // 设置key的序列化方式
            cacheConfig.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()));
            // 设置value的序列化方式
            cacheConfig.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));

            return super.createRedisCache(name, cacheConfig);
        }

        // key键序列化方式
        private RedisSerializer<String> keySerializer() {
            return new StringRedisSerializer();
        }

        // value值序列化方式
        private Jackson2JsonRedisSerializer valueSerializer() {
            Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
            // 对象映射处理模型
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
            // enableDefaultTyping 的作用，保留类型，便于反序列化识别类型
            //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL); // enableDefaultTyping 过时了
            objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
            // 解决jackson2无法反序列化LocalDateTime的问题
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.registerModule(new JavaTimeModule());
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
            return new Jackson2JsonRedisSerializer(Object.class);
        }

        /**
         * 通过name获取过期时间
         *
         * @param name
         * @return
         */
        private Duration getTtlByName(String name) {

            Duration duration = Duration.ofSeconds(DEFAULT_TTL);

            if (StringUtils.isBlank(name)) {
                return duration;
            }

            Long ttl = CacheType.getTtlByName(name);
            if (null != ttl) {
                if (ttl.equals(0)) {
                    duration = Duration.ZERO;
                } else {
                    duration = Duration.ofSeconds(ttl);
                }
            }

            return duration;
        }
    }

}
