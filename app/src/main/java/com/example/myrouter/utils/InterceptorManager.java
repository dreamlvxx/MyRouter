package com.example.myrouter.utils;

import java.util.HashMap;

/**
 * 全局拦截器管理
 */
public class InterceptorManager {
    private static HashMap<String,LogInterceptor> strategyArrayList = new HashMap<>();

    public static void addInterceptor(LogInterceptor s){
        strategyArrayList.put(s.name(),s);
    }

    public static void clear(){
        strategyArrayList.clear();
    }

    public static void remove(String key){
        strategyArrayList.remove(key);
    }

    public static HashMap<String,LogInterceptor> getMap(){
        return strategyArrayList;
    }

    public static void getInterceptor(String key){
        strategyArrayList.get(key);
    }
}