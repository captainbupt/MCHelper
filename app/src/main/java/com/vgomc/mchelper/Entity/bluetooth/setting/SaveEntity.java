package com.vgomc.mchelper.entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class SaveEntity extends BaseBluetoothSettingEntity {
    @Override
    public String getRequest() {
        return "AT+SAVE";
    }
}
