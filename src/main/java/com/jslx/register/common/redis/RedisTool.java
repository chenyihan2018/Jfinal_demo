package com.jslx.register.common.redis;/**
 * Created by chenjia on 2018/12/17.
 */

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import com.jfinal.plugin.redis.serializer.FstSerializer;
import com.jfinal.plugin.redis.serializer.ISerializer;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @author chenjia
 * @create 2018-12-17 11:46
 * @desc redis
 **/
public class RedisTool {

    private static Cache redis =  Redis.use();

    private static SerializerFeature[] features = {SerializerFeature.WriteMapNullValue};

    /**
     * 获取对应db的jedis客户端
     * @param
     * @return
     */
    public static Jedis getJedis(Integer dbNum){
        Jedis jedis = redis.getJedis();
        jedis.select(dbNum);
        return jedis;
    }

    /**
     * 把对象信息放入缓存中
     * @return
     */
    public static Object setObject2Redis(String key, Object object) {
        Jedis jedis = getJedis(0);
        try {
            if (object instanceof String) {
                jedis.set(key, (String) object);
            } else {
                jedis.set(key, JSONObject.toJSONString(object, features));
            }
        } finally {
            jedis.close();
        }
        return object;
    }

    /**
     * 存储过去时间的对象
     * @param key
     * @param object
     * @return
     */
    public static Object setObject2RedisExpire(String key, Object object,int secondes) {
        Jedis jedis = getJedis(0);
        try {
            if (object instanceof String) {
                jedis.set(key, (String) object);
                jedis.expire(key,secondes);
            } else {
                jedis.set(key, JSONObject.toJSONString(object, features));
                jedis.expire(key,secondes);
            }
        } finally {
            jedis.close();
        }
        return object;
    }

    public static Object setObject2Redis(String key, Object object, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        if (object instanceof String) {
            userJedis.set(key, (String) object);
        } else {
            userJedis.set(key, JSONObject.toJSONString(object, features));
        }
        return object;
    }

    public static Object getObjectFromRedis(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        String s = userJedis.get(key);
        try {
            Object parse = JSONObject.parse(s);
            return parse;
        } catch (Exception e) {
            return s;
        }
    }


    /**
     * 从缓存中获取对象
     *
     * @param key
     * @return
     */
    public static Object getObjectfromRedis(String key) {
        Jedis jedis = getJedis(0);
        try {
            String s = jedis.get(key);
            try {
                Object parse = JSONObject.parse(s);
                return parse;
            } catch (Exception e) {
                return s;
            }
        } finally {
            jedis.close();
        }
    }

    public static void removeObjectFromRedis(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        userJedis.del(key);
    }

    public static Object getObjectfromRedis(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        String s = userJedis.get(key);
        try {
            Object parse = JSONObject.parse(s);
            return parse;
        } catch (Exception e) {
            return s;
        }
    }


    /**
     * 向缓存中的list添加一个元素
     *
     * @param key   缓存中list的名字
     * @param value 需要添加到缓存中的对象
     * @return 返回list的长度
     */
    public static Long add2RedisListHead(String key, Object value, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        if (!(value instanceof String)) {
            String s = JSONObject.toJSONString(value, features);
            Long lpush = userJedis.lpush(key, s);
            return lpush;
        } else {
            return userJedis.lpush(key, (String) value);
        }
    }

    /**
     * 向缓存中的list尾部追加一个元素
     *
     * @param key   缓存中的list的名字
     * @param value 需要加入到缓存中的对象
     * @return 返回list的长度
     */
    public static Long add2RedisListEnd(String key, Object value, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        if (!(value instanceof String)) {
            return userJedis.rpush(key, JSONObject.toJSONString(value, features));
        }
        return userJedis.rpush(key, (String) value);
    }


