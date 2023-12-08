package com.example.spring3security6docker.service.impl;

import com.example.spring3security6docker.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImp<K, V> implements RedisService {

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
