package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.utility.TimeUtil;

import java.text.ParseException;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class DeviceTimeEntity extends BaseBluetoothEntity {

    public long time;

    @Override
    public String getRequest() {
        return "AT+TIME?";
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        try {
            time = TimeUtil.deviceTime2Long(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
