package com.vgomc.mchelper.entity.system;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by weizhouh on 6/22/2015.
 */
public class CloudData {
    public String code;
    public String messages;
    public CloudResult result;

    public CloudData(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            code = jsonObject.optString("code");
            messages = jsonObject.optString("messages");
            result = new CloudResult(jsonObject.optJSONObject("result"));
        } else {
            code = "-1";
            messages = "获取数据异常";
        }
    }
}
