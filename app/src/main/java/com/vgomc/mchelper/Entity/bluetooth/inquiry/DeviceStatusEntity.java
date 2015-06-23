package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.utility.TimeUtil;

/**
 * Created by weizhouh on 6/1/2015.
 */
public class DeviceStatusEntity extends BaseBluetoothEntity {

    public static final String[] FLAG_REPRESENTATIONS = new String[]{"GPRS开启", "蓝牙开启", "", "", "FLASH错误", "SD卡错误", "GPRS错误", "蓝牙错误", "FLASH重新初始化", "", "", "", "", "电池故障", "正在充电", "备用电池供电"};

    public long resetTime;
    public int resetFlag;
    public long measureTime;
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
        String[] datas = data.split(",");
        try {
            resetTime = TimeUtil.deviceTime2Long(datas[0]);
            resetFlag = Integer.valueOf(datas[1], 16);
            measureTime = TimeUtil.deviceTime2Long(datas[2]);
            battery = Float.parseFloat(datas[3]);
            solarBattery = Float.parseFloat(datas[4]);
            rtcBattery = Float.parseFloat(datas[5]);
            longitude = Float.parseFloat(datas[6]);
            latitude = Float.parseFloat(datas[7]);
            altitude = Float.parseFloat(datas[8]);
            flag = Integer.valueOf(datas[9], 16);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public float[] getBattery() {
        return new float[]{battery, solarBattery, rtcBattery};
    }

    public String getFlagRepresentation() {
        int flag = this.flag;
        StringBuilder builder = new StringBuilder();
        for (int ii = 0; ii < FLAG_REPRESENTATIONS.length && flag > 0; ii++) {
            if (flag % 2 == 1) {
                builder.append(FLAG_REPRESENTATIONS[ii]);
                builder.append("\n");
            }
            flag /= 2;
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public String getGPS() {
        return longitude + "\n" + latitude + "\n" + altitude;
    }
}
