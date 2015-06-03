package com.vgomc.mchelper.Entity.bluetooth;

/**
 * Created by weizhouh on 6/1/2015.
 */
public abstract class BaseBluetoothEntity {

    public int errorCode;

    public abstract String getRequest();

    public abstract boolean parseData(String data);

    public boolean parseResponse(String response) {
        String[] responses = response.split("\r\n");
        if (responses.length == 1) {
            if (responses[0].equals("OK")) {
                return true;
            } else {
                errorCode = parseErrorCode(responses[0]);
                return false;
            }
        } else if (responses.length == 2) {
            if (responses[1].equals("OK")) {
                return parseData(responses[0]);
            } else {
                errorCode = parseErrorCode(responses[1]);
                return false;
            }
        }
        return false;
    }

    private int parseErrorCode(String errorCode) {
        return Integer.parseInt(errorCode.split(":")[1]);
    }

}
