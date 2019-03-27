package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.setting.Measuring;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class MeasurePlanEntity extends BaseBluetoothEntity {

    public Measuring[] measuringArray;
    public BigInteger[] ids;

    @Override
    public String getRequest() {
        return "AT+MEAS?";
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        String[] datas = data.split(SEPERATOR);
        measuringArray = new Measuring[datas.length];
        ids = new BigInteger[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                int measId = Integer.parseInt(datas[ii].split(":")[0]);
                Measuring measuring = new Measuring(measId);
                String[] measuringInfo = datas[ii].split(":")[1].split(",");
                measuring.beginTime = Long.parseLong(measuringInfo[0]) * 60000l;
                measuring.endTime = Long.parseLong(measuringInfo[1]) * 60000l;
                measuring.interval = Integer.parseInt(measuringInfo[2]);
                ids[ii] = new BigInteger(measuringInfo[3], 16);
                measuring.isOn = (measuring.beginTime == measuring.endTime) ? false : true;
                measuringArray[ii] = measuring;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
