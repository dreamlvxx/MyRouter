package com.example.myrouter.utils;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 分发器
 * 全局配置参数
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class LogConfig {

    /**
     * 是否只有debug打印
     */
    public boolean mDebug;

    /**
     * 是否打印本地disk
     */
    public boolean mDisk;

    /**
     * 全局Tag
     */
    public String mTag;

    /**
     * 用户自定义path
     */
    public String mPath;

    private LogConfig(ConfigBuilder builder){
        this.mDebug = builder.debug;
        this.mDisk = builder.disk;
        this.mTag = builder.tag;
//        this.mPath = builder.path;
    }

    public static class ConfigBuilder{
//        private String path = Constant.DEFAULT_LOGOUT_PATH;
        private boolean debug = false;
        private boolean disk = false;
        private String tag = XLog.TAG;

        public ConfigBuilder setPath(String path) {
//            this.path = path;
            return this;
        }

        public ConfigBuilder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public ConfigBuilder setDisk(boolean disk) {
            this.disk = disk;
            return this;
        }

        public ConfigBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public LogConfig build(){
            return new LogConfig(this);
        }
    }

}
