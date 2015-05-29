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
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hour, minute, second);
        calendar.setTimeInMillis(calendar.getTimeInMillis() + millisecond);
        return calendar.getTimeInMillis();
    }

    public static Calendar long2calendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

}
