package com.learn.microservices.inventory_service.Config;

import jakarta.annotation.PostConstruct;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisCacheConfig {

    // currently my redisson bean is being setup automatically by spring, so I need this for health check.

    private final RedissonClient redissonClient;

    public RedisCacheConfig(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
 // I have used a separate way to configure this in user service config file.

    // currently my redisson bean is being setup automatically by spring, so I dont need this.
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redissonClient() throws IOException {
//        Config config = Config.fromYAML(
//                this.getClass().getClassLoader().getResource("redisson-config.yml")
//        );
//        return Redisson.create(config);
//    }
//
//    @Bean
//    public CacheManager cacheManager(RedissonClient redissonClient) {
//        Map<String, CacheConfig> configMap = new HashMap<>();
//        configMap.put("users", new org.redisson.spring.cache.CacheConfig(10 * 60 * 1000, 0));
//        configMap.put("products", new org.redisson.spring.cache.CacheConfig(60 * 60 * 1000, 0));
//
//        return new RedissonSpringCacheManager(redissonClient, configMap);
//    }


    // Optional: Redis Health Check - this will run after bean initialization
    @PostConstruct
    public void checkRedisHealth() {
        try {
//            RedissonClient redissonClient = redissonClient(); // Call the bean method
            long keyCount = redissonClient.getKeys().count();
            System.out.println("Redis is up and reachable. Total keys: " + keyCount);
        } catch (Exception e) {
            System.err.println("Redis is NOT reachable: " + e.getMessage());
        }
    }

}

