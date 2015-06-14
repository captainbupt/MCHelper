package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/12/2015.
 */
public abstract class BaseBluetoothSettingEntity extends BaseBluetoothEntity {

    @Override
    public boolean parseData(String data) {
        return true;
    }
}
