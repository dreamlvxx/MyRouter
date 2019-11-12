package com.example.myrouter.utils;

/**
 * 根据Event类型，分发做不同处理
 */
interface Dispatcher{
    /**
     * 根据type进行处理
     * @param type
     * @param event
     */
    void resolve(LogType type, Event event);
}