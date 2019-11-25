package com.example.myrouter.utils;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 输出到文件的log的结构体
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class FileLogEntity {
    private String time ;
    private String tag;
    private int level;
    private int pid;
    private long tid;
    private String content;

    public FileLogEntity(LogEntityBuilder config) {
        this.time = config.time;
        this.tag = config.tag;
        this.level = config.level;
        this.pid = config.pid;
        this.tid = config.tid;
        this.content = config.content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("Time:")
                .append(time)
                .append(" | ")
                .append("Tag:")
                .append(tag)
                .append(" | ")
                .append("Tid:")
                .append(tid)
                .append(" | ")
                .append("Content:")
                .append(content)
                .append("}")
                .append("\n");
        return sb.toString();
    }



    interface Level{
        int LEVEL_HIGH = 0;
        int LEVEL_MIDIUM = 1;
        int LEVEL_LOW = 2;
    }
    static class LogEntityBuilder {
        String time ;
        String tag;
        int level;
        int pid;
        long tid;
        String content;

        public LogEntityBuilder setTime(String time) {
            this.time = time;
            return this;
        }

        public LogEntityBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public LogEntityBuilder setLevel(int level) {
            this.level = level;
            return this;
        }

        public LogEntityBuilder setPid(int pid) {
            this.pid = pid;
            return this;
        }

        public LogEntityBuilder setTid(long tid) {
            this.tid = tid;
            return this;
        }

        public LogEntityBuilder setContent(String content) {
            this.content = content.replaceAll(" ","");
            return this;
        }

        public FileLogEntity build(){
            return new FileLogEntity(this);
        }
    }
}