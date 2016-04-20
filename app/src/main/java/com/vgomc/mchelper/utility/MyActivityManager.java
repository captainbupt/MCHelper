package com.vgomc.mchelper.utility;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Activity管理类
 */
public class MyActivityManager {

    private static Stack<Activity> mActivityStack;
    private static MyActivityManager instance;

    private MyActivityManager(){
    }

    /**
     * 功能描述:获取AppManager对象，单例模式
     * @return
     */
    public static MyActivityManager getAppManager(){
        if (instance == null){
            instance = new MyActivityManager();
        }
        return instance;
    }

    /**
     * 功能描述: 添加Activity到堆栈
     * @param activity
     */
    public void addActivity(Activity activity){
        if (mActivityStack == null){
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 功能描述: 获取当前Activity（堆栈中最后一个压入的）
     * @return
     */
    public Activity currentActivity(){
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    /**
     * 功能描述: 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 功能描述: 结束指定的Activity
     * @param activity
     */
    public void finishActivity(Activity activity){
        if (activity != null){
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 功能描述: 移除指定的Activity
     * @param activity
     */
    public void removeActivity(Activity activity){
        if (activity != null){
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 功能描述: 结束指定类名的Activity
     * @param cls
     */
    public static void finishActivity(Class<?> cls){
        try {
            Stack<Activity> activityStackTemp = new Stack<Activity>();
            for (Activity activity : mActivityStack){
                if (activity.getClass().equals(cls)){
                    //判断activity是否已经消失,如果未消失,则消失activity,如果栈里面有activity,但是已经消失了,则从栈里面
                    //移除该对象,为了防止多页面跳时,部分activity被错误加入.
                    if(!activity.isFinishing()){
                        activityStackTemp.add(activity);
                        activity.finish();
                    }
                }
            }
            //在list集合遍历的时候，不能删除list中的元素，否则会报java.util.ConcurrentModificationException异常
            mActivityStack.removeAll(activityStackTemp);
            // 在这里捕获空，当程序退出，又接收推送，点击推送，这个方法报空
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 结束所有Activity
     */
    public static void finishAllActivity(){
        //这里可能会报数组越界的错误提前异常捕获
        Stack<Activity> activityStackTemp = new Stack<Activity>();
        activityStackTemp.addAll(mActivityStack);
        try {
            for (int i = 0, size = activityStackTemp.size(); i < size; i++){
                if (null != activityStackTemp.get(i)){
                    if(!activityStackTemp.get(i).isFinishing()){
                        activityStackTemp.get(i).finish();
                    }
                }
            }
            mActivityStack.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 退出应用程序
     * @param context 上下文
     * @param isBackground 是否开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground){
        try{
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
//			android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground){
//				System.exit(0);    // 程序在下载时开启了下载service，此处不要杀掉任何进程
            }
        }
    }
}
