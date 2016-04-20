package com.vgomc.mchelper.entity.bluetooth.setting;

import android.content.Context;

import com.vgomc.mchelper.utility.ToastUtil;

import android.widget.Toast;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class BatteryStatusSettingEntity extends BaseBluetoothSettingEntity {

    String subject;
    int onTime;

    public BatteryStatusSettingEntity(String subject, int onTime) {
        this.subject = subject;
        this.onTime = onTime;
    }

    @Override
    public String getRequest() {
        return "AT+SETPWR=" + subject + "," + onTime;
    }

    @Override
    public boolean parseErrorCode(Context context, int errorCode) {
        ToastUtil.showToast(context, "当前工作状态不允许手动控制");
        return true;
    }
}
