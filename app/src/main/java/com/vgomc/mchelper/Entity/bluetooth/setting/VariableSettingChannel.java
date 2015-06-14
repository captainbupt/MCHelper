package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.Entity.setting.Variable;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class VariableSettingChannel extends BaseBluetoothSettingEntity {

    int id;
    String variableName;
    String channelName;
    String dataType;
    String sensorAddress;
    int registerAddress;
    float k0;
    float k1;
    float k2;
    float k3;

    public VariableSettingChannel(int id, Variable variable) {
        this.id = id;
        variableName = variable.name;
        channelName = variable.subjectName;
        dataType = Variable.DATA_TYPE[variable.dataType];
        sensorAddress = variable.sensorAddress;
        registerAddress = variable.registerAddress;
        k0 = variable.factors[0];

    }

    @Override
    public String getRequest() {
        return String.format("AT+VAR=%d,%s,%s,%s,%s,%d,%.4f,%.4f,%.4f,%.4f", id, variableName, channelName, dataType, sensorAddress, registerAddress, k0, k1, k2, k3);
    }
}
