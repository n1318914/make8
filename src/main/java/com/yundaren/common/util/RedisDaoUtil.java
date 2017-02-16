package com.yundaren.common.util;

import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

@Slf4j
public class RedisDaoUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 获取Redis List缓存
	 */
	public static List getList(String key) {
		List listResult = null;

		try {
			String listJson = RedisClientUtil.get(key);
			// 如果缓存不为空，则返回缓存对象
			if (!StringUtils.isEmpty(listJson)) {
				listResult = objectMapper.readValue(listJson, List.class);
			}
		} catch (Exception e) {
			log.error("jackson list 2 json failed.", e);
		}

		return listResult;
	}

	/**
	 * 获取Redis缓存对象
	 */
	public static <T> T getBean(String key, Class<T> clz) {
		T t = null;

		try {
			String objJson = RedisClientUtil.get(key);
			// 如果缓存不为空，则返回缓存对象
			if (!StringUtils.isEmpty(objJson)) {
				t = objectMapper.readValue(objJson, clz);
			}
		} catch (Exception e) {
			log.error("jackson " + clz + " 2 json failed.", e);
		}

		return t;
	}

	/**
	 * 对象转换为JSON后，放置到Redis
	 */
	public static void set(String key, Object obj) {
		try {
			String jsonText = objectMapper.writeValueAsString(obj);
			RedisClientUtil.set(key, jsonText);
		} catch (Exception e) {
			log.error("jackson json 2 obj failed.", e);
		}
	}

	/**
	 * 删除指定的key,也可以传入一个包含key的数组
	 */
	public static void del(String... keys) {
		RedisClientUtil.del(keys);
	}

	/**
	 * 获取hash缓存对象
	 */
	public static <T> T hget(String key, String field, Class<T> clz) {
		T t = null;

		try {
			String objJson = RedisClientUtil.hget(key, field);
			// 如果缓存不为空，则返回缓存对象
			if (!StringUtils.isEmpty(objJson)) {
				t = objectMapper.readValue(objJson, clz);
			}
		} catch (Exception e) {
			log.error("jackson " + clz + " 2 json failed.", e);
		}

		return t;
	}

	/**
	 * 对象转换为JSON后，放置到Redis hash缓存中
	 */
	public static void hset(String key, String field, Object obj) {
		try {
			String jsonText = objectMapper.writeValueAsString(obj);
			RedisClientUtil.hset(key, field, jsonText);
		} catch (Exception e) {
			log.error("jackson json 2 obj failed.", e);
		}
	}

	/**
	 * 模糊删除指定的key
	 */
	public static void delLike(String key) {
		// 模糊查找对应的key，然后删除
		Set<String> keySet = RedisClientUtil.keys(key);
		for (String strkey : keySet) {
			RedisClientUtil.del(strkey);
		}
	}

	/**
	 * HDEL 删除hash缓存的key
	 * 
	 * @param key
	 *            hashkey值
	 * @param filed
	 *            hashkey中字段名
	 */
	public static void hdel(String key, String... fields) {
		RedisClientUtil.hdel(key, fields);
	}
}
