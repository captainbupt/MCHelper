package com.vgomc.mchelper.Entity.bluetooth.inquiry;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.setting.Storage;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class StorageTableEntity extends BaseBluetoothEntity {

    public Storage[] storageArray;

    @Override
    public String getRequest() {
        return "AT+TBL?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(SEPERATOR);
        storageArray = new Storage[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                String[] storageInfo = datas[ii].split(":")[1].split(",");
                Storage storage = new Storage();
                storage.beginTime = Long.parseLong(storageInfo[0]) * 60000l;
                storage.endTime = Long.parseLong(storageInfo[1]) * 60000l;
                storage.interval = Integer.parseInt(storageInfo[2]);
                storage.isSend = Integer.parseInt(storageInfo[3]) == 1;
                storage.isOn = true;
                storageArray[ii] = storage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
