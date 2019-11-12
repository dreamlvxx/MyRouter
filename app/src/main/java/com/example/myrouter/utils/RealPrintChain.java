package com.example.myrouter.utils;

import java.util.Set;

/**
 * 调用链默认实现
 */
public class RealPrintChain implements LogInterceptor.PrintChain {
    private int index;
    private Event event;

    public RealPrintChain(int index, Event event) {
        this.index = index;
        this.event = event;
    }

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public String process(Event event) {
        return processInner(event);
    }

    /**
     * 调用链进行递归
     * @param event 要处理的事件
     * @return 返回一个处理后的事件，这里暂时没有用
     */
    private String processInner(Event event){
        if (null == event){
            return "";
        }
        if (index > InterceptorManager.getMap().size() - 1){
            return event.content;
        }
        RealPrintChain chain = new RealPrintChain(index + 1,event);
        Set<String> keys = InterceptorManager.getMap().keySet();
        Object[] arr = keys.toArray();
        String key = (String) arr[index];
        LogInterceptor s = InterceptorManager.getMap().get(key);
        if(null == s){
            return "";
        }
        return s.intercept(chain);
    }
}
