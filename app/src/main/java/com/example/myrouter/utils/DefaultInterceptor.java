package com.example.myrouter.utils;


import android.util.Log;



/**
 * 默认拦截器实现
 */

public class DefaultInterceptor implements LogInterceptor,Dispatcher{

    public static final String KEY = "default";

    @Override
    public String name() {
        return KEY;
    }

    @Override
    public String intercept(PrintChain chain) {
        //先取到这个事件，进行处理
        Event e = chain.getEvent();
//        Log.e(TAG, "这是default打印的东西" + e.content);
//        e.content += "default返回值";
        if(e.force){
            resolve(e.type,e);
        }else if (e.isDebug){
            resolve(e.type,e);
        }
        return chain.process(e);
    }

    @Override
    public void resolve(LogType type, Event event) {
        String finalTag = Xlog.sTag;
        if (null != event.tag && event.tag.length() > 0){
            finalTag = event.tag;
        }
        switch (type){
            case INFO:
                Log.i(finalTag,event.content);
                break;
            case WARN:
                Log.w(finalTag,event.content);
                break;
            case DEBUG:
                Log.d(finalTag,event.content);
                break;
            case ERROR:
                Log.e(finalTag,event.content);
                break;
            case VERBOSE:
                Log.v(finalTag,event.content);
                break;
            default:
                break;
        }
    }
}