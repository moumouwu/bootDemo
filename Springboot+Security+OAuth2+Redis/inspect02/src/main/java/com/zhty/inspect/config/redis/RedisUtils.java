package com.zhty.inspect.config.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author Qin
 * @date 2020年12月16日10:22:26
 * @version 1.0
 */
@Component
public class RedisUtils {

  @Autowired
  private RedisTemplate<String,Object> redisTemplate;

  public RedisUtils(RedisTemplate<String,Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  /**
   * 执行缓存失效的时间
   * @param key 键
   * @param time 时间（秒）
   * @return
   */
  public boolean expire(String key,long time) {
    try {
      if (time>0) {
        redisTemplate.expire(key,time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 根据 key 获取过期时间
   * @param key 键 不能为null
   * @return 时间（秒） 返回0代表永久有效
   */
  public long getExpire(String key){
    return redisTemplate.getExpire(key,TimeUnit.SECONDS);
  }

  /*
  * 判断 key 是否存在
  *
  * @param key 键 不能为null
  *
  * @return true:存在  false:不存在
  *
  * */
  public boolean hasKey(String key){
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      return false;
    }
  }

  /*
  * 删除缓存
  *
  * @param key 可以传一个或者多个
  *
  * */
  public void del(String ... key) {
    if (key!=null&&key.length>0) {
      if (key.length ==1){
        redisTemplate.delete(key[0]);
      } else {
        redisTemplate.delete(CollectionUtils.arrayToList(key));
      }
    }
  }

  /*
  * 普通缓存获取
  *
  * @param key 键
  *
  * @return 值
  *
  * */
  public Object get(String key){
    return key==null?null:redisTemplate.opsForValue().get(key);
  }

  /*
  * 普通缓存放入
  *
  * @param key 键
  *
  * @param value 值
  *
  * @return
  *
  * */
  public boolean set(String key,Object value){
    try {
      redisTemplate.opsForValue().set(key,value);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /*
  * 普通缓存放入并设置时间
  *
  * @param key 键
  *
  * @param value 值
  *
  * @param time 时间
  *
  * @return
  *
  * */
  public boolean set(String key,Object value,long time){
    try {
      if (time>0){
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      } else {
        set(key,value);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /*
  * 递增
  *
  * @param key 键
  *
  * @param delta 要增加几
  *
  * @return
  *
  * */
  public long incr(String key,long delta) {
    if (delta<0){
      throw new RuntimeException("递增因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(key, delta);
  }

  /*
   * 递减
   *
   * @param key 键
   *
   * @param delta 要减少几
   *
   * @return
   *
   * */
  public long decr(String key,long delta){
    if (delta<0){
      throw new RuntimeException("递减因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(key,-delta);
  }

  /*
  * HashGet
  *
  * @param key 键，不能为null
  *
  * @param item 项，不能为null
  *
  * @return
  *
  * */
  public Object hashGet(String key,String item){
    return redisTemplate.opsForHash().get(key,item);
  }

  /*
  * 获得所有hashKey对应的所有键值
  *
  * @param key 键
  *
  * @return
  *
  * */
  public Map<Object,Object> hashMapGet(String key){
    return redisTemplate.opsForHash().entries(key);
  }


  /*
  * HashMapSet
  *
  * @param key 键
  *
  * @param map 多个键值对
  *
  * @return
  *
  *
  * */
  public boolean hashMapSet(String key,Map<String,Object> map) {
    try {
      redisTemplate.opsForHash().putAll(key,map);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /*
   * HashMapSet 并设置时间
   *
   * @param key 键
   *
   * @param map 多个键值对
   *
   * @param time 时间（秒）
   *
   * @return
   *
   *
   * */
  public boolean hashMapSet(String key,Map<String,Object> map,long time) {
    try {
      redisTemplate.opsForHash().putAll(key,map);
      if (time>0){
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /*
  * 向一张 hash表中放入数据，如果不存在将创建
  *
  * @pram key 键
  *
  * @param item 项
  *
  * @param value 值
  *
  * @return
  *
  *
  * */
  public boolean hashSet(String key,String item,Object value){
    try {
      redisTemplate.opsForHash().put(key,item,value);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 删除hash表中的值
   * @param key 键 不能为null
   * @param item 项 可以使多个 不能为null
   */
  public void hashDel(String key,Object... item){
    redisTemplate.opsForHash().delete(key, item);
  }

  /**
   * 判断hash表中是否有该项的值
   * @param key 键 不能为null
   * @param item 项 不能为null
   * @return true 存在 false不存在
   */
  public boolean hashHasKey(String key, String item){
    return redisTemplate.opsForHash().hasKey(key,item);
  }

  /**
   * hash递增 如果不存在,就会创建一个 并把新增后的值返回
   * @param key 键
   * @param item 项
   * @param by 要增加几(大于0)
   * @return
   */
  public double hashIncr(String key,String item,double by){
    return redisTemplate.opsForHash().increment(key,item,by);
  }

  /**
   * hash递减
   * @param key 键
   * @param item 项
   * @param by 要减少记(小于0)
   * @return
   */
  public double hasDecr(String key,String item,double by){
    return redisTemplate.opsForHash().increment(key,item,-by);
  }

  /**
   * 根据key获取Set中的所有值
   * @param key 键
   * @return
   */
  public Set<Object> setGet(String key){
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 根据value从一个set中查询,是否存在
   * @param key 键
   * @param value 值
   * @return true 存在 false不存在
   */
  public boolean setHasKey(String key,Object value){
    try {
      return redisTemplate.opsForSet().isMember(key,value);
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 将数据放入set缓存
   * @param key 键
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long setSet(String key,Object...values){
    try {
      return redisTemplate.opsForSet().add(key,values);
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 将set数据放入缓存
   * @param key 键
   * @param time 时间(秒)
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public long setSetAndTime(String key,long time,Object...values){
    try {
      Long count = redisTemplate.opsForSet().add(key,values);
      if (time>0){
        expire(key, time);
      }
      return count;
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 获取set缓存的长度
   * @param key 键
   * @return
   */
  public long setGetSetSize(String key){
    try {
      return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 移除值为value的
   * @param key 键
   * @param values 值 可以是多个
   * @return 移除的个数
   */
  public long setRemove(String key,Object...values){
    try {
      Long count = redisTemplate.opsForSet().remove(key,values);
      return count;
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 将list放入缓存
   * @param key 键
   * @param value 值
   * @param time 时间(秒)
   * @return
   */
  public boolean listSet(String key,Object value,long time){
    try {
      redisTemplate.opsForList().rightPush(key,value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   *
   * 将list放入缓存
   * @param key 键
   * @param value 值
   * @param time 时间(秒)
   * @return
   */
  public boolean listSet(String key, List<Object> value, long time){
    try {
      redisTemplate.opsForList().rightPushAll(key,value);
      if (time>0){
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 根据索引修改list中的某条数据
   * @param key 键
   * @param index 索引
   * @param value 值
   * @return
   */
  public boolean listUpdateIndex(String key,long index,Object value){
    try {
      redisTemplate.opsForList().set(key,index,value);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 移除N个值为value
   * @param key 键
   * @param count 移除多少个
   * @param value 值
   * @return 移除的个数
   */
  public long listRemove(String key,long count,Object value){
    try {
      Long remove = redisTemplate.opsForList().remove(key, count, value);
      return remove;
    } catch (Exception e) {
      return 0;
    }
  }

  /**
   * 模糊查询获取key值
   * @param pattern
   * @return
   */
  public Set keys(String pattern){
    return redisTemplate.keys(pattern);
  }


  /**
   * 使用Redis的消息队列
   * @param channel
   * @param message 消息内容
   */
  public void convertAndSend(String channel,Object message){
    redisTemplate.convertAndSend(channel, message);
  }

  /**
   *将数据添加到Redis的list中（从右边添加）
   * @param listKey
   * @param expireEnum 有效期的枚举类
   * @param values 待添加的数据
   */
  public void addToListRight(String listKey, Status.ExpireEnum expireEnum,Object... values){
    BoundListOperations<String,Object> boundListOperations = redisTemplate.boundListOps(listKey);
    boundListOperations.rightPushAll(values);
    boundListOperations.expire(expireEnum.getTime(),expireEnum.getTimeUnit());
  }

  /**
   * 根据起始结束序号遍历Redis中的list
   * @param listKey
   * @param start  起始序号
   * @param end  结束序号
   * @return
   */
  public List<Object> rangeList(String listKey,long start,long end){
    BoundListOperations<String,Object> boundListOperations = redisTemplate.boundListOps(listKey);
    return boundListOperations.range(start,end);
  }

  /**
   * 弹出右边的值 --- 并且移除这个值
   * @param listKey
   */
  public Object rightPop(String listKey){
    BoundListOperations<String,Object> boundListOperations = redisTemplate.boundListOps(listKey);
    return boundListOperations.rightPop();
  }

}
