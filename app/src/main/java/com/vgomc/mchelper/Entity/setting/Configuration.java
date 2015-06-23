package com.vgomc.mchelper.entity.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weizhouh on 5/23/2015.
 */
public class Configuration {

    public String name;
    public String password;
    public int timeZone;
    public int bluetoothTime;
    public boolean bluetoothTimeOn;
    public Map<String, Channel> channelMap;
    public VariableManager variableManager;
    public List<Object> batteryList;
    public List<Object> storageList;
    public List<Object> measuringList;
    public Network network;


    private static Configuration instance;

    public static Configuration getInstance() {
        return instance;
    }

    public static void setInstance(Configuration configuration) {
        instance = configuration;
    }

    public static void initInstance() {
        instance = new Configuration();
        instance.name = "默认名称";
        instance.password = "MC301A";
        instance.timeZone = 8;
        instance.bluetoothTime = 0;
        instance.bluetoothTimeOn = false;

        instance.channelMap = new HashMap<>();
        instance.channelMap.put(Channel.SUBJECT_P2, new Channel(Channel.TYPE_P, Channel.SUBJECT_P2, "null", Channel.TYPE_SIGNAL_NORMAL));
        instance.channelMap.put(Channel.SUBJECT_P3, new Channel(Channel.TYPE_P, Channel.SUBJECT_P3, "null",Channel.TYPE_SIGNAL_NORMAL));
        instance.channelMap.put(Channel.SUBJECT_AN0, new Channel(Channel.TYPE_AN0, Channel.SUBJECT_AN0,"null", Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN1, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN1,Battery.SUBJECT_3V1, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN2, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN2,Battery.SUBJECT_SWV1, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN3, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN3,Battery.SUBJECT_SWV1, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN4, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN4,Battery.SUBJECT_SWV2, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN5, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN5,Battery.SUBJECT_SWV2, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN6, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN6,Battery.SUBJECT_SWV3, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN7, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN7,Battery.SUBJECT_SWV3, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN8, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN8,Battery.SUBJECT_SWV4, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN9, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN9,Battery.SUBJECT_SWV4, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN10, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN10,Battery.SUBJECT_SWV5, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_AN11, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN11,Battery.SUBJECT_SWV5, Channel.TYPE_SIGNAL_CURRENT));
        instance.channelMap.put(Channel.SUBJECT_SHT, new SHTChannel());
        instance.channelMap.put(Channel.SUBJECT_RS485, new RS485Channel());
        instance.channelMap.put(Channel.SUBJECT_SDI, new Channel(Channel.TYPE_SDI, Channel.SUBJECT_SDI,Battery.SUBJECT_SWV6, Channel.TYPE_SIGNAL_NORMAL));

        VariableManager variableManager = new VariableManager();
        variableManager.setVariable(new Variable(Channel.SUBJECT_P2, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_P3, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN0, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN1, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN2, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN3, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN4, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN5, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN6, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN7, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN8, false));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN9, false));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN10, false));
        variableManager.setVariable(new Variable(Channel.SUBJECT_AN11, false));
        variableManager.setVariable(new Variable(Channel.SUBJECT_SHT, true, SHTChannel.SENSOR_TEMPERATURE));
        variableManager.setVariable(new Variable(Channel.SUBJECT_SHT, false, SHTChannel.SENSOR_HUMIDITY));
        variableManager.setVariable(new Variable(Channel.SUBJECT_SHT, true, SHTChannel.SENSOR_DEW));
        variableManager.setVariable(new Variable(Channel.SUBJECT_RS485, true));
        variableManager.setVariable(new Variable(Channel.SUBJECT_SDI, true));
        instance.variableManager = variableManager;


        List<Object> batteryList = new ArrayList<>();
        batteryList.add(new Battery(Battery.SUBJECT_3V1));
        batteryList.add(new Battery(Battery.SUBJECT_SWV1));
        batteryList.add(new Battery(Battery.SUBJECT_SWV2));
        batteryList.add(new Battery(Battery.SUBJECT_SWV3));
        batteryList.add(new Battery(Battery.SUBJECT_SWV4));
        batteryList.add(new Battery(Battery.SUBJECT_SWV5));
        batteryList.add(new Battery(Battery.SUBJECT_SWV6));
        instance.batteryList = batteryList;

        instance.storageList = new ArrayList<>();
        instance.storageList.add(new Storage());
        instance.storageList.add(new Storage());

        instance.measuringList = new ArrayList<>();
        instance.measuringList.add(new Measuring());
        instance.measuringList.add(new Measuring());

        instance.network = new Network();
    }
}
