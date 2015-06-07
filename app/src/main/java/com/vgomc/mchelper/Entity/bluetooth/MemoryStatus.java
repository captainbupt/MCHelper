package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class MemoryStatus extends BaseBluetoothEntity {

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
    public boolean parseData(String data) {
        return false;
    }
}
