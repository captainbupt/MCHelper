package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class MemoryStatusEntity extends BaseBluetoothEntity {

    public int eraseCount;
    public int copyCount;
    public int sector;
    public int offset;
    public float used;
    public float total;

    @Override
    public String getRequest() {
        return "AT+FLASH?";
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        String[] datas = data.split(",");
        try {
            eraseCount = Integer.parseInt(datas[0]);
            copyCount = Integer.parseInt(datas[1]);
            sector = Integer.parseInt(datas[2]);
            offset = Integer.parseInt(datas[3]);
            used = Float.parseFloat(datas[4].replace("KB", ""));
            total = Float.parseFloat(datas[5].replace("KB", ""));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
