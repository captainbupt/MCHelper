package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class GPRSSettingEntity extends BaseBluetoothSettingEntity {

    public String mode;
    public String command;

    public GPRSSettingEntity(boolean isOn, boolean isNormal) {
        if (isOn) {
            command = "ON";
            if (isNormal) {
                mode = "N";
            } else {
                mode = "D";
            }
        } else {
            command = "OFF";
            if (isNormal) {
                mode = "P";
            } else {
                mode = "T";
            }
        }
    }


    @Override
    public String getRequest() {
        return "AT+GPRS=" + command + "," + mode;
    }

}
