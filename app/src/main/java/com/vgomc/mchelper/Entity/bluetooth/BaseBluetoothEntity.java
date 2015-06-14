package com.vgomc.mchelper.Entity.bluetooth;

import android.content.Context;

import com.vgomc.mchelper.view.status.SystemView;

import org.holoeverywhere.app.AlertDialog;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by weizhouh on 6/1/2015.
 */
public abstract class BaseBluetoothEntity {

    public static final String SEPERATOR = new String(new byte[]{13, 10});

    public abstract String getRequest();

    public abstract boolean parseData(String data);

    public boolean parseOKResponse(String response) {
        response = response.replaceFirst(SEPERATOR, "");
        response = response.replaceFirst(SEPERATOR + "OK" + SEPERATOR, "");
        return parseData(response);
    }

    // 返回true，则蓝牙模块不会继续后续命令的交换
    // 返回false， 则忽视当前错误，继续执行后续命令
    public boolean parseErrorCode(Context context, int errorCode) {
        new AlertDialog.Builder(context).setTitle("错误代码: " + errorCode).show();
        return true;
    }

}
