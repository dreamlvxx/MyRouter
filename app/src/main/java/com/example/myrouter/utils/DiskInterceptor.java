package com.example.myrouter.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.myrouter.utils.DiskIO.DEFAULT_LOGOUT_PATH;
import static com.example.myrouter.utils.DiskIO.write;

/**
 * 磁盘打印的拦截器
 */
public class DiskInterceptor implements LogInterceptor {

    public static final String KEY = "disk";

    @Override
    public String name() {
        return KEY;
    }

    @Override
    public String intercept(PrintChain chain) {
        Event e = chain.getEvent();
        //这里打印到file
        f(DiskIO.Level.LEVEL_HIGH,e.tag,chain.getEvent().content);
        return chain.process(e);
    }

    public static void f(int level,String tag,String content){
        f(level,tag,DEFAULT_LOGOUT_PATH,content);
    }

    public static void f(int level,String tag,String path,String content){
        if(null == path || null == content){
            return;
        }
        Thread t = Thread.currentThread();
        DiskIO.LogLocal lo = new DiskIO.Config()
                .setContent(content)
                .setLevel(level)
                .setTid(t.getId())
                .setTag(tag)
                .setTime(new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()))
                .build();
//        Log.e(TAG, "f: " + lo.toString());
        write(path, lo.toString());
    }
}
