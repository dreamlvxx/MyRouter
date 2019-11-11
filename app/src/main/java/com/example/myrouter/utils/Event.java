package com.example.myrouter.utils;

/**
 * 事件流
 */
public class Event{
    private Event(EventBuilder buidler){
        this.type = buidler.type;
        this.needDisk = buidler.needDisk;
        this.tag = buidler.tag;
        this.content = buidler.content;
        this.isDebug = buidler.isDebug;
        this.force = buidler.force;
    }

    public LogType type;
    public String content;
    public String tag;
    public boolean needDisk;
    public boolean isDebug;
    public boolean force;

    public static class EventBuilder{
        private LogType type;
        private String content;
        private String tag = Xlog.sTag;
        private boolean needDisk;
        private boolean isDebug;
        private boolean force;

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

        public Event build(){
            return new Event(this);
        }
    }
}