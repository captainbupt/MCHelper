package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/1/2015.
 */
public class DeviceStatus extends BaseBluetoothEntity {

    public long resetTime;
    public int resetFlag;
    public long measTime;
    public float battery;
    public float solarBattery;
    public float rtcBattery;
    public float longtitude;
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
