package com.vgomc.mchelper.Entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class BackupSettingEntity extends BaseBluetoothSettingEntity {

    String type;

    public BackupSettingEntity(boolean isAll) {
        type = isAll ? "F" : "R";
    }

    @Override
    public String getRequest() {
        return "AT+COPY=" + type;
    }
}
