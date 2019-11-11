package com.example.myrouter.utils;

/**
 * 根据Event类型，分发做不同处理
 */
interface Dispatcher{
    void resolve(LogType type, Event event);
}