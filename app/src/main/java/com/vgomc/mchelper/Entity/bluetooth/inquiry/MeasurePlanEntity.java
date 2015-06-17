package com.vgomc.mchelper.Entity.bluetooth.inquiry;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.setting.Measuring;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class MeasurePlanEntity extends BaseBluetoothEntity {

    public Measuring[] measuringArray;
    public int[] ids;

    @Override
    public String getRequest() {
        return "AT+MEAS?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(SEPERATOR);
        measuringArray = new Measuring[datas.length];
        ids = new int[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                String[] measuringInfo = datas[ii].split(":")[1].split(",");
                Measuring measuring = new Measuring();
                measuring.beginTime = Long.parseLong(measuringInfo[0]) * 60000l;
                measuring.endTime = Long.parseLong(measuringInfo[1]) * 60000l;
                measuring.interval = Integer.parseInt(measuringInfo[2]);
                ids[ii] = Integer.parseInt(measuringInfo[3], 16);
                measuring.isOn = true;
                measuringArray[ii] = measuring;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
