package com.vgomc.mchelper.entity.bluetooth.setting;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class RestartEntity extends BaseBluetoothSettingEntity {

    String type;

    public RestartEntity(boolean isHard) {
        type = isHard ? "C" : "W";
    }

    @Override
    public String getRequest() {
        return "AT+RESET=" + type + new String(new byte[]{13});
    }
}
