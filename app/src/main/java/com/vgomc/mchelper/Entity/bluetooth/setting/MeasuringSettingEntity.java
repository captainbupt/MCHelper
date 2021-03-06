package com.vgomc.mchelper.entity.bluetooth.setting;

import com.vgomc.mchelper.entity.setting.Measuring;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class MeasuringSettingEntity extends BaseBluetoothSettingEntity {

    int id;
    int startTime;
    int endTime;
    int interval;
    int variableId;

    public MeasuringSettingEntity(int id, Measuring measuring) {
        this.id = id;
        startTime = (int) (measuring.beginTime / 60000l);
        endTime = (int) (measuring.endTime / 60000l);
        interval = measuring.interval;
        variableId = (int) measuring.getVariableId();
    }

    @Override
    public String getRequest() {
        return String.format("AT+MEAS=%d,%d,%d,%d,%X", id, startTime, endTime, interval, variableId);
    }
}
