package com.vgomc.mchelper.Entity.setting;

import android.content.Context;

import com.vgomc.mchelper.R;

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
    public static final String[] SUBJECTS = new String[]{SUBJECT_SHT, SUBJECT_P2, SUBJECT_P3, SUBJECT_AN0, SUBJECT_AN1, SUBJECT_AN2, SUBJECT_AN3, SUBJECT_AN4, SUBJECT_AN5, SUBJECT_AN6, SUBJECT_AN7, SUBJECT_AN8, SUBJECT_AN9, SUBJECT_AN10, SUBJECT_AN11, SUBJECT_RS485, SUBJECT_SDI};


    public static final int TYPE_SIGNAL_NORMAL = -1;
    public static final int TYPE_SIGNAL_VOLTAGE = 0;
    public static final int TYPE_SIGNAL_CURRENT = 1;

    public int type = -1;
    public String subject = null;
    public String batteryName = null;
    public int signalType = TYPE_SIGNAL_VOLTAGE;

    public Channel() {
    }

    public Channel(int type, String subject, String batteryName, int signalType) {
        this.type = type;
        this.subject = subject;
        this.signalType = signalType;
        this.batteryName = batteryName;
    }

    public int getSensorCount() {
        Set<String> sensorSet = new HashSet<>();
        for (Variable variable : Configuration.getInstance().variableManager.getVariableList(subject)) {
            if (variable.isVariableOn)
                sensorSet.add(variable.sensorAddress);
        }
        return sensorSet.size();
    }

    public int getVariableCount() {
        int count = 0;
        for (Variable variable : getVariable()) {
            if (variable.isVariableOn) {
                count++;
            }
        }
        return count;
    }

    public int getVariableCountInSensor(String sensorAddress) {
        int count = 0;
        for (Variable variable : getVariable()) {
            if (variable.sensorAddress.equals(sensorAddress)) {
                count++;
            }
        }
        return count;
    }

    public List<Variable> getVariable() {
        List<Variable> variableList = Configuration.getInstance().variableManager.getVariableList(subject);
        if (type == TYPE_AN || type == TYPE_AN0 || type == TYPE_P) {
            if (variableList == null || variableList.size() == 0) {
                Variable variable = new Variable(subject, false);
                variableList.add(variable);
            }
        }
        return variableList;
    }

    public void removeVariable(Variable variable) {
        Configuration.getInstance().variableManager.deleteVariable(subject, variable.index);
    }

    public void setVariable(Variable variable) {
        variable.subjectName = subject;
        Configuration.getInstance().variableManager.setVariable(variable);
    }

    public String getWarmTime(Context context) {
        for (Object o : Configuration.getInstance().batteryList) {
            Battery battery = (Battery) o;
            if (battery.subject.equals(batteryName)) {
                if (battery.mode == Battery.MODE_AUTO) {
                    return battery.liveTime + context.getResources().getString(R.string.setting_channel_warm_time_unit);
                } else if (battery.mode == Battery.MODE_EXTERNAL) {
                    return context.getResources().getString(R.string.setting_battery_channel_mode_external);
                } else if (battery.mode == Battery.MODE_ALWAYS) {
                    return context.getResources().getString(R.string.setting_battery_channel_mode_always);
                }
            }
        }
        return context.getResources().getString(R.string.setting_battery_channel_mode_null);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
