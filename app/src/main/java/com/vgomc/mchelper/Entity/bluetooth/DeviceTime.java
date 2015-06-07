package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class DeviceTime extends BaseBluetoothEntity {

    public long time;

    @Override
    public String getRequest() {
        return "AT_TIME?";
    }

    @Override
    public boolean parseData(String data) {
        return true;
    }
}
