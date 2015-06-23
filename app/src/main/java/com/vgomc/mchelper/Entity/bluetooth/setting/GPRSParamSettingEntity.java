package com.vgomc.mchelper.entity.bluetooth.setting;

import com.vgomc.mchelper.entity.setting.Network;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class GPRSParamSettingEntity extends BaseBluetoothSettingEntity {

    private int onTime;
    private String server;
    private int port;

    public GPRSParamSettingEntity(Network network) {
        onTime = network.isAlwaysOn ? 0 : (int) (network.time / 1000);
        server = network.address;
        port = network.port;
    }

    @Override
    public String getRequest() {
        return String.format("AT+GPRSSET=%d,\"%s\",%d", onTime, server, port);
    }
}
