package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.setting.Network;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class GPRSParamEntity extends BaseBluetoothEntity {

    public Network network;

    @Override
    public String getRequest() {
        return "AT+GPRSSET?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(",");
        network = new Network();
        try {
            long onTime = Long.parseLong(datas[0]);
            network.isAlwaysOn = onTime == 0;
            network.time = onTime*1000l;
            network.address = datas[1].replace("\"","");
            network.port = Integer.parseInt(datas[2]);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
