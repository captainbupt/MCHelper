package com.vgomc.mchelper.entity.bluetooth.setting;

import com.vgomc.mchelper.entity.setting.Storage;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class StorageSettingEntity extends BaseBluetoothSettingEntity {

    int id;
    int startTime;
    int endTime;
    int interval;
    int send;

    public StorageSettingEntity(int id, Storage storage) {
        this.id = id;
        startTime = (int) (storage.beginTime / 60000l);
        endTime = (int) (storage.endTime / 60000l);
        interval = storage.interval;
        send = storage.isSend ? 1 : 0;
    }

    @Override
    public String getRequest() {
        return String.format("AT+TBL=%d,%d,%d,%d,%d", id, startTime, endTime, interval, send);
    }
}
