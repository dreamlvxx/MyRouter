package com.example.myrouter.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

import static com.example.myrouter.utils.Constant.FILE_NAME;
import static com.example.myrouter.utils.Constant.OUT_DATE;

/**
 * Copyright (C) 2013, Xiaomi Inc. All rights reserved.
 * <p>
 * 写文件工具
 * Created by lvxingxing on 19-11-22.
 */
public class DiskIO {

    /**
     * 1.文件路径（【默认path】 ： [自定义path ,暂不支持]）
     * 2.缓存策略（【日志过期自动清理 -默认 30 天】）
     * 3.输出内容结构体（TAG : 线程信息(thread) ： 进程信息 ： 类信息(class)【暂不支持】 ： 内容结构【自定义】）
     * 4.Level 输出等级分类【0-H 1-M 2-L】
     * 5.加密处理（BASE64） [不需要]
     * 6.压缩处理（huffman）【暂不支持】
     * 7.输出流（NIO）
     * 8.日志导出（发送服务器(capacity)）[暂不支持]
     */
    public static void write(String dirPath, String content) {
        FileOutputStream fos = null;
        FileChannel channel = null;
        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File outPath = autoDeleteAndReCreate(dir);
            if(!outPath.exists()){
                outPath.createNewFile();
            }
            fos = new FileOutputStream(outPath, true);
            channel = fos.getChannel();
            byte[] array = content.getBytes("utf-8");
            ByteBuffer buffer = ByteBuffer.wrap(array);
            channel.write(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != channel) {
                    channel.close();
                }
                if(null != fos){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 过期时自动删除重建
     * 首先结构为两层，第一层为dir文件夹，然后第二层只有一个文件。利用文件name中保存一个时间戳，然后根据时间戳计算过期。
     * 输出策略：day ： hour
     * @param dir
     * @return
     * @throws IOException
     */
    private static File autoDeleteAndReCreate(File dir) throws IOException {
        File[] files = dir.listFiles();
        if(null == files || files.length <= 0){
            File file = new File(dir + File.separator + FILE_NAME  + "-"+ System.currentTimeMillis() + ".txt");
            file.createNewFile();
            return file;
        }
        //保质期处理--检查是否过期
        for (File file : files) {

        }
        String name = files[0].getName();
        String sTime = name.substring(name.indexOf("-") + 1,name.indexOf("."));
        long tt = Long.valueOf(sTime);
        Date d1 = new Date(tt);
        Date d2 = new Date();
        if(diffDays(d1,d2) > OUT_DATE){
            delFile(dir + File.separator + name);
            File file = new File(dir + File.separator + FILE_NAME  + "-"+ System.currentTimeMillis() + ".txt");
            file.createNewFile();
            return file;
        }
        return files[0];
    }

    /**
     * 比较两个时间的差值
     * @param date1
     * @param date2
     * @return
     */
    private static int diffDays(Date date1, Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    private static boolean delFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return false;
        }

        if (file.isFile()) {
            return file.delete();
        } else {
            String[] filenames = file.list();
            for (String f : filenames) {
                delFile(f);
            }
            return file.delete();
        }
    }

    public static String read(String file) {
        FileInputStream fis = null;
        FileChannel channel = null;
        String res = "";
        try {
            fis = new FileInputStream(file);
            channel = fis.getChannel();
            int fileLen = (int) channel.size();
            ByteBuffer buffer = ByteBuffer.allocate(fileLen);
            channel.read(buffer);
            Buffer bf = buffer.flip();
            byte[] bt = (byte[]) bf.array();
            res = new String(bt,"utf-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(null != channel){
                    channel.close();
                }
                if(null != fis){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }



}
