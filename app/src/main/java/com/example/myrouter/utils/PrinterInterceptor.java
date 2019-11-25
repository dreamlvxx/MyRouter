package com.example.myrouter.utils;

import android.text.TextUtils;
import android.util.Log;


/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 分发器
 * logcat输出处理器
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class PrinterInterceptor implements ILogInterceptor, IDispatcher {

    public static final String KEY = "default";

    @Override
    public String name() {
        return KEY;
    }

    @Override
    public String intercept(PrintChain chain) {
        LogEvent e = chain.getEvent();
        if(e.force || e.isDebug){
            resolve(e.type,e);
        }
        return chain.process(e);
    }

    @Override
    public void resolve(LogType type, LogEvent logEvent) {
        String finalTag = XLog.sTag;
        if (!TextUtils.isEmpty(logEvent.tag)){
            finalTag = logEvent.tag;
        }
        switch (type){
            case INFO:
                Log.i(finalTag, logEvent.content);
                break;
            case WARN:
                Log.w(finalTag, logEvent.content);
                break;
            case DEBUG:
                Log.d(finalTag, logEvent.content);
                break;
            case ERROR:
                Log.e(finalTag, logEvent.content);
                break;
            case VERBOSE:
                Log.v(finalTag, logEvent.content);
                break;
            default:
                break;
        }
    }
}