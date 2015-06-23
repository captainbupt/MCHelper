package com.vgomc.mchelper.entity.bluetooth.inquiry;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.setting.Channel;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class ChannelEntity extends BaseBluetoothEntity {

    public Channel[] channelArray;

    @Override
    public String getRequest() {
        return "AT+CHN?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(SEPERATOR);
        channelArray = new Channel[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                String[] channelDatas = datas[ii].split(":")[1].split(",");
                Channel channel = new Channel();
                channel.subject = channelDatas[0];
                channel.batteryName = channelDatas[1];
                if (channelDatas[2].equals("V"))
                    channel.signalType = Channel.TYPE_SIGNAL_VOLTAGE;
                else if (channelDatas[2].equals("C"))
                    channel.signalType = Channel.TYPE_SIGNAL_CURRENT;
                else if (channelDatas[2].equals("N"))
                    channel.signalType = Channel.TYPE_SIGNAL_NORMAL;
                else
                    throw new Exception("不支持的通道工作模式");
                channelArray[ii] = channel;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
