package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class UnlockEntity extends BaseBluetoothSettingEntity {

    public String password;

    public UnlockEntity(String password) {
        this.password = password;
    }

    @Override
    public String getRequest() {
        return "AT+UNLOCK=" + password;
    }

}
