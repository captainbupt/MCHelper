package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.utility.TimeUtil;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class TimeSettingEntity extends BaseBluetoothSettingEntity {

    public String timeString;

    public TimeSettingEntity(long time) {
        timeString = TimeUtil.long2DeviceTime(time);
    }

    @Override
    public String getRequest() {
        return "AT+TIME=" + timeString;
    }
}
