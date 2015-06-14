package com.vgomc.mchelper.Entity.bluetooth.setting;

import com.vgomc.mchelper.Entity.setting.Channel;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class ChannelSettingEntity extends BaseBluetoothSettingEntity {

    String subject;
    String mode;

    public ChannelSettingEntity(String subject, int mode) {
        this.subject = subject;
        if (mode == Channel.TYPE_SIGNAL_CURRENT) {
            this.mode = "C";
        } else if (mode == Channel.TYPE_SIGNAL_VOLTAGE) {
            this.mode = "V";
        } else {
            this.mode = "N";
        }
    }

    @Override
    public String getRequest() {
        return "AT+CHN=" + subject + "," + mode;
    }
}
