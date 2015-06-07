package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class Unlock extends BaseBluetoothEntity {

    public String password = "MC301A";

    @Override
    public String getRequest() {
        return "AT+UNLOCK=" + password;
    }

    @Override
    public boolean parseData(String data) {
        return true;
    }
}
