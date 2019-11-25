package com.example.myrouter.utils;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 分发器
 * log代理
 * Created by lvxingxing on 19-11-22.
 */
public class RealLogProxy implements ILogProxy {

    public RealLogProxy() {
        /**
         * 默认添加log打印
         */
        LogInterceptorManager.addInterceptor(new PrinterInterceptor());
    }

    /**
     * 事件流入口
     * @param e log事件
     */
    private void startProcess(LogEvent e) {
        //根据临时配置是否需要disk
        if (!e.needDisk) {
            LogInterceptorManager.removeByKey(DiskInterceptor.KEY);
        }else{
            LogInterceptorManager.addInterceptor(new DiskInterceptor());
        }
        RealPrintChain rel = new RealPrintChain(0, e);
        rel.process(e);
    }

    @Override
    public void e(String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                .setType(LogType.ERROR)
                .setContent(log)
                .build();
        startProcess(logEvent);
    }

    @Override
    public void e(String tag, String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                .setType(LogType.ERROR)
                .setContent(log)
                .setTag(tag).build();
        startProcess(logEvent);
    }

    @Override
    public void i(String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.INFO)
                        .setContent(log)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void i(String tag, String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.INFO)
                        .setContent(log)
                        .setTag(tag)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void d(String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.INFO)
                        .setContent(log)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void d(String tag, String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.DEBUG)
                        .setContent(log)
                        .setTag(tag)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void d(String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.INFO)
                        .setContent(log)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void d(String tag, String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.DEBUG)
                        .setContent(log)
                        .setTag(tag)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void d(boolean needDisk, String tag, String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.DEBUG)
                        .setContent(log)
                        .setTag(tag)
                        .setNeedDisk(needDisk)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void w(String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.WARN)
                        .setContent(log)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void w(String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.WARN)
                        .setContent(log)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void w(String tag, String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.WARN)
                        .setContent(log)
                        .setTag(tag)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void w(String tag, String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.DEBUG)
                        .setContent(log)
                        .setTag(tag)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void v(String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.VERBOSE)
                        .setContent(log)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void v(String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.VERBOSE)
                        .setContent(log)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void v(String tag, String log) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.VERBOSE)
                        .setContent(log)
                        .setTag(tag)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void v(String tag, String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.VERBOSE)
                        .setContent(log)
                        .setTag(tag)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void e(String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.ERROR)
                        .setContent(log)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void e(String tag, String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.DEBUG)
                        .setContent(log)
                        .setTag(tag)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void i(String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.INFO)
                        .setContent(log)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }

    @Override
    public void i(String tag, String log, boolean force) {
        LogEvent logEvent =
                new LogEvent.EventBuilder()
                        .setType(LogType.DEBUG)
                        .setContent(log)
                        .setTag(tag)
                        .setForce(force)
                        .build();
        startProcess(logEvent);
    }
}
