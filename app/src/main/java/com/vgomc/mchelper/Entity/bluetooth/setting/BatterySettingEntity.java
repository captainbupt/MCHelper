package com.vgomc.mchelper.Entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class BatterySettingEntity extends BaseBluetoothSettingEntity {

    String subject;
    int mode;
    int startTime;
    int warmTime;

    public BatterySettingEntity(String subject, int mode, int startTime, int warmTime) {
        this.subject = subject;
        this.mode = mode;
        this.startTime = startTime;
        this.warmTime = warmTime;
    }

    @Override
    public String getRequest() {
        return String.format("AT+PWR=%s,%d,%d,%d",subject,mode,startTime,warmTime);
    }
}
