package com.example.myrouter.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.myrouter.utils.Constant.VALID_FILE_PATH;

/**
 * Copyright (C) 2019, Xiaomi Inc. All rights reserved.
 * <p>
 * Description:
 * 基础Log
 * @author yanyunpeng
 * @date 19-3-25.
 */
public class XLog {
    public static final String TAG = "XLog";

    public static WeakReference<Context> mContext;

    public static boolean sDebug = false;

    public static String sTag = TAG;

    public static String mDiskFileDirPath = "";

    /**
     * 使用如下方法进行初始化时，交给代理处理。
     * {@link #init(boolean, Context, LogConfig)}
     */
    private static RealLogProxy realLogProxy;

    private XLog(){}

    /**
     * 是否缓存本地disk
     */
    public static boolean globalNeedDisk = false;

    private static boolean validParams(LogConfig config){
        if(null == config){
            Log.e(TAG,"初始化失败，请检查config参数");
            return false;
        }

        if(TextUtils.isEmpty(config.mPath)){
            Log.e(TAG,"请配置log输出文件路径");
            return false;
        }

        Pattern pattern = Pattern.compile(VALID_FILE_PATH);
        Matcher matcher = pattern.matcher(config.mPath);
        if(!matcher.matches()){
            Log.e(TAG,"请检查path合法性");
            return false;
        }
        return true;
    }

    /**
     * 初始化,进行全局配置
     * @param debug 是否debug模式
     * @param context 上下文，目前用于寻找本地目录
     * @param config 配置参数
     */
    public static void init(boolean debug, Context context, LogConfig config){
        if(!validParams(config)){
            //如果初始化失败，进行默认配置
            init(debug);
            return;
        }
        realLogProxy = new RealLogProxy();
        mContext = new WeakReference<>(context);
        sDebug = debug;
        if (!TextUtils.isEmpty(config.mTag)){
            sTag = config.mTag;
        }
        if(!TextUtils.isEmpty(config.mPath)){
            mDiskFileDirPath = config.mPath;
        }else{
            mDiskFileDirPath = context.getFilesDir().getPath() + "/MiLog/";
        }
        globalNeedDisk = config.mDisk;
    }

    public static void init(boolean debug) {
        init("XLog", debug);
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

    public static void d(boolean needDisk, String tag, String log){
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
        if (null != realLogProxy){
            realLogProxy.e(log,force);
            return;
        }
        e(sTag, log, force);
    }

    public static void e(String tag, String log, boolean force){
        if(null != realLogProxy){
            realLogProxy.e(tag,log,force);
            return;
        }
        if(force){
            Log.e(tag, log);
        } else {
            e(tag, log);
        }
    }

    public static void i(String log, boolean force){
        if(null !=realLogProxy){
            realLogProxy.i(log,force);
            return;
        }
        i(sTag, log, force);
    }

    public static void i(String tag, String log, boolean force){
        if(null != realLogProxy){
            realLogProxy.i(tag,log,force);
            return;
        }
        if(force){
            Log.i(tag, log);
        } else {
            i(tag, log);
        }
    }

    public static void d(String log, boolean force){
        if(null != realLogProxy){
            realLogProxy.d(log,force);
            return;
        }
        d(sTag, log, force);
    }

    public static void d(String tag, String log, boolean force){
        if(null != realLogProxy){
            realLogProxy.d(tag,log,force);
            return;
        }
        if(force){
            Log.d(tag, log);
        } else {
            d(tag, log);
        }
    }

    public static void w(String log, boolean force){
        if (null != realLogProxy){
            realLogProxy.w(log,force);
        }
        w(sTag, log, force);
    }

    public static void w(String tag, String log, boolean force){
        if(null != realLogProxy){
            realLogProxy.w(tag,log,force);
            return;
        }
        if(force){
            Log.w(tag, log);
        } else {
            w(tag, log);
        }
    }

    public static void v(String log, boolean force){
        if (null != realLogProxy){
            realLogProxy.v(log,force);
            return;
        }
        v(sTag, log, force);
    }

    public static void v(String tag, String log, boolean force){
        if (null != realLogProxy){
            realLogProxy.v(tag,log,force);
        }
        if(force){
            Log.v(tag, log);
        } else {
            v(tag, log);
        }
    }
}
