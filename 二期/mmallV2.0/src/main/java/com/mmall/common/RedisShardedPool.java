package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;

/**
 * redis 分片池
 */
public class RedisShardedPool {
    private static ShardedJedisPool pool;//jedis连接池
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));//最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));//在jedispool中最大的idle状态（空闲）的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));//在jedispool中最小的idle状态（空闲）的jedis实例的个数
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true，则得到的jedis实例肯定是可以用的
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true，则放回jedispool的jedis实例肯定是可以用的
    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip", "127.0.0.1");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port", "6379"));
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip", "127.0.0.1");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port", "6379"));

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true); //连接耗尽时，是否阻塞，false会抛出异常，true阻塞直到超时，

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port);

        ArrayList<JedisShardInfo> jedisShardInfos = new ArrayList<>(2);
        jedisShardInfos.add(info1);
        jedisShardInfos.add(info2);

        pool = new ShardedJedisPool(config, jedisShardInfos, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
    }

    static {
        initPool();
    }

    public static ShardedJedis getJedis(){
        return pool.getResource();
    }

    public static void returnBrokenResource(ShardedJedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void returnResource(ShardedJedis jedis) {
        pool.returnResource(jedis);
    }
}
