package com.vgomc.mchelper.Entity.setting;

/**
 * Created by weizhouh on 5/26/2015.
 */
public class Battery {
    public static int MAX_TIME = 1200000;

    public final static String SUBJECT_3V1 = "3V1";
    public final static String SUBJECT_SWV1 = "SWV1";
    public final static String SUBJECT_SWV2 = "SWV2";
    public final static String SUBJECT_SWV3 = "SWV3";
    public final static String SUBJECT_SWV4 = "SWV4";
    public final static String SUBJECT_SWV5 = "SWV5";
    public final static String SUBJECT_SWV6 = "SWV6";
    public final static String[] SUBJECTS = new String[]{SUBJECT_3V1, SUBJECT_SWV1, SUBJECT_SWV2, SUBJECT_SWV3, SUBJECT_SWV4, SUBJECT_SWV5, SUBJECT_SWV6};

    public final static int MODE_AUTO = 0;
    public final static int MODE_ALWAYS = 1;
    public final static int MODE_EXTERNAL = 2;

    public String subject;
    public int mode = MODE_ALWAYS;
    public boolean isOrder = false;
    public long startTime = 0;
    public long liveTime = 0;

    public Battery() {
    }

    public Battery(String subject) {
        this.subject = subject;
    }

}
