package com.yundaren.filter.handler.cache;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.yundaren.common.util.RedisClientUtil;
import com.yundaren.common.util.RedisDaoUtil;

@Component
@Aspect
@Slf4j
public class RedisCacheAspect {

	/**
	 * 定义缓存逻辑
	 */
	@Around("@annotation(com.yundaren.filter.handler.cache.RedisCacheable)")
	public Object cache(ProceedingJoinPoint pjp) {
		Object result = null;
		Boolean cacheEnable = RedisClientUtil.isEnable();
		// 判断是否开启缓存
		if (!cacheEnable) {
			try {
				result = pjp.proceed();
			} catch (Throwable e) {
				log.error("RedisCacheAspect cache skip", e);
			}
			return result;
		}

		Method method = getMethod(pjp);
		RedisCacheable cacheable = method.getAnnotation(RedisCacheable.class);

		String fieldKey = parseKey(cacheable.field(), method, pjp.getArgs());

		// 获取方法的返回类型,让缓存可以返回正确的类型
		Class returnType = ((MethodSignature) pjp.getSignature()).getReturnType();

		// 使用redis 的hash进行存取，易于管理
		result = RedisDaoUtil.hget(cacheable.key(), fieldKey, returnType);

		if (result == null) {
			try {
				result = pjp.proceed();
				Assert.notNull(fieldKey);
				RedisDaoUtil.hset(cacheable.key(), fieldKey, result);
			} catch (Throwable e) {
				log.error("RedisCacheAspect cache done", e);
			}
		}
		return result;
	}

	/**
	 * 清除缓存
	 */
	@Around(value = "@annotation(com.yundaren.filter.handler.cache.RedisCacheEvict)")
	public Object evict(ProceedingJoinPoint pjp) {
		Object result = null;
		Boolean cacheEnable = RedisClientUtil.isEnable();
		// 判断是否开启缓存
		if (!cacheEnable) {
			try {
				result = pjp.proceed();
			} catch (Throwable e) {
				log.error("RedisCacheAspect evict skip", e);
			}
			return result;
		}

		Method method = getMethod(pjp);
		RedisCacheEvict cacheable = method.getAnnotation(RedisCacheEvict.class);

		// 允许同时失效多个key下的field
		for (String field : cacheable.field()) {
			String fieldKey = parseKey(field, method, pjp.getArgs());
			if(StringUtils.isEmpty(fieldKey)){
				continue;
			}
			// 失效hash field
			for (String key : cacheable.key()) {
				RedisDaoUtil.hdel(key, fieldKey);
			}
		}

		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			log.error("RedisCacheAspect evict done", e);
		}
		return result;
	}

	/**
	 * 获取被拦截方法对象
	 * 
	 * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上 所以应该使用反射获取当前对象的方法对象
	 */
	private Method getMethod(ProceedingJoinPoint pjp) {
		Method method = null;
		try {
//			method = pjp.getTarget().getClass().getMethod(pjp.getSignature().getName(), argTypes);

			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			method = methodSignature.getMethod();
			if (method.getDeclaringClass().isInterface()) {
				method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
			}

		} catch (Exception e) {
			log.error("", e);
		}
		return method;
	}

	/**
	 * 获取缓存的key key 定义在注解上，支持SPEL表达式
	 * 
	 * @param pjp
	 * @return
	 */
	private String parseKey(String key, Method method, Object[] args) {

		// 获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = u.getParameterNames(method);

		// 使用SPEL进行key的解析
		ExpressionParser parser = new SpelExpressionParser();
		// SPEL上下文
		StandardEvaluationContext context = new StandardEvaluationContext();
		// 把方法参数放入SPEL上下文中
		for (int i = 0; i < paraNameArr.length; i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		return parser.parseExpression(key).getValue(context, String.class);
	}
}
