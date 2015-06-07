package com.vgomc.mchelper.Entity.bluetooth;

import com.vgomc.mchelper.view.status.SystemView;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by weizhouh on 6/1/2015.
 */
public abstract class BaseBluetoothEntity {

    public static final String SEPERATOR = new String(new byte[]{13, 10});

    public int errorCode;

    public abstract String getRequest();

    public abstract boolean parseData(String data);

    public boolean parseOKResponse(String response) {
        response = response.replaceFirst(SEPERATOR, "");
        response = response.replaceFirst(SEPERATOR + "OK" + SEPERATOR, "");
        return parseData(response);
    }

    public void parseErrorCode(String errorCode) {

    }

}
