package com.vgomc.mchelper.Entity.system;

import org.json.JSONObject;

/**
 * Created by weizhouh on 6/22/2015.
 */
public class DataValue {

    public String varId;
    public String varName;
    public String channelId;
    public String varUnit;
    public String value;
    public String varDate;

    public DataValue(JSONObject jsonObject) {
        varId = jsonObject.optString("varId");
        varName = jsonObject.optString("varName");
        channelId = jsonObject.optString("channelId");
        varUnit = jsonObject.optString("varUnit");
        value = jsonObject.optString("value");
        varDate = jsonObject.optString("varDate");
    }
}