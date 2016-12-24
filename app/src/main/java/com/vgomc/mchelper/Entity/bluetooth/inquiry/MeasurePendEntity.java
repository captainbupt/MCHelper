package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class MeasurePendEntity extends BaseBluetoothEntity {

    public int pendTime;

    @Override
    public String getRequest() {
        return "AT+PEND?";
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        try {
            pendTime = Integer.parseInt(data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
