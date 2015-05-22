package com.vgomc.mchelper.Entity;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class Channel {
    public String subject;
    public int sensorNum;
    public int variableNum;
    public String[] variableNames;
    public float[][] factors;

    public Channel(String subject, int sensorNum, int variableNum, String[] variableNames, float[][] factors) {
        this.subject = subject;
        this.sensorNum = sensorNum;
        this.variableNum = variableNum;
        this.variableNames = variableNames;
        this.factors = factors;
    }
}
