package com.vgomc.mchelper.entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class BatteryStatusSettingEntity extends BaseBluetoothSettingEntity {

    String subject;
    int onTime;

    public BatteryStatusSettingEntity(String subject, int onTime) {
        this.subject = subject;
        this.onTime = onTime;
    }

    @Override
    public String getRequest() {
        return "AT+SETPWR=" + subject + "," + onTime;
    }
}
