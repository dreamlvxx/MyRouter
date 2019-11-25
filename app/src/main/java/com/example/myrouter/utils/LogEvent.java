package com.example.myrouter.utils;



/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * log事件
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class LogEvent {
    private LogEvent(EventBuilder builder){
        this.level = builder.level;
        this.type = builder.type;
        this.tag = builder.tag;
        this.content = builder.content;
        this.isDebug = builder.isDebug;
        this.needDisk = builder.needDisk;
        this.force = builder.force;
    }

    /**
     * 日志输出级别
     */
    public int level;
    /**
     * 日志输出类别
     */
    public LogType type;
    /**
     * 日志输出主体内容
     */
    public String content;
    /**
     * 日志输出tag
     */
    public String tag;
    /**
     * 是否需要写磁盘
     */
    public boolean needDisk;
    /**
     * 是否debug环境输出
     */
    public boolean isDebug;
    /**
     * 是否强制输出
     */
    public boolean force;

    public static class EventBuilder{
        private int level = FileLogEntity.Level.LEVEL_HIGH;
        private LogType type = LogType.DEBUG;
        private String content = "";
        private String tag = XLog.sTag;
        private boolean needDisk = XLog.globalNeedDisk;
        private boolean isDebug = XLog.sDebug;
        private boolean force = false;

        public EventBuilder setType(LogType type) {
            this.type = type;
            return this;
        }

        public EventBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public EventBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public EventBuilder setNeedDisk(boolean needDisk) {
            this.needDisk = needDisk;
            return this;
        }

        public EventBuilder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public EventBuilder setForce(boolean force) {
            this.force = force;
            return this;
        }

        public EventBuilder setLevel(int level) {
            this.level = level;
            return this;
        }

        public LogEvent build(){
            return new LogEvent(this);
        }
    }
}