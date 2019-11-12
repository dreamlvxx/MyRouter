package com.example.myrouter.utils;

/**
 * 事件流拦截器
 */
public interface LogInterceptor {
    /**
     * 拦截器名称
     * @return name
     */
    String name();

    /**
     * 拦截，处理
     * @param chain 调用链
     * @return 处理后的event
     */
    String intercept(PrintChain chain);

    /**
     * 调用链接口
     */
    interface PrintChain{
        /**
         * 得到一个事件
         * @return event
         */
        Event getEvent();

        /**
         * 调用链的处理
         * @param event 事件
         * @return 处理后的event
         */
        String process(Event event);
    }
}