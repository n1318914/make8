package com.yundaren.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActiveCodeCache {

	private static ActiveCodeCache instance = new ActiveCodeCache();
	// 邮箱验证缓存
	private Map<Object, CacheTemplate> codeCache = new ConcurrentHashMap<Object, CacheTemplate>();
	// 24小时
	private static final long TIME_RUN = 1000 * 60 * 60 * 24;
	private static ListenCache listenCache = instance.new ListenCache();
	private static Thread listenthead = new Thread(listenCache);
	static {
		if (!listenthead.isAlive()) {
			listenthead.setName("[Thread MailCode]");
			listenthead.start();
		}
	}

	public static ActiveCodeCache getInstance() {
		if (instance == null) {
			instance = new ActiveCodeCache();
		}
		return instance;
	}

	public void remove(Object key) {
		log.info("remvoe success fo code cache key=" + key);
		codeCache.remove(key);
	}

	public void add(Object key, Object value) {
		CacheTemplate template = new CacheTemplate();
		template.setCode(value);
		template.setTime(System.currentTimeMillis());
		codeCache.put(key, template);
	}

	public Object get(Object key) {
		CacheTemplate cacheTemplate = codeCache.get(key);
		if (null == cacheTemplate) {
			return null;
		}
		Object code = cacheTemplate.getCode();
//		remove(key);
		return code;
	}

	public boolean containsKey(Object key) {
		return codeCache.containsKey(key);
	}

	private class ListenCache implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(TIME_RUN);
					log.info("current code cache:" + codeCache);
					Object objs[] = codeCache.keySet().toArray();
					if (null == objs) {
						continue;
					}
					int size = objs.length;
					for (int i = size - 1; i >= 0; i--) {
						Object key = objs[i];
						CacheTemplate cacheTemplate = codeCache.get(key);
						if (cacheTemplate != null) {
							long addTime = cacheTemplate.getTime();
							long currentTime = System.currentTimeMillis();
							Long time = currentTime - addTime;
							if (time >= TIME_RUN) {
								remove(key);
							}
						}
					}
				} catch (Exception e) {
					codeCache.clear();
					log.error("listen cache error", e);
				}
			}
		}
	}

	@Data
	private class CacheTemplate implements Serializable {
		private static final long serialVersionUID = 489520775365156709L;
		private Object code;
		private long time;
	}
}
