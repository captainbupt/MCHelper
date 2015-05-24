package com.vgomc.mchelper.Entity;

/**
 * Created by weizhouh on 5/24/2015.
 */
public class Variable {
    public final static int COUNT_FACTORS = 4;
    public static final int TYPE_SIGNAL_VOLTAGE = 0;
    public static final int TYPE_SIGNAL_CURRENT = 1;

    public boolean isVariableOn = true;
    public String name = "默认名称";
    public boolean isFormulaOn = false;
    public float[] factors = new float[]{0f, 1f, 0f, 0f};
    public int signalType = TYPE_SIGNAL_VOLTAGE;
    public int sensorAddress = 1;
    public int registerAddress = 1;
    public int registerType = 0;
    public int dataType = 0;
}
