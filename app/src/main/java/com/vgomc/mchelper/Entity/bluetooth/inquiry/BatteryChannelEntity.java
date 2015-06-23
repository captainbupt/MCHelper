package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.setting.Battery;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class BatteryChannelEntity extends BaseBluetoothEntity {

    public Battery[] batteryArray;

    @Override
    public String getRequest() {
        return "AT+PWR?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(SEPERATOR);
        batteryArray = new Battery[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                Battery battery = new Battery();
                String[] batteryString = datas[ii].split(":");
                battery.subject = batteryString[0];
                String[] batteryInfo = batteryString[1].split(",");
                battery.mode = Integer.parseInt(batteryInfo[0]);
                battery.startTime = Long.parseLong(batteryInfo[1]);
                battery.liveTime = Long.parseLong(batteryInfo[2]);
                batteryArray[ii] = battery;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
