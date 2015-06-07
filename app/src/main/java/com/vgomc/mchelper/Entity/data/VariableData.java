package com.vgomc.mchelper.Entity.data;

import java.io.Serializable;

/**
 * Created by weizhouh on 6/2/2015.
 */
public class VariableData implements Serializable{
    public String name;
    public float currentValue;
    public float averageValue;
    public float maxValue;
    public long maxTime;
    public float minValue;
    public long minTime;
    public float periodTotal;
    public float everTotal;
}
