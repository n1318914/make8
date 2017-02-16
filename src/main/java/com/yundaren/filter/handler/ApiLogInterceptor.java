package com.yundaren.filter.handler;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ApiLogInterceptor extends HandlerInterceptorAdapter {

    private ThreadLocal<Stopwatch> stopwatchThreadLocal = new ThreadLocal<Stopwatch>(){
        @Override
        protected Stopwatch initialValue() {
            return Stopwatch.createUnstarted();
        }
    };

    public ApiLogInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Stopwatch stopwatch = stopwatchThreadLocal.get();
        stopwatch.reset();
        stopwatch.start();
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Stopwatch stopwatch = stopwatchThreadLocal.get();
        stopwatch.stop();
        long costTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        ApiLogger.log(request, response, costTime);
        super.postHandle(request, response, handler, modelAndView);
    }

}
