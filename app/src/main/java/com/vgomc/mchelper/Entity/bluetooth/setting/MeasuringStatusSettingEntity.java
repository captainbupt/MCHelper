package com.vgomc.mchelper.Entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class MeasuringStatusSettingEntity extends BaseBluetoothSettingEntity {

    int pendTime;

    public MeasuringStatusSettingEntity(int pendTime) {
        this.pendTime = pendTime;
    }

    @Override
    public String getRequest() {
        return "AT+PEND=" + pendTime;
    }
}
