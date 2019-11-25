package com.example.myrouter.utils;



import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.myrouter.utils.DiskIO.write;


/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 打印磁盘的拦截处理器
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class DiskInterceptor implements ILogInterceptor {

    public static final String KEY = "disk";

    @Override
    public String name() {
        return KEY;
    }

    @Override
    public String intercept(PrintChain chain) {
        LogEvent e = chain.getEvent();
        //进行内部打印
        f(e.level,e.tag,e.content);
        return chain.process(e);
    }

    private static void f(int level, String tag, String content){
        if(null == content){
            return;
        }
        Thread t = Thread.currentThread();
        FileLogEntity lo = new FileLogEntity.LogEntityBuilder()
                .setContent(content)
                .setLevel(level)
                .setTid(t.getId())
                .setTag(tag)
                .setTime(new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()))
                .build();
        write(XLog.mDiskFileDirPath, lo.toString());
    }
}
