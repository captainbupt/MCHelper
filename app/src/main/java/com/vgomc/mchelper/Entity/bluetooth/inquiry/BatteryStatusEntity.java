package com.vgomc.mchelper.Entity.bluetooth.inquiry;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class BatteryStatusEntity extends BaseBluetoothEntity {

    public String[] subjects;
    public boolean[] isOns;
    public int onTimes[];

    @Override
    public String getRequest() {
        return "AT+SETPWR?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(SEPERATOR);
        subjects = new String[datas.length];
        isOns = new boolean[datas.length];
        onTimes = new int[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                String[] batteryString = datas[ii].split(":");
                subjects[ii] = batteryString[0];
                String[] batteryInfo = batteryString[1].split(",");
                isOns[ii] = Integer.parseInt(batteryInfo[0]) == 1;
                onTimes[ii] = Integer.parseInt(batteryInfo[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
