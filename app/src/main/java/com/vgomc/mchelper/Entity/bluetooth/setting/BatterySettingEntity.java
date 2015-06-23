package com.vgomc.mchelper.entity.bluetooth.setting;

import com.vgomc.mchelper.entity.setting.Battery;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class BatterySettingEntity extends BaseBluetoothSettingEntity {

    String subject;
    int mode;
    long startTime;
    long warmTime;

    public BatterySettingEntity(Battery battery) {
        this.subject = battery.subject;
        this.mode = battery.mode;
        this.startTime = battery.startTime;
        this.warmTime = battery.liveTime;
    }

    @Override
    public String getRequest() {
        return String.format("AT+PWR=%s,%d,%d,%d",subject,mode,startTime,warmTime);
    }
}
