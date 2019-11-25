package com.example.myrouter.utils;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 分发器
 * log事件拦截器接口
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public interface ILogInterceptor {
    /**
     * 拦截器名称
     * @return name
     */
    String name();

    /**
     * 拦截进行处理
     * @param chain 调用链结点
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
        LogEvent getEvent();

        /**
         * 调用链的处理
         * @param logEvent 事件
         * @return 处理后的event
         */
        String process(LogEvent logEvent);
    }
}