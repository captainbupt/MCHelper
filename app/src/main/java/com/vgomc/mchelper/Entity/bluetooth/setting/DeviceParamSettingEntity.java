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

    public DeviceParamSettingEntity(String name, int zone, int bluetoothTime, String key, RS485Channel rs485Channel) {
        this.name = name;
        this.zone = zone;
        this.bluetoothTime = bluetoothTime;
        this.key = key;
        this.id = rs485Channel.slaveAddress;
        this.bps = rs485Channel.baudRate;
        if (rs485Channel.protocol == RS485Channel.TYPE_PROTOCOL_ASCII) {
            protocol = "ASCII-";
        } else {
            protocol = "RTU-";
        }
        if (rs485Channel.mode == RS485Channel.TYPE_MODE_MASTER) {
            protocol += "M";
        } else {
            protocol += "S";
        }
    }

    @Override
    public String getRequest() {
        return String.format("AT+DEVICE=%d,\"%s\",%d,%d,\"%s\",%d,%s", id, name, zone, bluetoothTime, key, bps, protocol);
    }
}
