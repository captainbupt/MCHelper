package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/1/2015.
 */
public class DeviceStatus extends BaseBluetoothEntity {

    public static final String[] FLAG_REPRESENTATIONS = new String[]{"GPRS开启", "蓝牙开启", "", "", "FLASH错误", "SD卡错误", "GPRS错误", "蓝牙错误", "FLASH重新初始化", "", "", "", "", "电池故障", "正在充电", "备用电池供电"};

    public long resetTime;
    public int resetFlag;
    public long measTime;
    public float battery;
    public float solarBattery;
    public float rtcBattery;
    public float longitude;
    public float latitude;
    public float altitude;
    public int flag;


    @Override
    public String getRequest() {
        return "AT+SYS?";
    }

    @Override
    public boolean parseData(String data) {
        return true;
    }

}
