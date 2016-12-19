package com.vgomc.mchelper.entity.bluetooth.setting;

import com.vgomc.mchelper.entity.setting.Variable;

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

    public VariableSettingChannel(Variable variable) {
        id = variable.deviceIndex;
        variableName = variable.name;
        channelName = variable.subjectName;
        dataType = Variable.DATA_TYPE[variable.dataType];
        sensorAddress = variable.sensorAddress;
        registerAddress = variable.registerAddress;
        k0 = variable.factors[0];
        k1 = variable.factors[1];
        k2 = variable.factors[2];
        k3 = variable.factors[3];
    }

    @Override
    public String getRequest() {
        if(channelName.compareTo("SDI")==0) {
            return String.format("AT+VAR=%d,\"%s\",%s,%s,'%s',%d,", id, variableName, channelName, dataType, sensorAddress, registerAddress) + k0 + "," + k1 + "," + k2 + "," + k3;
        }
        else{
            return String.format("AT+VAR=%d,\"%s\",%s,%s,%s,%d,", id, variableName, channelName, dataType, sensorAddress, registerAddress) + k0 + "," + k1 + "," + k2 + "," + k3;
        }
    }
}
