package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class SDCardStatus extends BaseBluetoothEntity {

    public static final String[] ERROR_REPRESENTAION = new String[]{"复位读取设备失败", "读取设备测试失败", "设备读取设备模式失败", "加载SD卡失败，可能是因为SD卡不存在", "查询SD卡信息失效，可能是因为SD卡故障或设备不支持"};

    public int total;
    public int free;
    public int format;
    public int errorNumber;

    @Override
    public String getRequest() {
        return null;
    }

    @Override
    public boolean parseData(String data) {
        return false;
    }
}
