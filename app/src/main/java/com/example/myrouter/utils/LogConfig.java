package com.example.myrouter.utils;

/**
 * 全局配置
 */
public class LogConfig {
    private LogConfig(ConfigBuilder builder){
        this.isOnlyDebug = builder.isDebug;
        this.isDisk = builder.isDisk;
        this.mTag = builder.tag;
    }
    //是否只有debug打印
    public boolean isOnlyDebug;

    //是否打印本地disk
    public boolean isDisk ;

    //全局tag
    public String mTag ;

    public static class ConfigBuilder{
        boolean isDebug;
        boolean isDisk;
        String tag;

        public ConfigBuilder setDebug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public ConfigBuilder setDisk(boolean disk) {
            isDisk = disk;
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
