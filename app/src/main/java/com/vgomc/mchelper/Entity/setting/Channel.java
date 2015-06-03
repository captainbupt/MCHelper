package com.vgomc.mchelper.Entity.setting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class Channel implements Serializable, Cloneable {

    public static final int TYPE_P = 0;
    public static final int TYPE_AN0 = 1;
    public static final int TYPE_AN = 2;
    public static final int TYPE_SHT = 3;
    public static final int TYPE_RS485 = 4;
    public static final int TYPE_SDI = 5;

    public static final String SUBJECT_P2 = "P2";
    public static final String SUBJECT_P3 = "P3";
    public static final String SUBJECT_AN0 = "AN0";
    public static final String SUBJECT_AN1 = "AN1";
    public static final String SUBJECT_AN2 = "AN2";
    public static final String SUBJECT_AN3 = "AN3";
    public static final String SUBJECT_AN4 = "AN4";
    public static final String SUBJECT_AN5 = "AN5";
    public static final String SUBJECT_AN6 = "AN6";
    public static final String SUBJECT_AN7 = "AN7";
    public static final String SUBJECT_AN8 = "AN8";
    public static final String SUBJECT_AN9 = "AN9";
    public static final String SUBJECT_AN10 = "AN10";
    public static final String SUBJECT_AN11 = "AN11";
    public static final String SUBJECT_SHT = "SHT";
    public static final String SUBJECT_RS485 = "RS485";
    public static final String SUBJECT_SDI = "SDI";

    public int type = -1;
    public String subject = "null";
    public int warmTime = 1000;
    public List<Variable> variables = new ArrayList<>();

    public Channel() {
    }

    public Channel(int type, String subject, ArrayList<Variable> variables) {
        this.type = type;
        this.subject = subject;
        this.variables = variables;
    }

    public int getSensorCount() {
        Set<Integer> sensorSet = new HashSet<>();
        for (Variable variable : variables) {
            if (variable.isVariableOn)
                sensorSet.add(variable.sensorAddress);
        }
        return sensorSet.size();
    }

    public int getVariableCount() {
        int count = 0;
        for (Variable variable : variables) {
            if (variable.isVariableOn) {
                count++;
            }
        }
        return count;
    }

    public int getVariableCountInSensor(int sensorAddress) {
        int count = 0;
        for (Variable variable : variables) {
            if (variable.sensorAddress == sensorAddress) {
                count++;
            }
        }
        return count;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
