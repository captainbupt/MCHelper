package com.vgomc.mchelper.base;


import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;

import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.utility.CrashHandler;

import org.xutils.x;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class AppApplication extends Application {
    // tag
    public static String appVersion;
    public static final String SYSVERSION = android.os.Build.VERSION.RELEASE;

    private static AppApplication appApplication;

    //公开，静态的工厂方法
    public static AppApplication getInstance() {
        return appApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        // Bitmap初始化必须在MyVolley之前，否则会丢出异常
        // BitmapLruCache.init(getApplicationContext());
        // MyVolley.init(getApplicationContext());
        // 开启异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        // 获取程序版本
        try {
            appVersion = getVersionName();
        } catch (Exception e) {
            appVersion = "1.0";
            e.printStackTrace();
        }
        // 解决文件分享问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        Configuration.initInstance();
        x.Ext.init(this);
    }

    private String getVersionName() throws Exception {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
                0);
        String version = packInfo.versionName;
        return version;
    }

    /**
     * 功能描述: 获取屏幕高度
     *
     * @return 屏幕高度
     */
    public int getScreenHeight(Context context) {
        // 获得手机分辨率
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 功能描述: 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    public int getScreenWidth(Context context) {
        // 获得手机分辨率
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

}
