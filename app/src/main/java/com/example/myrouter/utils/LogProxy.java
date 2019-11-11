package com.example.myrouter.utils;

/**
 * Xlog代理层
 */
public interface LogProxy{
    void e(String log);
    void e(String tag, String log);

    void i(String log);
    void i(String tag, String log);

    void d(String log);
    void d(String tag, String log);
    void d(String log, boolean force);
    void d(String tag, String log, boolean force);
    void d(boolean needDisk,String tag,String log);

    void w(String log);
    void w(String log, boolean force);
    void w(String tag, String log);
    void w(String tag, String log, boolean force);

    void v(String log);
    void v(String log, boolean force);
    void v(String tag, String log);
    void v(String tag, String log, boolean force);

    void e(String log, boolean force);
    void e(String tag, String log, boolean force);

    void i(String log, boolean force);
    void i(String tag, String log, boolean force);


}