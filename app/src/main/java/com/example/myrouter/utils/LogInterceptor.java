package com.example.myrouter.utils;

/**
 * 事件流拦截器
 */
public interface LogInterceptor {
    /**
     * 拦截器名称
     * @return
     */
    String name();

    /**
     * 拦截，处理
     * @param chain
     * @return
     */
    String intercept(PrintChain chain);
    //调用链
    interface PrintChain{
        Event getEvent();
        String process(Event event);
    }
}