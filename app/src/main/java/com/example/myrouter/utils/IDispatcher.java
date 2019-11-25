package com.example.myrouter.utils;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 分发器
 * 根据Event类型，分发做不同处理
 * Created by lvxingxing on 19-11-22.
 */
interface IDispatcher {
    /**
     * 根据type进行处理
     * @param type
     * @param logEvent
     */
    void resolve(LogType type, LogEvent logEvent);
}