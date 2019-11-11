package com.example.myrouter.utils;

import android.util.Log;


public class Xlog {

    public final static String TAG = "XLog";

    public static boolean sDebug = false;

    public static String sTag;

    private static RealLogProxy realLogProxy;

    public Xlog(){}

    /**
     * //------------------全局变量配置表----------------------------------------
     *  是否只有debug打印
     *
     */
    public static boolean globalOnlyDebug;
    /**
     * 是否缓存本地disk
     */
    public static boolean globalNeedDisk;


    /**
     * //----------------------------------------------------------------------
     */


    /**
     * 初始化--进行全局配置
     * @param config
     */
    public static void initGlobal(LogConfig config){
        realLogProxy = new RealLogProxy();
        InterceptorManager.addInterceptor(new DefaultInterceptor());
        if (null == config){
            return;
        }
        globalOnlyDebug = config.isOnlyDebug;
        if (null != config.mTag && config.mTag.length() > 0){
            sTag = config.mTag;
        }
        globalNeedDisk = config.isDisk;
        if (globalNeedDisk){
            InterceptorManager.addInterceptor(new DiskInterceptor());
        }
    }


    public static void init(boolean debug) {
        init("XLog", debug);
        initGlobal(new LogConfig.ConfigBuilder().setDebug(debug).setDisk(false).build());
    }

    public static void init(String defaultTag, boolean debug) {
        sTag = defaultTag;
        sDebug = debug;
    }

    public static void e(String tag, String log) {
        if (null != realLogProxy){
            realLogProxy.e(tag,log);
            return;
        }
        if(sDebug){
            Log.e(tag, log);
        }
    }

    public static void e(String log) {
        if (null != realLogProxy){
            realLogProxy.e(log);
            return;
        }
        if(sDebug) {
            Log.e(sTag, log);
        }
    }

    public static void i(String tag, String log) {
        if (null != realLogProxy){
            realLogProxy.i(tag,log);
            return;
        }
        if(sDebug){
            Log.i(tag, log);
        }
    }

    public static void i(String log) {
        if (null != realLogProxy){
            realLogProxy.i(log);
            return;
        }
        if(sDebug) {
            Log.i(sTag, log);
        }
    }

    public static void d(String tag, String log) {
        if (null != realLogProxy){
            realLogProxy.d(tag,log);
            return;
        }
        if(sDebug){
            Log.d(tag, log);
        }
    }

    public static void d(boolean needDisk,String tag,String log){
        if (null != realLogProxy){
            realLogProxy.d(needDisk,tag,log);
        }
    }

    public static void d(String log) {
        if (null != realLogProxy){
            realLogProxy.d(log);
            return;
        }
        if(sDebug) {
            Log.d(sTag, log);
        }
    }

    public static void w(String tag, String log) {
        if (null != realLogProxy){
            realLogProxy.w(tag,log);
            return;
        }
        if(sDebug){
            Log.w(tag, log);
        }
    }

    public static void w(String log) {
        if (null != realLogProxy){
            realLogProxy.w(log);
            return;
        }
        if(sDebug) {
            Log.w(sTag, log);
        }
    }

    public static void v(String tag, String log) {
        if (null != realLogProxy){
            realLogProxy.v(tag,log);
            return;
        }
        if(sDebug){
            Log.v(tag, log);
        }
    }

    public static void v(String log) {
        if (null != realLogProxy){
            realLogProxy.v(log);
            return;
        }
        if(sDebug) {
            Log.v(sTag, log);
        }
    }

    public static void e(String log, boolean force){
        e(sTag, log, force);
    }

    public static void e(String tag, String log, boolean force){
        if(force){
            Log.e(tag, log);
        } else {
            e(tag, log);
        }
    }

    public static void i(String log, boolean force){
        i(sTag, log, force);
    }

    public static void i(String tag, String log, boolean force){
        if(force){
            Log.i(tag, log);
        } else {
            i(tag, log);
        }
    }

    public static void d(String log, boolean force){
        d(sTag, log, force);
    }

    public static void d(String tag, String log, boolean force){
        if(force){
            Log.d(tag, log);
        } else {
            d(tag, log);
        }
    }

    public static void w(String log, boolean force){
        w(sTag, log, force);
    }

    public static void w(String tag, String log, boolean force){
        if(force){
            Log.w(tag, log);
        } else {
            w(tag, log);
        }
    }

    public static void v(String log, boolean force){
        v(sTag, log, force);
    }

    public static void v(String tag, String log, boolean force){
        if(force){
            Log.v(tag, log);
        } else {
            v(tag, log);
        }
    }

}
