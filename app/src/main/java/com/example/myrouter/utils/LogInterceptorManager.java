package com.example.myrouter.utils;

import java.util.HashMap;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 分发器
 * 全局的拦截器
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class LogInterceptorManager {
    private static HashMap<String, ILogInterceptor> strategyArrayList = new HashMap<>();

    public static void addInterceptor(ILogInterceptor s){
        strategyArrayList.put(s.name(),s);
    }

    public static void clear(){
        strategyArrayList.clear();
    }

    public static void removeByKey(String key){
        strategyArrayList.remove(key);
    }

    public static HashMap<String, ILogInterceptor> getMap(){
        return strategyArrayList;
    }

    public static void getInterceptorByKey(String key){
        strategyArrayList.get(key);
    }
}