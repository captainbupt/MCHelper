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

    public String getVariableNames() {
        if (channelList.size() == 0)
            return "无";
        String result = channelList.get(0).variables.get(variablePositionList.get(0)).name;
        for (int ii = 1; ii < channelList.size(); ii++) {
            result = result + "," + channelList.get(0).variables.get(variablePositionList.get(0)).name;
        }
        return result;
    }

    public String getVariableNamesWithNewLine() {
        if (channelList.size() == 0)
            return "无";
        String result = channelList.get(0).variables.get(variablePositionList.get(0)).name;
        for (int ii = 1; ii < channelList.size(); ii++) {
            result = result + "\n" + channelList.get(0).variables.get(variablePositionList.get(0)).name;
        }
        return result;
    }
}
