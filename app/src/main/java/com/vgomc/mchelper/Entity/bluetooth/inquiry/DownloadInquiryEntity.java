package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.setting.BaseBluetoothSettingEntity;

/**
 * Created by weizhouh on 6/15/2015.
 */
public class DownloadInquiryEntity extends BaseBluetoothSettingEntity {

    public int start;
    public int count;
    String mode;

    public DownloadInquiryEntity(boolean isAll) {
        if (isAll) {
            mode = "A";
        } else {
            mode = "N";
        }
    }

    @Override
    public String getRequest() {
        return "AT+RECORD?" + mode;
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        String[] datas = data.split(",");
        try {
            start = Integer.parseInt(datas[1]);
            count = Integer.parseInt(datas[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return super.parseData(data, buffer);
    }
}
