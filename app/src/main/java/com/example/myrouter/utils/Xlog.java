package com.example.myrouter.utils;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Xlog {

    private final static String TAG = "SAD";

    private static boolean sDebug = false;

    private static String sTag;

    public Xlog(){}

    //全局配置 : 局部配置
    //strategy  打印策略(本地，disk)
    //Interceptor 拦截器到指定strategy（责任链，事件流event -- 》）


    //全局配置
    class GlobalConfig{
        //是否只有debug打印
        boolean isOnlyDebug;
        //打印范围

        //是否缓存本地disk
        boolean isDisk;
    }





    //局部配置
    public void setDebug(){

    }

    public void setDisk(){

    }


    class Event{
        public Event(String content, String tag) {
            this.content = content;
            this.tag = tag;
        }

        String content;
        String tag;
    }

    //test
    public void log(){
        Event e = new Event("xx","你想说什么");
        RealPrintChain rel = new RealPrintChain(0,e);
        String s= rel.process(e);
        Log.e(TAG, "log: 返回值 = " + s);
    }

    List<LogInterceptor> interceptors = new ArrayList<>();
    class RealPrintChain implements LogInterceptor.PrintChain {
        int index;
        Event event;

        public RealPrintChain(int index,Event event) {
            this.index = index;
            this.event = event;
        }

        @Override
        public Event getEvent() {
            return event;
        }

        @Override
        public String process(Event event) {
            return processInner(event);
        }

        //内部进行处理
        private String processInner(Event event){
            if (index > InterceptorManager.strategyArrayList.size() - 1){
                return event.content;
            }
            RealPrintChain chain = new RealPrintChain(index + 1,event);
            LogInterceptor s = InterceptorManager.strategyArrayList.get(index);
            return s.intercept(chain);
        }
    }


    public Xlog addInterceptor(LogInterceptor s){
        InterceptorManager.addInterceptor(s);
        return this;
    }

    static class InterceptorManager {
        static ArrayList<LogInterceptor> strategyArrayList = new ArrayList<>();
        public static void addInterceptor(LogInterceptor s){
            strategyArrayList.add(s);
        }
    }


    public interface LogInterceptor {
        //做处理
        String intercept(PrintChain chain);
        void a();
        void e();
        void i();
        void d();
        void v();
        void w();

        //内部的调用链
        interface PrintChain{
            Event getEvent();
            String process(Event event);
        }
    }

    //进行事件分发 -> e,i,d,w,i,a
    public class Dispatcher{

    }

    public static class DefaultInterceptor implements LogInterceptor {

        @Override
        public String intercept(PrintChain chain) {
            //先取到这个事件，进行处理
            Event e = chain.getEvent();
            Log.e(TAG, "这是default打印的东西" + e.content);
            e.content += "default返回值";
            //处理完毕之后，调用chain本身的process，进行下一个的处理
            return chain.process(e);
        }

        @Override
        public void a() {

        }

        @Override
        public void e() {

        }

        @Override
        public void i() {

        }

        @Override
        public void d() {

        }

        @Override
        public void v() {

        }

        @Override
        public void w() {

        }
    }

    public static class DiskInterceptor implements LogInterceptor {

        @Override
        public String intercept(PrintChain chain) {
            //先取到这个事件，进行处理
            Event e = chain.getEvent();
            Log.e(TAG, "这是disk打印的东西" + e.content);
            //处理完毕之后，调用本身的process，进行下一个的处理
            e.content += "disk返回值";
            return chain.process(e);
        }

        @Override
        public void a() {
        }

        @Override
        public void e() {
        }

        @Override
        public void i() {

        }

        @Override
        public void d() {

        }

        @Override
        public void v() {

        }

        @Override
        public void w() {

        }
    }

    public static void init(boolean debug) {
        init("XLog", debug);
    }

    public static void init(String defaultTag, boolean debug) {
        sTag = defaultTag;
        sDebug = debug;
    }

    public static void e(String tag, String log) {
        if(sDebug){
            Log.e(tag, log);
        }
    }

    public static void e(String log) {
        if(sDebug) {
            Log.e(sTag, log);
        }
    }

    public static void i(String tag, String log) {
        if(sDebug){
            Log.i(tag, log);
        }
    }

    public static void i(String log) {
        if(sDebug) {
            Log.i(sTag, log);
        }
    }

    public static void d(String tag, String log) {
        if(sDebug){
            Log.d(tag, log);
        }
    }

    public static void d(String log) {
        if(sDebug) {
            Log.d(sTag, log);
        }
    }

    public static void w(String tag, String log) {
        if(sDebug){
            Log.w(tag, log);
        }
    }

    public static void w(String log) {
        if(sDebug) {
            Log.w(sTag, log);
        }
    }

    public static void v(String tag, String log) {
        if(sDebug){
            Log.v(tag, log);
        }
    }

    public static void v(String log) {
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
    private final static String DEFAULT_LOGOUT_PATH =  Environment.getExternalStorageDirectory()  + "/MiLog/";//文件夹
    private final static String FILE_NAME = "milog";//文件名称
    public static void f(int level,String tag,String content){
        f(level,tag,DEFAULT_LOGOUT_PATH,content);
    }

    public static void f(int level,String tag,String path,String content){
        if(null == path || null == content){
            return;
        }
        Thread t = Thread.currentThread();
        LogLocal lo = new Config()
                .setContent(content)
                .setLevel(level)
                .setTid(t.getId())
                .setTag(tag)
                .setTime(new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss").format(new Date()))
                .build();
        Log.e(TAG, "f: " + lo.toString());
        write(path, lo.toString());
    }

    public static void write(String path, String content) {
        FileOutputStream fos = null;
        FileChannel channel = null;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file1 = autoDeleteAndReCreate(dir);
            Log.e(TAG,"write: path = " + file1.getPath());
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
        Log.e(TAG, "autoDeleteAndReCreate: d1" + d1 + "d2" + d2 + "diff" + diffDays(d2,d1));
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
            Log.e(TAG,"delFile: success");
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
        Log.e(TAG, "read: " + finalStr);
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