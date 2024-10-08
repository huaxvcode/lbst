package com.lbstspringboot3.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class Redis {

  private final RedisTemplate<String, Object> redisTemplate;

  @Autowired
  public Redis(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  // ===================================common===================================

  /**
   * 指定缓存失效时间
   *
   * @param key
   * @param time
   * @return
   */
  public boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 根据key获取过期时间
   *
   * @param key 键不能为null
   * @return
   */
  public long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  /**
   * 判断key是否存在
   *
   * @param key
   * @return
   */
  public boolean hasKey(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 删除缓存
   * @param key 可以传一个值或多个
   */
  /*@SuppressWarnings("unchecked")
  public void del(String... key)
  {
      if (key != null && key.length > 0) {
          if (key.length == 1) {
              redisTemplate.delete(key[0]);
          } else {
              redisTemplate.delete(CollectionUtils.arrayToList(key));
          }
      }
  }*/

  // ===================================String===================================

  /**
   * 普通缓存获取
   *
   * @param key
   * @return
   */
  public Object get(String key) {
    return key == null ? null : redisTemplate.opsForValue().get(key);
  }

  /**
   * 普通缓存放入
   *
   * @param key
   * @param value
   * @return
   */
  public boolean set(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 普通缓存放入并设置时间
   *
   * @param key
   * @param value
   * @param time  时间(秒) time要大于0，如果time小于等于0，将设置无限期
   * @return
   */
  public boolean set(String key, Object value, long time) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      } else {
        set(key, value);
      }
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 递增
   *
   * @param key
   * @param delta
   * @return
   */
  public long incr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递增因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(key, delta);
  }

  /**
   * 递减
   *
   * @param key
   * @param delta
   * @return
   */
  public long decr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递减因子必须大于0");
    }
    return redisTemplate.opsForValue().increment(key, -delta);
  }

  // ===================================Map===================================

  /**
   * HashGet
   *
   * @param key  不能为null
   * @param item 不能为null
   * @return
   */
  public Object hget(String key, String item) {
    return redisTemplate.opsForHash().get(key, item);
  }

  /**
   * 获取hashKey对应的所有键值
   *
   * @param key
   * @return
   */
  public Map<Object, Object> hmget(String key) {
    return redisTemplate.opsForHash().entries(key);
  }

  /**
   * HashSet
   *
   * @param key
   * @param map
   * @return
   */
  public boolean hmset(String key, Map<String, Object> map) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * HashSet 并设置时间
   *
   * @param key
   * @param map
   * @param time
   * @return
   */
  public boolean hmset(String key, Map<String, Object> map, long time) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key
   * @param item
   * @param value
   * @return
   */
  public boolean hset(String key, String item, Object value) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 向一张hash表中放入数据,如果不存在将创建
   *
   * @param key
   * @param item
   * @param value
   * @param time  如果已存在的hash表有时间,这里将会替换原有的时间
   * @return
   */
  public boolean hset(String key, String item, Object value, long time) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 删除hash表中的值
   *
   * @param key
   * @param item
   */
  public void hdel(String key, Object... item) {
    redisTemplate.opsForHash().delete(key, item);
  }

  /**
   * 判断hash表中是否有该项的值
   *
   * @param key
   * @param item
   * @return
   */
  public boolean hHasKey(String key, String item) {
    return redisTemplate.opsForHash().hasKey(key, item);
  }

  /**
   * hash递增 如果不存在,就会创建一个 并把新增后的值返回
   *
   * @param key
   * @param item
   * @param by
   * @return
   */
  public double hincr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(key, item, by);
  }

  /**
   * hash递减
   *
   * @param key
   * @param item
   * @param by
   * @return
   */
  public double hdecr(String key, String item, double by) {
    return redisTemplate.opsForHash().increment(key, item, -by);
  }

  // ===================================set===================================

  /**
   * 根据key获取Set中的所有值
   *
   * @param key
   * @return
   */
  public Set<Object> sGet(String key) {
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      log.error(key, e);
      return null;
    }
  }

  /**
   * 根据value从一个set中查询,是否存在
   *
   * @param key
   * @param value
   * @return
   */
  public boolean sHasKey(String key, Object value) {
    try {
      return redisTemplate.opsForSet().isMember(key, value);
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 将数据放入set缓存
   *
   * @param key
   * @param values
   * @return
   */
  public long sSet(String key, Object... values) {
    try {
      return redisTemplate.opsForSet().add(key, values);
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  /**
   * 将set数据放入缓存
   *
   * @param key
   * @param time
   * @param values
   * @return
   */
  public long sSetAndTime(String key, long time, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().add(key, values);
      if (time > 0)
        expire(key, time);
      return count;
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  /**
   * 获取set缓存的长度
   *
   * @param key
   * @return
   */
  public long sGetSetSize(String key) {
    try {
      return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  /**
   * 移除值为value的
   *
   * @param key
   * @param values
   * @return
   */
  public long setRemove(String key, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().remove(key, values);
      return count;
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  // ===================================zset===================================

  /**
   * 根据key获取Set中的所有值
   *
   * @param key
   * @return
   */
  public Set<Object> zSGet(String key) {
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      log.error(key, e);
      return null;
    }
  }

  /**
   * 根据value从一个set中查询,是否存在
   *
   * @param key
   * @param value
   * @return
   */
  public boolean zSHasKey(String key, Object value) {
    try {
      return redisTemplate.opsForSet().isMember(key, value);
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  public Boolean zSSet(String key, Object value, double score) {
    try {
      return redisTemplate.opsForZSet().add(key, value, 2);
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 将set数据放入缓存
   *
   * @param key
   * @param time
   * @param values
   * @return
   */
  public long zSSetAndTime(String key, long time, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().add(key, values);
      if (time > 0)
        expire(key, time);
      return count;
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  /**
   * 获取set缓存的长度
   *
   * @param key
   * @return
   */
  public long zSGetSetSize(String key) {
    try {
      return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  /**
   * 移除值为value的
   *
   * @param key
   * @param values
   * @return
   */
  public long zSetRemove(String key, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().remove(key, values);
      return count;
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  // ===================================list===================================

  /**
   * 获取list缓存的内容
   *
   * @param key
   * @param start 开始 0 是第一个元素
   * @param end   结束 -1代表所有值
   * @return
   * @取出来的元素 总数 end-start+1
   */
  public List<Object> lGet(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (Exception e) {
      log.error(key, e);
      return null;
    }
  }

  /**
   * 获取list缓存的长度
   *
   * @param key
   * @return
   */
  public long lGetListSize(String key) {
    try {
      return redisTemplate.opsForList().size(key);
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }

  /**
   * 通过索引 获取list中的值
   *
   * @param key
   * @param index 索引 index>=0时， 0 表头，1
   *     第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
   * @return
   */
  public Object lGetIndex(String key, long index) {
    try {
      return redisTemplate.opsForList().index(key, index);
    } catch (Exception e) {
      log.error(key, e);
      return null;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key
   * @param value
   * @return
   */
  public boolean lSet(String key, Object value) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key
   * @param value
   * @param time
   * @return
   */
  public boolean lSet(String key, Object value, long time) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      if (time > 0)
        expire(key, time);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key
   * @param value
   * @return
   */
  public boolean lSet(String key, List<Object> value) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 将list放入缓存
   *
   * @param key
   * @param value
   * @param time
   * @return
   */
  public boolean lSet(String key, List<Object> value, long time) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      if (time > 0)
        expire(key, time);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 根据索引修改list中的某条数据
   *
   * @param key
   * @param index
   * @param value
   * @return
   */
  public boolean lUpdateIndex(String key, long index, Object value) {
    try {
      redisTemplate.opsForList().set(key, index, value);
      return true;
    } catch (Exception e) {
      log.error(key, e);
      return false;
    }
  }

  /**
   * 移除N个值为value
   *
   * @param key
   * @param count
   * @param value
   * @return
   */
  public long lRemove(String key, long count, Object value) {
    try {
      Long remove = redisTemplate.opsForList().remove(key, count, value);
      return remove;
    } catch (Exception e) {
      log.error(key, e);
      return 0;
    }
  }
}