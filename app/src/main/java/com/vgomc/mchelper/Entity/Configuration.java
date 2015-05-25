package com.vgomc.mchelper.Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    public int channelVariableMaxCount = 17;


    private static Configuration instance;

    public static Configuration getInstance() {
        return instance;
    }

    public static void setInstanceFromFile(String path) {
        instance = readObjectFromFile(path);
    }

    public static void initInstance() {
        instance = new Configuration();
        instance.name = "默认名称";
        instance.password = "123456";
        instance.timeZone = 8;
        instance.bluetoothTime = 0;
        instance.bluetoothTimeOn = false;
        instance.channelMap = new HashMap<>();
        ArrayList<Variable> P2Variables = new ArrayList<>();
        P2Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_P2, new Channel(Channel.TYPE_P, Channel.SUBJECT_P2, P2Variables));
        ArrayList<Variable> P3Variables = new ArrayList<>();
        P3Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_P3, new Channel(Channel.TYPE_P, Channel.SUBJECT_P3, P3Variables));
        ArrayList<Variable> AN0Variables = new ArrayList<>();
        AN0Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN0, new Channel(Channel.TYPE_AN0, Channel.SUBJECT_AN0, AN0Variables));
        ArrayList<Variable> AN1Variables = new ArrayList<>();
        AN1Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN1, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN1, AN1Variables));
        ArrayList<Variable> AN2Variables = new ArrayList<>();
        AN2Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN2, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN2, AN2Variables));
        ArrayList<Variable> AN3Variables = new ArrayList<>();
        AN3Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN3, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN3, AN3Variables));
        ArrayList<Variable> AN4Variables = new ArrayList<>();
        AN4Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN4, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN4, AN4Variables));
        ArrayList<Variable> AN5Variables = new ArrayList<>();
        AN5Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN5, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN5, AN5Variables));
        ArrayList<Variable> AN6Variables = new ArrayList<>();
        AN6Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN6, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN6, AN6Variables));
        ArrayList<Variable> AN7Variables = new ArrayList<>();
        AN7Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_AN7, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN7, AN7Variables));
        ArrayList<Variable> AN8Variables = new ArrayList<>();
        Variable AN8Variable = new Variable();
        AN8Variable.isVariableOn = false;
        AN8Variables.add(AN8Variable);
        instance.channelMap.put(Channel.SUBJECT_AN8, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN8, AN8Variables));
        ArrayList<Variable> AN9Variables = new ArrayList<>();
        Variable AN9Variable = new Variable();
        AN9Variable.isVariableOn = false;
        AN9Variables.add(AN9Variable);
        instance.channelMap.put(Channel.SUBJECT_AN9, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN9, AN9Variables));
        ArrayList<Variable> AN10Variables = new ArrayList<>();
        Variable AN10Variable = new Variable();
        AN10Variable.isVariableOn = false;
        AN10Variables.add(AN10Variable);
        instance.channelMap.put(Channel.SUBJECT_AN10, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN10, AN10Variables));
        ArrayList<Variable> AN11Variables = new ArrayList<>();
        Variable AN11Variable = new Variable();
        AN11Variable.isVariableOn = false;
        AN11Variables.add(AN11Variable);
        instance.channelMap.put(Channel.SUBJECT_AN11, new Channel(Channel.TYPE_AN, Channel.SUBJECT_AN11, AN11Variables));
        ArrayList<Variable> SHTVariables = new ArrayList<>();
        SHTVariables.add(new Variable());
        SHTVariables.add(new Variable());
        SHTVariables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_SHT, new Channel(Channel.TYPE_SHT, Channel.SUBJECT_SHT, SHTVariables));
        ArrayList<Variable> RS485Variables = new ArrayList<>();
        RS485Variables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_RS485, new RS485Channel(RS485Variables));
        ArrayList<Variable> SDIVariables = new ArrayList<>();
        SDIVariables.add(new Variable());
        instance.channelMap.put(Channel.SUBJECT_SDI, new Channel(Channel.TYPE_SDI, Channel.SUBJECT_SDI, SDIVariables));
    }

    public int getChannelVariableCount() {
        int count = 0;
        for (Channel channel : channelMap.values()) {
            count += channel.getVariableCount();
        }
        return count;
    }

    public static void writeObjectToFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(getInstance());
            objOut.flush();
            objOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Configuration readObjectFromFile(String path) {
        Configuration temp = null;
        File file = new File(path);
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = (Configuration) objIn.readObject();
            objIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

}
