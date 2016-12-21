package com.vgomc.mchelper.entity.bluetooth.inquiry;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.FileUtil;
import com.vgomc.mchelper.utility.ToastUtil;

/**
 * Created by weizhou1 on 2016/12/19.
 */
public class TakePhotoEntity extends BaseBluetoothEntity {

    public static final int RESOLUTION_HIGH = 0;
    public static final int RESOLUTION_MED = 1;
    public static final int RESOLUTION_LOW = 2;

    private int resolution;

    public long address;
    public long fileSize;

    public TakePhotoEntity(int resolution) {
        this.resolution = resolution;
    }

    @Override
    public String getRequest() {
        return "AT+PHOTO=" + resolution;
    }

    @Override
    public boolean parseData(String data) {
        try {
            String[] items = data.split(",");
            address = Long.parseLong(items[0]);
            fileSize = Long.parseLong(items[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean parseErrorCode(Context context, int errorCode) {
        if (errorCode == 20) {
            new AlertDialog.Builder(context).setTitle("当前设备无法拍照").show();
            return true;
        }
        return super.parseErrorCode(context, errorCode);
    }
}
