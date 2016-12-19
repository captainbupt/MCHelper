package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class BluetoothStatusEntity extends BaseBluetoothEntity {

    public final static String[] STATUS_REPRESENTATION = new String[]{"未初始化","初始化","电源关闭","电源打开","配置","工作","已连接"};
    public final static String[] FLAG_REPRESENTATION = new String[]{"配置蓝牙错误"};

    public boolean isOn;
    public int status;
    public int flag;
    public int onTime;
    public int waitTime;
    public int retryTime;

    @Override
    public String getRequest() {
        return "AT+BT?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(",");
        try {
            isOn = Integer.parseInt(datas[0]) == 0;
            status = Integer.parseInt(datas[1]);
            flag = Integer.parseInt(datas[2]);
            onTime = Integer.parseInt(datas[3]);
            waitTime = Integer.parseInt(datas[4]);
            retryTime = Integer.parseInt(datas[5]);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getStatus(){
        String statusString = "";
/*        if(flag == 0){
            statusString = FLAG_REPRESENTATION[0] + "\n";
        }*/

        if((this.status>=0)&&(this.status<STATUS_REPRESENTATION.length)) {
            statusString += STATUS_REPRESENTATION[this.status];
        }
        else statusString +="未知:"+this.status+"";

        return statusString;
    }
}
