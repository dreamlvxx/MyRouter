package com.example.myrouter.utils;

import android.os.Environment;
import android.util.Base64;
import android.util.EventLog;
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

public class Xlog {

    private final static String TAG = "SAD";

    private static boolean sDebug = false;

    private static String sTag;

    public Xlog(){}

    //全局配置 : 局部配置
    //strategy  打印策略(本地，disk)
    //Interceptor 拦截器到指定strategy（责任链，事件流event -- 》）


//    //加一层代理，把I,D,W,A,V,E代理给代理类去处理
//    public class LogProxy{
//        Xlog xlog;
//        public LogProxy(Xlog xlog) {
//            this.xlog = xlog;
//        }
//    }



    /**
     * //------------------全局变量配置表----------------------------------------
     *
     *  是否只有debug打印
     *
     */
    private static boolean globalOnlyDebug;
    /**
     * 是否缓存本地disk
     */
    private static boolean globalNeedDisk;
    /**
     * tag
     */
    private static String globalTag;

    /**
     * //----------------------------------------------------------------------
     */



    /**
     * 全局配置
     */
    public static class LogConfig {
        private LogConfig(ConfigBuilder builder){
            this.isOnlyDebug = builder.isDebug;
            this.isDisk = builder.isDisk;
            this.mTag = builder.tag;
        }
        //是否只有debug打印
        private boolean isOnlyDebug = false;

        //是否缓存本地disk
        private boolean isDisk = false;

        //全局tag
        private String mTag = "TvLog";

        public static class ConfigBuilder{
            boolean isDebug;
            boolean isDisk;
            String tag;

            public ConfigBuilder setDebug(boolean debug) {
                isDebug = debug;
                return this;
            }

            public ConfigBuilder setDisk(boolean disk) {
                isDisk = disk;
                return this;
            }

            public ConfigBuilder setTag(String tag) {
                this.tag = tag;
                return this;
            }

            public LogConfig build(){
                return new LogConfig(this);
            }
        }

    }

    /**
     * 初始化--进行全局配置
     * @param config
     */
    public static void init(LogConfig config){
        if (null == config){
            return;
        }
        globalOnlyDebug = config.isOnlyDebug;
        globalTag = config.mTag;
        globalNeedDisk = config.isDisk;
    }


    /**
     * 事件流
     */
    public static class Event{
        private Event(EventBuilder buidler){
            this.type = buidler.type;
            this.needDisk = buidler.needDisk;
            this.tag = buidler.tag;
            this.content = buidler.content;
        }

        private LogType type;
        private String content;
        private String tag;
        private boolean needDisk;

        private static class EventBuilder{
            private LogType type;
            private String content;
            private String tag;
            private boolean needDisk;

            public EventBuilder setType(LogType type) {
                this.type = type;
                return this;
            }

            public EventBuilder setContent(String content) {
                this.content = content;
                return this;
            }

            public EventBuilder setTag(String tag) {
                this.tag = tag;
                return this;
            }

            public EventBuilder setNeedDisk(boolean needDisk) {
                this.needDisk = needDisk;
                return this;
            }

            public Event build(){
                return new Event(this);
            }
        }
    }

    /**
     * 事件流入口
     * @param e
     */
    public static void startProcess(Event e){
        //先清空所有的interceptor
        InterceptorManager.clear();
        //先添加全局的default
        InterceptorManager.addInterceptor(new DefaultInterceptor());
        //根据是否选择disk添加
        if (globalNeedDisk){
            InterceptorManager.addInterceptor(new DiskInterceptor());
            //根据临时配置是否需要disk
            if (!e.needDisk){
                InterceptorManager.remove(1);
            }
        }

        RealPrintChain rel = new RealPrintChain(0,e);
        rel.process(e);
    }

    /**
     * 调用链默认实现
     */
    static class RealPrintChain implements LogInterceptor.PrintChain {
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


    public static void addInterceptor(LogInterceptor s){
        InterceptorManager.addInterceptor(s);
    }

    /**
     * 拦截器管理
     */
    private static class InterceptorManager {
        private static ArrayList<LogInterceptor> strategyArrayList = new ArrayList<>();

        public static void addInterceptor(LogInterceptor s){
            strategyArrayList.add(s);
        }

        public static void clear(){
            strategyArrayList.clear();
        }

        public static void remove(int index){
            strategyArrayList.remove(index);
        }
    }


    public interface LogInterceptor {
        //做处理
        String intercept(PrintChain chain);
        //调用链
        interface PrintChain{
            Event getEvent();
            String process(Event event);
        }
    }

    //进行事件分发 -> e,i,d,w,i,a
    interface Dispatcher{
        void resolve(LogType type,Event event);
    }

    /**
     *  默认拦截器实现
     */

    public static class DefaultInterceptor implements LogInterceptor,Dispatcher{

        @Override
        public String intercept(PrintChain chain) {
            //先取到这个事件，进行处理
            Event e = chain.getEvent();
            Log.e(TAG, "这是default打印的东西" + e.content);
            e.content += "default返回值";
            //处理完毕之后，调用chain本身的process，进行下一个的处理
            resolve(e.type,e);
            return chain.process(e);
        }

        @Override
        public void resolve(LogType type, Event event) {
            String finalTag = globalTag;
            if (null != event.tag && event.tag.length() > 0){
                finalTag = event.tag;
            }
            switch (type){
                case ASSERT:
                    break;
                case INFO:
                    i(finalTag,event.content);
                    break;
                case WARN:
                    w(finalTag,event.content);
                    break;
                case DEBUG:
                    d(finalTag,event.content);
                    break;
                case ERROR:
                    e(finalTag,event.content);
                    break;
                case VERBOSE:
                    v(finalTag,event.content);
                    break;
                    default:
                        break;
            }
        }
    }

    /**
     * 打印类型
     */
    public enum LogType{
        VERBOSE,DEBUG,INFO,WARN,ERROR,ASSERT
    }

    /**
     * 磁盘打印的拦截器
     */
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

    public static void d(boolean needDisk,String tag,String log){
        Event ee = new Event.EventBuilder()
                .setContent(log)
                .setNeedDisk(needDisk)
                .setTag(tag)
                .build();

        startProcess(ee);
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
