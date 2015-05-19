package com.vgomc.mchelper.utility;

import android.content.Context;

import com.vgomc.mchelper.R;

import org.holoeverywhere.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtil {

    /**
     * 功能描述: 用于直接输入文字的方法
     * @param context   上下文对象
     * @param message   需要提醒的文字
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 功能描述:  用于输入String字符串id的方法
     * @param context   上下文对象
     * @param message   需要提醒的String字符串id
     */
    public static void showToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}