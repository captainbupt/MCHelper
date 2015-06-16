package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.utility.TimeUtil;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class ClearEntity extends BaseBluetoothSettingEntity {

    String key;

    public ClearEntity(long time) {
        this.key = TimeUtil.long2DeviceDate(time);
    }

    public ClearEntity(String date) {
        this.key = date;
    }

    @Override
    public String getRequest() {
        return "AT+CLRRECORD=" + key;
    }
}
