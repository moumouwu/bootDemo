package com.zhty.inspect.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Qin
 * @version 1.0
 * @date 2020-12-15 17:30
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

  @Bean
  public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String,Object> template = new RedisTemplate<>();
    // 配置连接工厂
    template.setConnectionFactory(factory);

    // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列方式）
    Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

    ObjectMapper objectMapper = new ObjectMapper();
    // 指定要序列化的域，field,get和set，以及修饰范围,ANY包括private和public
    objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);

    // 指定序列化输入的类型，类必须是非final修饰额，final修饰的类比如String、Integer等会抛出异常
    objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, DefaultTyping.NON_FINAL,
        As.PROPERTY);
    jsonRedisSerializer.setObjectMapper(objectMapper);

    // 值采用json序列化
    template.setValueSerializer(valueSerializer());
    // 使用StringRedisSerializer来序列化和反序列化redis的key值
    template.setKeySerializer(keySerializer());

    // 设置hash key和value序列化模式
    template.setHashKeySerializer(keySerializer());
    template.setHashValueSerializer(valueSerializer());
    template.afterPropertiesSet();

    return template;
  }


  private RedisSerializer<String> keySerializer(){
    return new StringRedisSerializer();
  }

  private RedisSerializer<Object> valueSerializer(){
    return new GenericJackson2JsonRedisSerializer();
  }

  @Bean
  @Override
  public KeyGenerator keyGenerator(){
    return (target,method,params) -> {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(target.getClass().getName());
      stringBuilder.append(method.getName());
      for (Object object: params) {
        stringBuilder.append(object.toString());
      }
      return stringBuilder.toString();
    };
  }

  @Bean
  public HashOperations<String,String,Object> hashOperations(
      RedisTemplate<String,Object> redisTemplate){
    return redisTemplate.opsForHash();
  }

  @Bean
  public ValueOperations<String,Object> valueOperations(RedisTemplate<String,Object> redisTemplate){
    return redisTemplate.opsForValue();
  }

  @Bean
  public ListOperations<String,Object> listOperations(RedisTemplate<String,Object> redisTemplate) {
    return redisTemplate.opsForList();
  }

  @Bean
  public SetOperations<String,Object> setOperations(RedisTemplate<String,Object> redisTemplate){
    return redisTemplate.opsForSet();
  }

  @Bean
  public ZSetOperations<String,Object> zSetOperations(RedisTemplate<String,Object> redisTemplate){
    return redisTemplate.opsForZSet();
  }
}
