package com.vgomc.mchelper.entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class BluetoothStatusSettingEntity extends BaseBluetoothSettingEntity {

    String command;

    public BluetoothStatusSettingEntity(boolean isOn) {
        command = isOn ? "ON" : "OFF";
    }

    @Override
    public String getRequest() {
        return "AT+BT=" + command;
    }
}
