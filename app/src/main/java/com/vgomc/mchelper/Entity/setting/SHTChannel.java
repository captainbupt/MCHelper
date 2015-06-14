package com.vgomc.mchelper.Entity.setting;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class SHTChannel extends Channel {
    public static final int SENSOR_TEMPERATURE = 1;
    public static final int SENSOR_HUMIDITY = 2;
    public static final int SENSOR_DEW = 3;


    public SHTChannel() {
        super(TYPE_SHT, SUBJECT_SHT,Battery.SUBJECT_3V1, TYPE_SIGNAL_NORMAL);
    }

    public List<Variable> getVariable() {
        List<Variable> variableList = super.getVariable();
        Variable[] variableArray = new Variable[3];
        for (int ii = 0; ii < variableList.size(); ii++) {
            Variable variable = variableList.get(ii);
            int registerAddress = -1;
            try {
                registerAddress = variable.registerAddress;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (registerAddress <= 0 || registerAddress >= 4) {
                Configuration.getInstance().variableManager.deleteVariable(SUBJECT_SHT, variable.index);
                ii--;
                continue;
            }
            if (variableArray[registerAddress - 1] != null) {
                Configuration.getInstance().variableManager.deleteVariable(SUBJECT_SHT, variable.index);
                ii--;
            } else {
                variableArray[registerAddress - 1] = variable;
            }
        }
        for (int ii = 0; ii < variableArray.length; ii++) {
            if (variableArray[ii] == null) {
                variableArray[ii] = new Variable(SUBJECT_SHT, false, (ii + 1));
            }
        }
        return Arrays.asList(variableArray);
    }

}
