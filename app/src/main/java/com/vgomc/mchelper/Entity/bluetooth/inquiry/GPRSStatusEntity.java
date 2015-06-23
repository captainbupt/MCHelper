package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class GPRSStatusEntity extends BaseBluetoothEntity {

    public static final String[] STATUS_REPRESENTATION = new String[]{"未初始化", "已初始化", "已启动", "完成网络初始化", "已登录移动网", "已连接TCP服务器"};
    public static final String[] FLAG_REPRESENTATION = new String[]{"GPRS启动错误", "初始化错误（总）", "无SIM卡", "登录移动网络错误", "无法登录TCP服务器（总）", "设置SMS服务错误", "设置SMS接受模式错误", "启动TCP连接错误", "连接TCP服务错误", "不允许发送TCP数据", "发送TCP数据失败", "GPRS服务未允许", "无法注册GPRS服务", "需要输入PIN码", "连接TCP无应答", ""};

    public int status;
    public int flag;
    public int onTime;
    public int waitTime;
    public int retryTimes;
    public boolean isDebug;
    public int strength;
    public int errorRate;
    public String netName;

    @Override
    public String getRequest() {
        return "AT+GPRS?";
    }

    @Override
    public boolean parseData(String data) {
        String datas[] = data.split(",");
        try {
            status = Integer.parseInt(datas[0]);
            flag = Integer.parseInt(datas[1], 16);
            onTime = Integer.parseInt(datas[2]);
            waitTime = Integer.parseInt(datas[3]);
            retryTimes = Integer.parseInt(datas[4]);
            isDebug = Integer.parseInt(datas[5]) == 1;
            strength = Integer.parseInt(datas[6]);
            errorRate = Integer.parseInt(datas[7]);
            netName = datas[8].replace("\"", "");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getFlagRepresentation() {
        int flag = this.flag;
        StringBuilder builder = new StringBuilder();
        for (int ii = 0; ii < FLAG_REPRESENTATION.length && flag > 0; ii++) {
            if (flag % 2 == 1) {
                builder.append(FLAG_REPRESENTATION[ii]);
                builder.append("\n");
            }
            flag /= 2;
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
