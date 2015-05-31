package com.vgomc.mchelper.Entity;

import com.vgomc.mchelper.utility.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/30/2015.
 */
public class Measuring {
    public boolean isOn = false;
    public long beginTime = TimeUtil.time2long(0, 0, 0, 0);
    public long endTime = TimeUtil.time2long(24, 0, 0, 0); // 24小时
    public int interval = 5;
    public List<Channel> channelList = new ArrayList<>();
    public List<Integer> variablePositionList = new ArrayList<>();

    public String getVariableNames(String split) {
        if (channelList.size() == 0)
            return "无";
        StringBuilder builder = new StringBuilder();
        for (int ii = 0; ii < channelList.size(); ii++) {
            if (variablePositionList.get(ii) < channelList.get(ii).variables.size()) {
                Variable variable = channelList.get(ii).variables.get(variablePositionList.get(ii));
                if (variable.isVariableOn) {
                    builder.append(split + variable.name);
                }
            }
        }
        builder.deleteCharAt(0);
        return builder.toString();
    }
}
