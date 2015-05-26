package com.vgomc.mchelper.Entity;

/**
 * Created by weizhouh on 5/26/2015.
 */
public class Battery {
    public static int MAX_TIME = 1200000;

    public static String SUBJECT_3V1 =  "3V1　";
    public static String SUBJECT_SWV1 = "SWV1";
    public static String SUBJECT_SWV2 = "SWV2";
    public static String SUBJECT_SWV3 = "SWV3";
    public static String SUBJECT_SWV4 = "SWV4";
    public static String SUBJECT_SWV5 = "SWV5";


    public String subject;
    public boolean isAlwaysOn = true;
    public int startTime = 0;


    public Battery(String subject) {
        this.subject = subject;
    }

    public int getEndTime() {
        return 1000;
    }
}
