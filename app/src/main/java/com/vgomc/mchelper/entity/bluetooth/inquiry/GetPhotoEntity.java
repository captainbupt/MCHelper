package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhou1 on 2016/12/19.
 */

public class GetPhotoEntity extends BaseBluetoothEntity {

    private long address;
    private long size;

    public String content;

    public GetPhotoEntity(long address, long size) {
        this.address = address;
        this.size = size;
    }

    @Override
    public String getRequest() {
        return "AT+PHOTO?" + address + "," + size;
    }

    @Override
    public boolean parseData(String data) {
        this.content = data;
        return true;
    }
}