    /**
     * 判断缓存List中是否存在某vlaue
     *
     * @param key
     * @param value
     * @param userJedis
     * @return
     */
    public static boolean isExistToList(String key, String value, Jedis userJedis) {
        boolean isExist = false;
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        List<String> list = null;
        list = userJedis.lrange(key, 0, userJedis.llen(key));
        if (list != null && list.size() > 0) {
            if (list.contains(value)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public static boolean isExistToListNew(String key, String value, Jedis userJedis) {
        boolean isExist = false;
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        List<String> list = null;
        if (userJedis.exists(key)) {
            list = userJedis.lrange(key, 0, userJedis.llen(key));
        }
        if (list != null && list.size() > 0) {
            for (String str : list) {
                if (str.equals(value)) {
                    isExist = true;
                }
            }
        }
        return isExist;
    }

    /**
     * 获取List集合
     *
     * @param key
     * @param userJedis
     * @return
     */
    public static List<String> getRedisList(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        return userJedis.lrange(key, 0, userJedis.llen(key));
    }

    /**
     * 删除缓存中list中指定的对象(后存储的先删除)
     *
     * @param key    缓存中的list的名字
     * @param count  指定对象出现的次数,如果有多个则填入相关的数字,默认为1可以为null
     * @param object
     * @return 返回删除后List的对象
     */

    public static Long deleteObjectsFromRedisList(String key, Integer count, Object object, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        if (!(object instanceof String)) {
            object = JSONObject.toJSONString(object, features);
        }
        return userJedis.lrem(key, count, (String) object);
    }

    /**
     * 从列表的头部弹出一个值
     *
     * @param key list列表的名字
     * @return 弹出的对象
     */
    public static Object popObjectsFromRedisListHead(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        String lpop = userJedis.lpop(key);
        Object object = null;
        try {
            try {
                object = JSONObject.parse(lpop);
            } catch (Exception e) {
                return lpop;
            }
            return object;
        } catch (Exception e) {
            return lpop;
        }
    }

    /**
     * 从列表的尾部弹出一个值
     *
     * @param key list列表的名字
     * @return 返回弹出的对象
     */
    public static Object popObjectsFromRedisListEnd(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        String rpop = userJedis.rpop(key);
        Object parse = null;
        try {
            parse = JSONObject.parse(rpop);
            return parse;
        } catch (Exception e) {
            return rpop;
        }
    }

    /**
     * 获取list的长度
     *
     * @param key List列表的名字
     * @return 列表的长度
     */
    public static Long getRedisListSize(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        return userJedis.llen(key);
    }

    /**
     * 返回列表中从头开始其实到结束位置的list
     *
     * @param key   list列表的名字
     * @param start 开始的索引
     * @param end   结束的索引
     * @return list
     */
    public static List getObjectsFromRedisListStartHead(String key, Long start, Long end, Jedis userJedis) {
        ArrayList<Object> objects;
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        List<String> lrange = userJedis.lrange(key, start, end);
        objects = new ArrayList<>();
        for (String s : lrange) {
            Object parse = JSONObject.parse(s);
            objects.add(parse);
        }
        return objects;
    }

    /**
     * 把对象保存到set集合中
     *
     * @param key    set的名字
     * @param object 需要存入的对象
     * @return set的长度
     */
    public static Long addObject2RedisSet(String key, Object object, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        if (!(object instanceof String)) {
            object = JSONObject.toJSONString(object, features);
        }
        return userJedis.sadd(key, (String) object);
    }

    /**
     * 获得集合里面的所有的数据
     *
     * @param key 集合的名字
     * @return 返回一个set集合
     */
    public static Set getAllFromRedisSet(String key, Jedis userJedis) {
        HashSet<Object> objects;
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        Set<String> smembers = userJedis.smembers(key);
        return smembers;
    }

    public static Object getObjectFromRedisSet(String key, Jedis userJedis) {
        String spop = userJedis.spop(key);
        Object parse = JSONObject.parse(spop);
        return parse;
    }

    public static Map<String, String> getHgetAllFromRedisMap(String key, Jedis userJedis) {
        Map<String, String> map = null;
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        map = userJedis.hgetAll(key);
        return map;
    }

    /**
     * 把一系列的数据存入到一个集合中(初始化数据用的比较多)
     *
     * @param key     set的名字
     * @param objects 对象可变数组
     * @return 返回长度
     */
    public static Long addObjects2RedisSet(String key, Object... objects) {
        Long sadd = redis.sadd(key, objects);
        return sadd;
    }

    /**
     * 返回两个set之间的差集
     *
     * @param key
     * @param key2
     * @return
     */
    public static Set getSetAfterSdiff(String key, String key2, Jedis userJedis) {
        HashSet<Object> objects;
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        Set<String> sdiff = userJedis.sdiff(key, key2);
        objects = new HashSet<>();
        Iterator<String> iterator = sdiff.iterator();
        while (iterator.hasNext()) {
            objects.add(JSONObject.parse(iterator.next()));
        }
        return objects;
    }

    /**
     * 获取两个集合之间的交集
     *
     * @param key
     * @param key2
     * @return
     */
    public static Set getSetAfterSinter(String key, String key2, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = getJedis(0);
        }
        return userJedis.sinter(key, key2);
    }

    /**
     * 把一个对象和排序的字段添加到排名集合中
     *
     * @param setKey set的名字
     * @param score  分数
     * @param key    分数对应的key
     * @return 返回添加后的set的大小
     */
    public static Long addObject2RankSet(String setKey, Double score, Object key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        if (!(key instanceof String)) {
            key = JSONObject.toJSONString(key, features);
        }
        return userJedis.zadd(setKey, score, (String) key);
    }

    /**
     * 返回排名池里面的所有元素
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set getAllFromRank(String key, Long start, Long end, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        return userJedis.zrange(key, start, end);
    }

    /**
     * 获取某个对象的排名,从1开始
     *
     * @param zsetkey
     * @param key
     * @return
     */
    public static Long getRank(String zsetkey, Object key, Jedis userJedis) {
        Long zrank = 0L;
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        if (!(key instanceof String)) {
            key = JSONObject.toJSONString(key, features);
        }
        zrank = userJedis.zrank(zsetkey, (String) key) == null ? 0L : userJedis.zrank(zsetkey, (String) key);
        //由于排名是从0开始的所以自动加一
        zrank++;
        return zrank;
    }

    /**
     * 回名称为key的zset（元素已按score从大到小排序）中member元素的rank（即index，从0开始）
     *
     * @param zsetkey
     * @param key
     * @param userJedis
     * @return
     */
    public static Long getZrevRank(String zsetkey, String key, Jedis userJedis) {
        Long score = 0L;
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        score = userJedis.zrevrank(zsetkey, key);
        //由于排名是从0开始的所以自动加一
        if (score != null) {
            score++;
        }
        return score == null ? 0 : score;
    }

    /**
     * 获取前几位按照分数的降序排列,索引从0开始
     *
     * @param zsetKey
     * @param start
     * @param end
     * @param userJedis
     * @return
     */
    public static List getTopMembersFromRank(String zsetKey, Long start, Long end, Jedis userJedis) {
        List list = new ArrayList();
        Set<String> zrevrange = userJedis.zrevrange(zsetKey, start, end);
        parseObject(list, zrevrange);
        return list;
    }

    /**
     * 获取后几位按照分数的降序排列,索引从0开始
     *
     * @param zsetKey
     * @param start
     * @param end
     * @param userJedis
     * @return
     */
    public static List getLastMembersFromRank(String zsetKey, Long start, Long end, Jedis userJedis) {
        List list = new ArrayList();
        Set<String> zrevrange = userJedis.zrange(zsetKey, start, end);
        parseObject(list, zrevrange);
        return list;
    }

    public static List<Object> getLastMembers2FromRank(String zsetKey, int start, int end, Jedis userJedis) {
        List<Object> list = new ArrayList();
        Set<String> zrevrange = userJedis.zrange(zsetKey, start, end);
        parseObject(list, zrevrange);
        return list;
    }


    /**
     * 解析结果集
     *
     * @param list
     * @param zrevrange
     */
    public static void parseObject(List list, Set<String> zrevrange) {
        for (String s : zrevrange) {
            try {
                Object objects = JSONObject.parseObject(s);
                list.add(objects);
            } catch (Exception e) {
                list.add(s);
            }
        }
    }

    /**
     * 获取排名基数
     *
     * @param zsetKey
     * @param userJedis
     * @return
     */
    public static Long getCardinality(String zsetKey, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        return userJedis.zcard(zsetKey);
    }

    /**
     * 获取某个对象的排名的分数
     *
     * @param zsetKey
     * @param key
     * @return
     */
    public static Double getRankScore(String zsetKey, Object key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        if (!(key instanceof String)) {
            key = JSONObject.toJSONString(key, features);
        }
        return userJedis.zscore(zsetKey, (String) key);
    }

    /**
     * 给指定的对象增加一定的分数
     *
     * @param zsetKey
     * @param score
     * @param key
     * @return
     */
    public static Double addScore2ObjecInRank(String zsetKey, Double score, Object key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        if (!(key instanceof String)) {
            key = JSONObject.toJSONString(key, features);
        }
        return userJedis.zincrby(zsetKey, score, (String) key);
    }

    /**
     * 从有序列表中按照倒分数的叙获取对象的集合
     *
     * @param zsetKey
     * @param start
     * @param end
     * @param userJedis
     */
    public static List<Object> getObjectsFromRank(String zsetKey, Integer start, Integer end, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        Set<String> zrange = userJedis.zrevrange(zsetKey, start, end);//从大到小进行排列
        List<Object> list = new ArrayList<>();
        for (String s : zrange) {
            try {
                Object parse = JSONObject.parse(s);
                list.add(parse);
            } catch (Exception e) {
                list.add(s);
            }
        }
        return list;

    }


    public static Cache getRedis() {
        return redis;
    }

    public static void setRedis(Cache redis) {
        RedisTool.redis = redis;
    }

    /**
     * 从set中删除某个元素
     *
     * @param key
     * @param object
     * @param userJedis
     * @return
     */
    public static Long removeObjectFromRedsi(String key, Object object, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        if (!(object instanceof String)) {
            object = JSONObject.toJSONString(key, features);
        }
        return userJedis.srem(key, (String) object);
    }

    /**
     * 向名称为key的hash中添加元素field<—>value
     *
     * @param key
     * @param field
     * @param value
     * @param userJedis
     */
    public static Long setHashForRedis(String key, String field, Object value, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        if (!(value instanceof String)) {
            value = JSONObject.toJSONString(value, SerializerFeature.WriteMapNullValue);
        }
        return userJedis.hset(key, field, (String) value);
    }

    /**
     * 名称为key的hash中是否存在键为field的域
     *
     * @param key
     * @param field
     * @param userJedis
     * @return
     */
    public static boolean isExistsHashForReids(String key, String field, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        return userJedis.hexists(key, field);
    }

    /**
     * 返回名称为key的hash中field对应的value
     *
     * @param key
     * @param field
     * @param userJedis
     * @return
     */
    public static String getHashForRedis(String key, String field, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        return userJedis.hget(key, field);
    }

    /**
     * 返回名称为key的hash中所有键
     *
     * @param key
     * @param userJedis
     * @return
     */
    public static Set<String> getHashKeysForRedis(String key, Jedis userJedis) {
        if (userJedis == null) {
            userJedis = RedisTool.getJedis(0);
        }
        return userJedis.hkeys(key);
    }

    /**
     * 获取set的集合的成员的数量
     */
    public static Long getSetSize(String key, Jedis userJedis) {
        Long scard = userJedis.scard(key);
        return scard;
    }

    /**
     * 从缓存的集合中获取一个对象
     *
     * @param key
     * @param jedis
     * @return
     */
    public static Object getOneFromSet(String key, Jedis jedis) {
        String spop = jedis.spop(key);
        Object jsonObject = null;
        try {
            jsonObject = JSONObject.parse(spop);
        } catch (Exception e) {
            jsonObject = spop;
        }
        return jsonObject;
    }

    /**
     * 根据分数段获取一个集合,按照分数从高到底排列
     *
     * @param key
     * @param min
     * @param max
     * @param jedis
     */
    public static ArrayList<String> getMembersByScoreArea(String key, Double min, Double max, Jedis jedis) {
        Set<String> strings = jedis.zrevrangeByScore(key, max, min);
        strings.forEach(System.out::println);
        System.out.println("=======");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(strings);
        return arrayList;
    }

    /**
     * ============序列化相关==========
     */

    protected static ISerializer serializer = FstSerializer.me;

    /**
     * 这个是key的序列化方式
     *
     * @param key
     * @return
     */
    protected static byte[] keyToBytes(String key) {
        return serializer.keyToBytes(key);
    }

    /**
     * 这个是value的序列化方式
     *
     * @param value
     * @return
     */
    protected static byte[] valueToBytes(Object value) {
        return serializer.valueToBytes(value);
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    protected static Object valueFromBytes(byte[] bytes) {
        return serializer.valueFromBytes(bytes);
    }

    public static String set(String key, Object value, Jedis jedis) {
        return jedis.set(keyToBytes(key), valueToBytes(value));
    }

    public static String setex(String key, int seconds, Object value, Jedis jedis) {
        return jedis.setex(keyToBytes(key), seconds, valueToBytes(value));
    }

    public static  <T> T get(String key, Jedis jedis) {
        return (T) valueFromBytes(jedis.get(keyToBytes(key)));
    }
}
