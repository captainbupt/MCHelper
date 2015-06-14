package com.vgomc.mchelper.Entity.bluetooth.inquiry;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class BluetoothStatuEntity extends BaseBluetoothEntity {

    public final static String[] STATUS_REPRESENTATION = new String[]{"未初始化","初始化","电源关闭","电源打开","配置","工作"};
    public final static String[] FLAG_REPRESENTATION = new String[]{"配置蓝牙错误"};

    public boolean isOn;
    public int status;
    public int flag;
    public int onTime;
    public int waitTime;
    public int retryTime;

    @Override
    public String getRequest() {
        return "AT+BT?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(",");
        try {
            isOn = Integer.parseInt(datas[0]) == 0;
            status = Integer.parseInt(datas[1]);
            flag = Integer.parseInt(datas[2]);
            onTime = Integer.parseInt(datas[3]);
            waitTime = Integer.parseInt(datas[4]);
            retryTime = Integer.parseInt(datas[5]);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}