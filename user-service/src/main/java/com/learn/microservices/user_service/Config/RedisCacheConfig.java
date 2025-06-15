package com.learn.microservices.user_service.Config;

import jakarta.annotation.PostConstruct;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    // currently my redisson bean is being setup automatically by spring, so I need this for health check.

    private final RedissonClient redissonClient;

    public RedisCacheConfig(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    // currently my redisson bean is being setup automatically by spring, so I dont need this.
//    @Bean
//    public CacheManager cacheManager() {
//        try {
//            Config config = Config.fromYAML(
//                    this.getClass().getClassLoader().getResource("redisson-config.yml")
//            );
//            RedissonClient redissonClient = Redisson.create(config);
//
////            Map<String, CacheConfig> configMap = new HashMap<>();
////            configMap.put("users", new CacheConfig(10 * 60 * 1000, 0));
////            configMap.put("products", new CacheConfig(60 * 60 * 1000, 0));
////            return new RedissonSpringCacheManager(redissonClient, configMap);
//
//            return new RedissonSpringCacheManager(redissonClient);
//        } catch (Exception e) {
//            System.err.println("Redis not available. Falling back to NoOpCacheManager.");
//            return new NoOpCacheManager();
//        }
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

