package com.vgomc.mchelper.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.vgomc.mchelper.transmit.file.FileServiceProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;
import java.util.TreeSet;

public class CrashHandler implements UncaughtExceptionHandler {

    /**
     * Debug Log tag
     */
    public static final String TAG = "CrashHandler";
    /**
     * 是否开启日志输出,在Debug状态下开启, 在Release状态下关闭以提示程序性能
     */
    public static final boolean DEBUG = false;
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    /**
     * CrashHandler实例
     */
    private static CrashHandler INSTANCE;
    /**
     * 程序的Context对象
     */
    private Context mContext;
    /**
     * 使用Properties来保存设备的信息和错误堆栈信息
     */
    private Properties mDeviceCrashInfo = new Properties();
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String STACK_TRACE = "STACK_TRACE";
    /**
     * 错误报告文件的扩展名
     */
    private static final String CRASH_REPORTER_EXTENSION = ".cr";

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    /**
     * 初始化,注册Context对象, 获取系统默认的UncaughtException处理器, 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx
     */
    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            // Sleep一会后结束程序
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Error : ", e);
            }
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            Log.w(TAG, "handleException --- ex==null");
            return true;
        }
        final String msg = ex.getLocalizedMessage();
        if (msg == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast toast = Toast.makeText(mContext, "程序出错，即将退出!",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                // toast.show();
                // MsgPrompt.showMsg(mContext, "程序出错啦", msg+"\n点确认退出");
                Looper.loop();
            }
        }.start();
        // 收集设备信息
        collectCrashDeviceInfo(mContext);
        // 保存错误报告文件
        saveCrashInfoToFile(ex);
        return true;
    }

    private String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = mContext.getApplicationContext()
                .getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(mContext
                    .getApplicationContext().getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return
     */
    private String saveCrashInfoToFile(Throwable ex) {
        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();
        mDeviceCrashInfo.put("Version", getVersionName());
        mDeviceCrashInfo.put("EXEPTION", ex.getLocalizedMessage());
        mDeviceCrashInfo.put(STACK_TRACE, result);
        try {
            // long timestamp = System.currentTimeMillis();
            Time t = new Time("GMT+8");
            t.setToNow(); // 取得系统时间
            int date = t.year * 10000 + t.month * 100 + t.monthDay;
            int time = t.hour * 10000 + t.minute * 100 + t.second;
            String fileName = "crash-" + date + "-" + time
                    + CRASH_REPORTER_EXTENSION;
            FileOutputStream trace = new FileOutputStream(FileServiceProvider.getExternalPath(mContext) + File.separator + fileName);
            mDeviceCrashInfo.store(trace, "");
            trace.flush();
            trace.close();
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing report file...", e);
        }
        return null;
    }

    /**
     * 收集程序崩溃的设备信息
     *
     * @param ctx
     */
    public void collectCrashDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put(VERSION_NAME,
                        pi.versionName == null ? "not set" : pi.versionName);
                mDeviceCrashInfo.put(VERSION_CODE, "" + pi.versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "Error while collect package info", e);
        }
        // 使用反射来收集设备信息.在Build类中包含各种设备信息,
        // 例如: 系统版本号,设备生产商 等帮助调试程序的有用信息
        // 具体信息请参考后面的截图
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                mDeviceCrashInfo.put(field.getName(), "" + field.get(null));
                if (DEBUG) {
                    Log.d(TAG, field.getName() + " : " + field.get(null));
                }
            } catch (Exception e) {
                Log.e(TAG, "Error while collect crash info", e);
            }
        }
    }

}
