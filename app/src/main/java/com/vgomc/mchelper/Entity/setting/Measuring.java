package com.vgomc.mchelper.entity.setting;

import com.vgomc.mchelper.utility.TimeUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/30/2015.
 */
public class Measuring {
    public int measId = 0;
    public boolean isOn = false;
    public long beginTime = TimeUtil.time2long(0, 0, 0, 0);
    public long endTime = TimeUtil.time2long(24, 0, 0, 0); // 24小时
    public int interval = 5;
    public List<Integer> variableIndexList = new ArrayList<>();

    public Measuring() {
    }

    public Measuring(int measId) {
        this.measId = measId;
    }

    public String getVariableNames(String split) {
        StringBuilder builder = new StringBuilder();
        for (int ii = 0; ii < variableIndexList.size(); ii++) {
            Variable variable = Configuration.getInstance().variableManager.getVariable(variableIndexList.get(ii));
            if (variable != null && variable.isVariableOn) {
                builder.append(split + variable.name);
            } else {
                variableIndexList.remove(ii);
                ii--;
            }
        }
        if (builder.length() == 0) {
            return "无";
        } else {
            builder.deleteCharAt(0);
            return builder.toString();
        }
    }

    public void setVariableData(BigInteger variableIDs) {
        variableIndexList.clear();
        BigInteger zeroInteger = new BigInteger("0");
        BigInteger twoInteger = new BigInteger("2");
        for (int ii = 1; !variableIDs.equals(zeroInteger); variableIDs = variableIDs.divide(twoInteger), ii++) {
            if (variableIDs.mod(twoInteger).intValue() == 1) {
                Variable variable = Configuration.getInstance().variableManager.getVariableByDeviceIndex(ii);
                if (variable != null) {
                    variableIndexList.add(variable.index);
                }
            }
        }
    }

    public long getVariableId() {
        if (measId == 3) {
            return 0x80000000;
        }
        List<Variable> variableList = Configuration.getInstance().variableManager.getAllVariableList();
        char[] binaryString = new char[32];
        for (int ii = 0; ii < binaryString.length; ii++) {
            binaryString[ii] = '0';
        }
        for (Variable variable : variableList) {
            if (variableIndexList.contains(variable.index)) {
                binaryString[32 - variable.deviceIndex] = '1';
            }
        }
        return Long.parseLong(String.valueOf(binaryString), 2);
    }

}
