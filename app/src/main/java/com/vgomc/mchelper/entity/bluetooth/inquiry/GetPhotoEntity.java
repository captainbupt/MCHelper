package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;

import java.util.Arrays;

/**
 * Created by weizhou1 on 2016/12/19.
 */

public class GetPhotoEntity extends BaseBluetoothEntity {

    private long address;
    private long size;

    public byte[] content;

    public GetPhotoEntity(long address, long size) {
        this.address = address;
        this.size = size;
    }

    @Override
    public String getRequest() {
        return "AT+RWFLASH?" + address + "," + size;
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        if(buffer.length != size + 8){
            return false;
        }
        this.content = Arrays.copyOfRange(buffer, 2, buffer.length - 6);
        return true;
    }
}