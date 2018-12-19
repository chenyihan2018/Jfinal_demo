package com.jslx.register.config;

import com.jfinal.plugin.redis.serializer.ISerializer;
import redis.clients.util.SafeEncoder;

/**
 * @author: lybing
 * @create: 2018-12-14 10:20
 * @desc: 序列化成String
 */

public class StringRedisSerializer implements ISerializer {
    public static final StringRedisSerializer me = new StringRedisSerializer();

    @Override
    public byte[] keyToBytes(String s) {
        return SafeEncoder.encode(s);
    }

    @Override
    public String keyFromBytes(byte[] bytes) {
        return SafeEncoder.encode(bytes);
    }

    @Override
    public byte[] fieldToBytes(Object o) {
        return new byte[0];
    }

    @Override
    public Object fieldFromBytes(byte[] bytes) {
        return SafeEncoder.encode(bytes);
    }

    @Override
    public byte[] valueToBytes(Object o) {
        if (o == null){
            throw new RuntimeException("o can not be null for valueToBytes");
        }
        return SafeEncoder.encode(o.toString());
    }

    @Override
    public Object valueFromBytes(byte[] bytes) {
        return SafeEncoder.encode(bytes);
    }

}
