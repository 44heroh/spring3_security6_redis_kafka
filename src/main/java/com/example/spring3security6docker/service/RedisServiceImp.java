package com.example.spring3security6docker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImp<K, V> {

    private final RedisTemplate<K, V> redisTemplate;

    @Autowired
    public RedisServiceImp(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setValue(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public V getValue(K key) {
        return redisTemplate.opsForValue().get(key);
    }
}
