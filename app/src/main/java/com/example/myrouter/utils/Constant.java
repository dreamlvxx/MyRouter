package com.example.myrouter.utils;


import android.content.Context;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 *
 * @author lvxingxing
 * @date 19-11-22
 */
public class Constant {
    /**
     * 输出文件夹名称
     */
//    public static final String DEFAULT_LOGOUT_PATH =  Context.getFilesDir().getPath() + "/MiLog/";
    /**
     * 输出文件名称前缀
     */
    public static final String FILE_NAME = "milog";

    /**
     * 输出日志过期时间 (day)
     */
    public static final int OUT_DATE = 30;

    /**
     * 文件路径是否有效性
     */
    public static final String VALID_FILE_PATH = "[a-zA-Z]:(?:[/\\\\][^/\\\\:*?\"<>|]{1,255})+";
}
