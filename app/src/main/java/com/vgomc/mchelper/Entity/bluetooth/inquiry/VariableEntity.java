package com.vgomc.mchelper.Entity.bluetooth.inquiry;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.setting.Variable;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class VariableEntity extends BaseBluetoothEntity {

    public Variable[] variableArray;

    @Override
    public String getRequest() {
        return "AT+VAR?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(SEPERATOR);
        variableArray = new Variable[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                String[] variableString = datas[ii].split(":");
                String[] variableInfo = variableString[1].split(",");
                String subject = variableInfo[1];
                Variable variable = new Variable(subject, true);
                variable.deviceIndex = Integer.parseInt(variableString[0]);
                variable.name = variableInfo[0].replaceAll("\"", "");
                variable.subjectName = variableInfo[1];
                variable.setDataType(variableInfo[2]);
                variable.sensorAddress = variableInfo[3].replaceAll("\"", "");
                variable.setRegisterAddress(Integer.parseInt(variableInfo[4]));
                variable.factors[0] = Float.parseFloat(variableInfo[5]);
                variable.factors[1] = Float.parseFloat(variableInfo[6]);
                variable.factors[2] = Float.parseFloat(variableInfo[7]);
                variable.factors[3] = Float.parseFloat(variableInfo[8]);
                variableArray[ii] = variable;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
