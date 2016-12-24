package com.vgomc.mchelper.entity.bluetooth.setting;

import android.content.Context;

import com.vgomc.mchelper.entity.setting.VariableManager;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by weizhouh on 6/15/2015.
 */
public class DownloadingEntity extends BaseBluetoothSettingEntity {

    int start, index, count;
    String fileName;
    Context context;

    public DownloadingEntity(Context context, int start, int index, int count, String fileName) {
        this.start = start;
        this.context = context;
        this.index = index;
        this.count = count;
        this.fileName = fileName;
    }

    @Override
    public String getRequest() {
        return "AT+RECORD=" + start + "," + index + "," + count;
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        try {
            String result = parseRecord(data);
            FileServiceProvider.saveRecord(context, fileName, result);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return true;
    }

    public String parseRecord(String data) throws Exception {
        byte[] bytes = data.getBytes(Charset.forName("GBK"));
        if (bytes.length != 512) {
            throw new Exception("字节数不是512. 字节：" + bytes.length);
        }
        StringBuilder builder = new StringBuilder();
        int tableId = bytes[0] & 0xff;
        builder.append(tableId / 16 + 1);
        builder.append(",");
        builder.append(tableId % 16 + 1);
        builder.append(",");
        builder.append(bcd2int(bytes[1]));
        builder.append(bcd2int(bytes[2]));
        builder.append("-");
        builder.append(bcd2int(bytes[3]));
        builder.append("-");
        builder.append(bcd2int(bytes[4]));
        builder.append(" ");
        builder.append(bcd2int(bytes[5]));
        builder.append(":");
        builder.append(bcd2int(bytes[6]));
        builder.append(":");
        builder.append(bcd2int(bytes[7]));
        builder.append(",");
        builder.append(getFloat(bytes, 8, 12));
        builder.append(",");
        builder.append(getFloat(bytes, 12, 16));
        builder.append(",");
        builder.append(getFloat(bytes, 16, 20));
        builder.append(",");
        builder.append(getFloat(bytes, 20, 24));
        builder.append(",");
        builder.append(getFloat(bytes, 24, 28));
        builder.append(",");
        builder.append(getFloat(bytes, 28, 32));
        builder.append(",");
        builder.append(Integer.toHexString(getInt(bytes, 32, 34)));
        for (int ii = 0; ii < VariableManager.variableMaxCount; ii++) {
            int offset = 28 * ii;
            builder.append(",");
            builder.append(getFloat(bytes, 34 + offset, 38 + offset));
            builder.append(",");
            builder.append(getFloat(bytes, 38 + offset, 42 + offset));
            builder.append(",");
            builder.append(getFloat(bytes, 42 + offset, 46 + offset));
            builder.append(",");
            builder.append(getFloat(bytes, 46 + offset, 50 + offset));
            builder.append(",");
            builder.append(getFloat(bytes, 50 + offset, 54 + offset));
            builder.append(",");
            builder.append(getFloat(bytes, 54 + offset, 58 + offset));
            builder.append(",");
            int maxTime = getInt(bytes, 58 + offset, 60 + offset);
            builder.append(maxTime / 60);
            builder.append(":");
            builder.append(maxTime % 60);
            builder.append(",");
            int minTime = getInt(bytes, 60 + offset, 62 + offset);
            builder.append(minTime / 60);
            builder.append(":");
            builder.append(minTime % 60);
        }
        int sum = 0;
        for (int ii = 0; ii < bytes.length - 2; ii++) {
            sum += bytes[ii];
        }
        sum += 1;
        //if (((byte) (sum & 0xff) == bytes[510]) && ((byte) (sum & 0xff00 >> 8) == bytes[511]))
        return builder.toString();
       /* else
            throw new Exception("校验值错误");*/
    }

    public static float getFloat(byte[] bytes, int start, int end) {
        return getFloat(Arrays.copyOfRange(bytes, start, end));
    }

    public static float getFloat(byte[] bytes) {
        return Float.intBitsToFloat(getInt(bytes));
    }

    public static int getInt(byte[] bytes, int start, int end) {
        return getInt(Arrays.copyOfRange(bytes, start, end));
    }

    public static int getInt(byte[] bytes) {
        int result = 0;
        for (int ii = 0; ii < bytes.length; ii++) {
            result = result | (0xff & bytes[ii]) << (8 * ii);
        }
        return result;
    }

    public int bcd2int(byte b) {
        int tmp = b % 0xff;
        return tmp / 16 * 10 + tmp % 16;
    }
}
