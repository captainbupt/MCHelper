package com.vgomc.mchelper.entity.bluetooth;

import android.content.Context;
import android.support.v7.app.AlertDialog;

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
        new AlertDialog.Builder(context).setTitle("错误代码: " + errorCode).setMessage("发送命令： "+getRequest()).show();
        return true;
    }

}
