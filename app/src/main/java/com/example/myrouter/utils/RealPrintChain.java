package com.example.myrouter.utils;

import java.util.Set;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 分发器
 * 调用链的实现，负责对每一个logEvent进行处理。
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class RealPrintChain implements ILogInterceptor.PrintChain {
    private int index;
    private LogEvent logEvent;

    public RealPrintChain(int index, LogEvent logEvent) {
        this.index = index;
        this.logEvent = logEvent;
    }

    @Override
    public LogEvent getEvent() {
        return logEvent;
    }

    @Override
    public String process(LogEvent logEvent) {
        return processInner(logEvent);
    }

    /**
     * 调用链进行递归
     * @param logEvent 要处理的事件
     * @return 返回一个处理后的事件，这里暂时没有用
     */
    private String processInner(LogEvent logEvent){
        if (null == logEvent){
            return "";
        }
        if (index > LogInterceptorManager.getMap().size() - 1){
            return logEvent.content;
        }
        //进行intercept处理
        Set<String> keys = LogInterceptorManager.getMap().keySet();
        Object[] arr = keys.toArray();
        String key = (String) arr[index];
        ILogInterceptor s = LogInterceptorManager.getMap().get(key);
        if(null == s){
            return "";
        }
        RealPrintChain chain = new RealPrintChain(index + 1, logEvent);
        return s.intercept(chain);
    }
}
