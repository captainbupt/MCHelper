package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class GPRSStatus extends BaseBluetoothEntity {

    public int status;
    public int flag;
    public int onTime;
    public int waitTime;
    public int retryTimes;
    public boolean isDebug;
    public int strength;
    public int errorRate;
    public String netName;

    @Override
    public String getRequest() {
        return "AT+GPRS?";
    }

    @Override
    public boolean parseData(String data) {
        return false;
    }
}
