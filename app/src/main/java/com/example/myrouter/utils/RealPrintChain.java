package com.example.myrouter.utils;

import java.util.Set;

/**
 * 调用链默认实现
 */
public class RealPrintChain implements LogInterceptor.PrintChain {
    int index;
    Event event;

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
     * 调用连递归
     * @param event
     * @return
     */
    private String processInner(Event event){
        if (null == event){
            return "";
        }
        if (index > InterceptorManager.strategyArrayList.size() - 1){
            return event.content;
        }
        RealPrintChain chain = new RealPrintChain(index + 1,event);
        Set<String> keys = InterceptorManager.strategyArrayList.keySet();
        Object[] arr = keys.toArray();
        String key = (String) arr[index];
        LogInterceptor s = InterceptorManager.strategyArrayList.get(key);
        if(null == s){
            return "";
        }
        return s.intercept(chain);
    }
}
