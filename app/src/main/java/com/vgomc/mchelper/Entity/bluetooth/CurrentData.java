package com.vgomc.mchelper.Entity.bluetooth;

import com.vgomc.mchelper.Entity.data.VariableData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class CurrentData extends BaseBluetoothEntity {

    public static final int COUNT_TABLE = 2;

    public List<VariableData> variableDataList;

    public int tableIndex;

    public CurrentData(int tableIndex) {
        this.tableIndex = tableIndex;
    }

    @Override
    public String getRequest() {
        return "AT+DATA?" + tableIndex;
    }

    @Override
    public boolean parseData(String data) {
        System.out.println(data);
        variableDataList = new ArrayList<>();
        try {
            String[] variableStrings = data.split(SEPERATOR);
            for (int ii = 0; ii < variableStrings.length; ii++) {
                System.out.println(variableStrings[ii]);
                VariableData tempVariableData = new VariableData();
                String nameDataStrings[] = variableStrings[ii].split(":");
                tempVariableData.name = nameDataStrings[0];
                String dataStrings[] = nameDataStrings[1].split(",");
                tempVariableData.currentValue = Float.parseFloat(dataStrings[0]);
                tempVariableData.averageValue = Float.parseFloat(dataStrings[1]);
                tempVariableData.maxValue = Float.parseFloat(dataStrings[2]);
                tempVariableData.minValue = Float.parseFloat(dataStrings[3]);
                tempVariableData.periodTotal = Float.parseFloat(dataStrings[4]);
                tempVariableData.everTotal = Float.parseFloat(dataStrings[5]);
                tempVariableData.maxTime = Long.parseLong(dataStrings[6]) * 60000l;
                tempVariableData.minTime = Long.parseLong(dataStrings[7]) * 60000l;
                variableDataList.add(tempVariableData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
