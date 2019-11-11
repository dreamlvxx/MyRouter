package com.example.myrouter.utils;

import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

public class DiskIO {

    /**
     * 1.文件路径（【默认path】 ： [自定义path ,暂不支持]）
     * 2.缓存策略（【日志过期自动清理 -默认 30 天】）
     * 3.输出内容结构体（TAG : 线程信息(thread) ： 进程信息 ： 类信息(class)【暂不支持】 ： 内容结构【自定义】）
     * 4.Level 输出等级分类【0-H 1-M 2-L】
     * 5.加密处理（BASE64）
     * 6.压缩处理（huffman）【暂不支持】
     * 7.输出流（NIO）
     * 8.日志导出（发送服务器(capacity)）暂无
     */
    public final static String DEFAULT_LOGOUT_PATH =  Environment.getExternalStorageDirectory()  + "/MiLog/";//文件夹
    public final static String FILE_NAME = "milog";//文件名称

    public static void write(String path, String content) {
        FileOutputStream fos = null;
        FileChannel channel = null;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file1 = autoDeleteAndReCreate(dir);
//            Log.e(TAG,"write: path = " + file1.getPath());
            if(!file1.exists()){
                file1.createNewFile();
            }
            String baseStr = Base64.encodeToString(content.getBytes(), Base64.DEFAULT);
            fos = new FileOutputStream(file1, true);
            channel = fos.getChannel();
            byte[] array = baseStr.getBytes("utf-8");
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
     * @param dir
     * @return
     * @throws IOException
     */
    private static File autoDeleteAndReCreate(File dir) throws IOException {
        File[] files = dir.listFiles();
        if(null == files || files.length <= 0){
            File file = new File(dir + File.separator + FILE_NAME  + "-"+System.currentTimeMillis() + ".txt");
            file.createNewFile();
            return file;
        }
        //保质期处理--检查是否过期
        String name = files[0].getName();
        String sTime = name.substring(name.indexOf("-") + 1,name.indexOf("."));
        long tt = Long.valueOf(sTime);
        Date d1 = new Date(tt);
        Date d2 = new Date();
//        Log.e(TAG, "autoDeleteAndReCreate: d1" + d1 + "d2" + d2 + "diff" + diffDays(d2,d1));
        if(diffDays(d1,d2) > 30){
            delFile(dir + File.separator + name);
            File file = new File(dir + File.separator + FILE_NAME  + "-"+System.currentTimeMillis() + ".txt");
            file.createNewFile();
            return file;
        }
        return files[0];
    }

    public static int diffDays(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    public static boolean delFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return false;
        }

        if (file.isFile()) {
//            Log.e(TAG,"delFile: success");
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
        String finalStr = "";
        try {
            fis = new FileInputStream(file);
            channel = fis.getChannel();
            int fileLen = (int) channel.size();
            ByteBuffer buffer = ByteBuffer.allocate(fileLen);
            channel.read(buffer);
            Buffer bf = buffer.flip();
            byte[] bt = (byte[]) bf.array();
            res = new String(bt,"utf-8");
            finalStr = new String(Base64.decode(res.getBytes(), Base64.DEFAULT));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            //base64解析不了\n 这种东西。
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
//        Log.e(TAG, "read: " + finalStr);
        return finalStr;
    }


    interface Level{
        int LEVEL_HIGH = 0;
        int LEVEL_MIDIUM = 1;
        int LEVEL_LOW = 2;
    }
    static class Config{
        String time ;
        String tag;
        int level;
        int pid;
        long tid;
        String content;

        public Config setTime(String time) {
            this.time = time;
            return this;
        }

        public Config setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Config setLevel(int level) {
            this.level = level;
            return this;
        }

        public Config setPid(int pid) {
            this.pid = pid;
            return this;
        }

        public Config setTid(long tid) {
            this.tid = tid;
            return this;
        }

        public Config setContent(String content) {
            this.content = content.replaceAll(" ","");
            return this;
        }

        public LogLocal build(){
            return new LogLocal(this);
        }
    }

    static class LogLocal{
        String time ;
        String tag;
        int level;
        int pid;
        long tid;
        String content;

        public LogLocal(Config config) {
            this.time = config.time;
            this.tag = config.tag;
            this.level = config.level;
            this.pid = config.pid;
            this.tid = config.tid;
            this.content = config.content;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{")
                    .append("Time:")
                    .append(time)
                    .append("Tag:")
                    .append(tag)
                    .append("|")
                    .append("Tid:")
                    .append(tid)
                    .append("|")
                    .append("Content:")
                    .append(content)
                    .append("}");
            return sb.toString();
        }
    }
}
