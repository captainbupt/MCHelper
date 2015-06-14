package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.Entity.setting.RS485Channel;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class DeviceParamSettingEntity extends BaseBluetoothSettingEntity {

    int id;
    String name;
    int zone;
    int bluetoothTime;
    String key;
    int bps;
    String protocol;

    public DeviceParamSettingEntity(int id, String name, int zone, int bluetoothTime, String key, int rs485Rating, int rs485Protocol, int rs485Mode) {
        this.id = id;
        this.name = name;
        this.zone = zone;
        this.bluetoothTime = bluetoothTime;
        this.key = key;
        this.bps = rs485Rating;
        if (rs485Protocol == RS485Channel.TYPE_PROTOCOL_ASCII) {
            protocol = "ASCII-";
        } else {
            protocol = "RTU-";
        }
        if (rs485Mode == RS485Channel.TYPE_MODE_MASTER) {
            protocol += "M";
        } else {
            protocol += "S";
        }
    }

    @Override
    public String getRequest() {
        return String.format("AT+DEVICE=%d,%s,%d,%d,%s,%d,%s", id, name, zone, bluetoothTime, key, bps, protocol);
    }
}
