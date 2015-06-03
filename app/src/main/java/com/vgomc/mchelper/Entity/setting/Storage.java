package com.vgomc.mchelper.Entity.setting;

import com.vgomc.mchelper.utility.TimeUtil;

/**
 * Created by weizhouh on 5/30/2015.
 */
public class Storage {
    public boolean isOn = false;
    public long beginTime = TimeUtil.time2long(0, 0, 0, 0);
    public long endTime =  TimeUtil.time2long(24, 0, 0, 0); // 24小时
    public int interval = 5;
    public boolean isSend = true;
}
