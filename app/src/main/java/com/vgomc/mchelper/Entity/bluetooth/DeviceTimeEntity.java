package com.vgomc.mchelper.Entity.bluetooth;

import com.vgomc.mchelper.utility.TimeUtil;
import com.vgomc.mchelper.widget.TimeEditView;

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
    public boolean parseData(String data) {
        try {
            time = TimeUtil.deviceTime2Long(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
