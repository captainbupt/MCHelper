package com.vgomc.mchelper.entity.system;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 6/22/2015.
 */
public class CloudResult {
    public String companyName;
    public String deviceId;
    public String deviceName;
    public List<Object> dataValues;

    public CloudResult(JSONObject jsonObject) {
        if (jsonObject != null) {
            companyName = jsonObject.optString("companyName");
            deviceId = jsonObject.optString("deviceId");
            deviceName = jsonObject.optString("deviceName");
            JSONArray dataArray = jsonObject.optJSONArray("dataValues");
            if (dataArray != null) {
                dataValues = new ArrayList<>();
                for (int ii = 0; ii < dataArray.length(); ii++) {
                    dataValues.add(new DataValue(dataArray.optJSONObject(ii)));
                }
            }
        }
    }
}
