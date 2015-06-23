package com.vgomc.mchelper.entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class AccumulateSettingEntity extends BaseBluetoothSettingEntity {

    int variableId;
    float accumulate;

    public AccumulateSettingEntity(int variableId, float accumulate) {
        this.variableId = variableId;
        this.accumulate = accumulate;
    }

    @Override
    public String getRequest() {
        return "AT+AC=" + variableId + "," + accumulate;
    }
}
