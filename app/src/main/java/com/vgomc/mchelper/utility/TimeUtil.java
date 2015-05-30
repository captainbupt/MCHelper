package com.vgomc.mchelper.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by weizhouh on 5/27/2015.
 */
public class TimeUtil {

    public static long time2long(int hour, int minute, int second, int millisecond) {
        return (long) hour * 3600000l + (long) minute * 60000l + (long) second * 1000l + (long) millisecond;
    }

    public static int[] long2timeArray(long time) {
        int[] timeArray = new int[4];
        timeArray[0] = (int) (time / 3600000l);
        time %= 3600000l;
        timeArray[1] = (int) (time / 60000l);
        time %= 60000l;
        timeArray[2] = (int) (time / 1000l);
        timeArray[3] = (int) (time % 1000l);
        return timeArray;
    }

}
