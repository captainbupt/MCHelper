package com.vgomc.mchelper.utility;

/**
 * Created by weizhouh on 5/29/2015.
 */
public class ClassUtil {
    public static int[] int2array(int number, int size) {
        int[] result = new int[size];
        for (int ii = size - 1; ii >= 0; ii--) {
            result[ii] = number % 10;
            number /= 10;
        }
        return result;
    }

    public static int array2int(int[] array) {
        int result = 0;
        for (int ii = 0; ii < array.length; ii++) {
            result *= 10;
            result += array[ii];
        }
        return result;
    }
}
