package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.utility.TimeUtil;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class ResetEntity extends BaseBluetoothSettingEntity {

    String key;

    public ResetEntity(long time) {
        key = TimeUtil.long2DeviceDate(time);
    }

    @Override
    public String getRequest() {
        return "AT+INIT=" + key;
    }
}
