package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.utility.TimeUtil;

/**
 * Created by weizhouh on 6/1/2015.
 */
public class DeviceParameterEntity extends BaseBluetoothEntity {

    public String uid;
    public String model;
    public String version;
    public int index;
    public String name;
    public int zone;
    public int bluetoothTime;
    public String key;
    public int rating;
    public String protocol;
    public int lastRead;
    public long lastData;

    @Override
    public String getRequest() {
        return "AT+DEVICE?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(",");
        try {
            uid = datas[0];
            model = datas[1].replace("\"", "");
            version = datas[2].replace("\"", "");
            index = Integer.parseInt(datas[3]);
            name = datas[4].replace("\"","");
            zone = Integer.parseInt(datas[5]);
            bluetoothTime = Integer.parseInt(datas[6]);
            key = datas[7].replace("\"","");
            rating = Integer.parseInt(datas[8]);
            protocol = datas[9];
            lastRead = Integer.parseInt(datas[10]);
            lastData = TimeUtil.deviceTime2Long(datas[11]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
