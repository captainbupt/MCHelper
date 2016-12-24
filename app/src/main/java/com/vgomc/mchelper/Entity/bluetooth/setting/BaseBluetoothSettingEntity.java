package com.vgomc.mchelper.entity.bluetooth.setting;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/12/2015.
 */
public abstract class BaseBluetoothSettingEntity extends BaseBluetoothEntity {

    @Override
    public boolean parseData(String data, byte[] buffer) {
        return true;
    }
}
