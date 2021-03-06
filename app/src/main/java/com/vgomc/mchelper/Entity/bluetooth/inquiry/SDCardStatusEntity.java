package com.vgomc.mchelper.entity.bluetooth.inquiry;

import android.content.Context;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.utility.ToastUtil;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class SDCardStatusEntity extends BaseBluetoothEntity {

    public static final String[] ERROR_REPRESENTAION = new String[]{"复位读取设备失败", "读取设备测试失败", "设备读取设备模式失败", "加载SD卡失败，可能是因为SD卡不存在", "查询SD卡信息失效，可能是因为SD卡故障或设备不支持"};

    public float total;
    public float free;
    public String format;

    @Override
    public String getRequest() {
        return "AT+SD?";
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        String[] datas = data.split(",");
        try {
            total = Float.parseFloat(datas[0].replace("MB", ""));
            free = Float.parseFloat(datas[1].replace("MB", ""));
            format = datas[2];
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean parseErrorCode(Context context, int errorCode, String content) {
        ToastUtil.showToast(context, "获取SD卡信息失败");
        total = 0;
        free = 0;
        format = "";
        return false;
    }
}
