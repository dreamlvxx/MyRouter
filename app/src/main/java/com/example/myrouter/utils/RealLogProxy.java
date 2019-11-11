package com.example.myrouter.utils;


/**
 * 主要负责生成一个Event，然后走调用连
 */
public class RealLogProxy implements LogProxy{

    /**
     * 事件流入口
     * @param e
     */
    public void startProcess(Event e) {
        //根据临时配置是否需要disk
        if (!e.needDisk) {
            InterceptorManager.remove(DiskInterceptor.KEY);
        }
        RealPrintChain rel = new RealPrintChain(0, e);
        rel.process(e);
    }

    Event.EventBuilder builder = new Event.EventBuilder();

    @Override
    public void e(String log) {
        Event event = new Event.EventBuilder().setType(LogType.ERROR)
                .setContent(log).build();
        startProcess(event);
    }

    @Override
    public void e(String tag, String log) {
        Event event = new Event.EventBuilder().setType(LogType.ERROR)
                .setContent(log)
                .setTag(tag).build();

    }

    @Override
    public void i(String log) {
        builder.setType(LogType.INFO)
                .setContent(log);
    }

    @Override
    public void i(String tag, String log) {
        builder.setType(LogType.INFO)
                .setTag(tag)
                .setContent(log);
    }

    @Override
    public void d(String log) {
        Event event = new Event.EventBuilder().setType(LogType.DEBUG)
                .setDebug(true)
                .setForce(false)
                .setContent(log).build();
        startProcess(event);
    }

    @Override
    public void d(String tag, String log) {
        Event event = new Event.EventBuilder().setType(LogType.DEBUG)
                .setTag(tag)
                .setDebug(true)
                .setForce(false)
                .setContent(log).build();
        startProcess(event);
    }

    @Override
    public void d(String log, boolean force) {
        builder.setType(LogType.DEBUG)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void d(String tag, String log, boolean force) {
        builder.setType(LogType.DEBUG)
                .setTag(tag)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void d(boolean needDisk, String tag, String log) {
        builder.setType(LogType.DEBUG)
                .setNeedDisk(needDisk)
                .setTag(tag)
                .setContent(log);
    }

    @Override
    public void w(String log) {
        builder.setType(LogType.WARN)
                .setContent(log);
    }

    @Override
    public void w(String log, boolean force) {
        builder.setType(LogType.WARN)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void w(String tag, String log) {
        builder.setType(LogType.WARN)
                .setTag(tag)
                .setContent(log);
    }

    @Override
    public void w(String tag, String log, boolean force) {
        builder.setType(LogType.WARN)
                .setTag(tag)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void v(String log) {
        builder.setType(LogType.VERBOSE)
                .setContent(log);
    }

    @Override
    public void v(String log, boolean force) {
        builder.setType(LogType.VERBOSE)
                .setContent(log);
    }

    @Override
    public void v(String tag, String log) {
        builder.setType(LogType.VERBOSE)
                .setTag(tag)
                .setContent(log);
    }

    @Override
    public void v(String tag, String log, boolean force) {
        builder.setType(LogType.VERBOSE)
                .setTag(tag)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void e(String log, boolean force) {
        builder.setType(LogType.ERROR)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void e(String tag, String log, boolean force) {
        builder.setType(LogType.ERROR)
                .setTag(tag)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void i(String log, boolean force) {
        builder.setType(LogType.INFO)
                .setContent(log)
                .setForce(force);
    }

    @Override
    public void i(String tag, String log, boolean force) {
        builder.setType(LogType.INFO)
                .setTag(tag)
                .setContent(log)
                .setForce(force);
    }
}
