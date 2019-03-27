package com.vgomc.mchelper.entity.bluetooth.setting;

import android.content.Context;
import android.text.TextUtils;

import com.vgomc.mchelper.entity.bluetooth.inquiry.VariableEntity;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.ToastUtil;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by weizhouh on 6/15/2015.
 */
public class DownloadingEntity extends BaseBluetoothSettingEntity {

    int start, index, count;
    String fileName;
    Context context;
    boolean[] columnList;
    VariableEntity variableEntity;

    public DownloadingEntity(Context context, int start, int index, int count, boolean[] columnList, VariableEntity variableEntity, String fileName) {
        this.start = start;
        this.context = context;
        this.index = index;
        this.count = count;
        this.fileName = fileName;
        this.columnList = columnList;
        this.variableEntity = variableEntity;
    }

    @Override
    public String getRequest() {
        return "AT+RECORD=" + start + "," + index + "," + count;
    }

    @Override
    public boolean parseData(String data, byte[] buffer) {
        String result = null;
        try {
            result = parseRecord(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        String[] columns = result.split(",");
        int currentVariableIndex = Integer.parseInt(columns[0]);
        String currentVariableTime = columns[2];
        try {

            if (currentVariableTime.equals(lastVariableTime)) {
                if (currentVariableIndex == 2) {  // 补充上一行
                    StringBuilder builder = new StringBuilder();
                    // 只取变量值，且抛掉最后两个
                    for (int i = 10; i < columns.length; i++) {
                        builder.append(",").append(columns[i]);
                    }
                    FileServiceProvider.saveRecord(context, fileName, builder.toString(), false);
                } else {
                    ToastUtil.showToast(context, "时间一致且变量组为1，跳过记录");
                }
            } else {// 新起一行
                if (currentVariableIndex == 1) {
                    // 时间为空，则为第一行，不需要补\r\n
                    if (!TextUtils.isEmpty(lastVariableTime)) {
                        FileServiceProvider.saveRecord(context, fileName, "\r\n", false);
                    }
                    FileServiceProvider.saveRecord(context, fileName, result, false);
                } else {
                    if (!TextUtils.isEmpty(lastVariableTime)) {
                        FileServiceProvider.saveRecord(context, fileName, "\r\n", false);
                    }
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 10; i++) {
                        builder.append(",").append(columns[i]);
                    }
                    builder.deleteCharAt(0);

                    for (int i = 0; i < 17; i++) {
                        for (int j = 0; j < columnList.length; j++) {
                            if (columnList[j]) {
                                builder.append(",");
                            }
                        }
                    }
                    for (int i = 10; i < columns.length; i++) {
                        builder.append(",").append(columns[i]);
                    }
                    FileServiceProvider.saveRecord(context, fileName, builder.toString(), false);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        lastVariableTime = currentVariableTime;
        return true;
    }

    private static String lastVariableTime = "";

    private String parseRecord(byte[] buffer) throws Exception {
        byte[] bytes = new byte[buffer.length - 2 - 6];
        if (bytes.length != 512) {
            throw new Exception("字节数不是512. 字节：" + bytes.length);
        }
        for (int i = 2; i < buffer.length - 6; i++) {
            bytes[i - 2] = buffer[i];
        }
        StringBuilder builder = new StringBuilder();
        int tableId = bytes[0] & 0xff;
        builder.append(tableId / 16 + 1);
        int variableIndex = tableId / 16 + 1;
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
        builder.append(String.format("%.2f", getFloat(bytes, 8, 12)));
        builder.append(",");
        builder.append(String.format("%.2f", getFloat(bytes, 12, 16)));
        builder.append(",");
        builder.append(String.format("%.2f", getFloat(bytes, 16, 20)));
        builder.append(",");
        builder.append(getFloat(bytes, 20, 24));
        builder.append(",");
        builder.append(getFloat(bytes, 24, 28));
        builder.append(",");
        builder.append(getFloat(bytes, 28, 32));
        builder.append(",");
        builder.append(Integer.toHexString(getInt(bytes, 32, 34)));

        for (int ii = 0; ii < (variableIndex == 1 ? 17 : 15); ii++) {
            int index = (variableIndex - 1) * 17 + ii;
            String name;
            if (index < variableEntity.variableArray.length) {
                name = variableEntity.variableArray[index].name;
            } else {
                name = "";
            }
            if ("NULL".equals(name)) {
                continue;
            }
            int offset = 28 * ii;

            int maxTime = getInt(bytes, 58 + offset, 60 + offset);
            boolean isAccumulateInt = (maxTime & 0x8000) > 0;
            boolean isOtherInt = (maxTime & 0x4000) > 0;

            for (int i = 0; i < 6; i++) {
                if (columnList[i]) {
                    builder.append(",");
                    int begin = i * 4 + 34 + offset;
                    int end = i * 4 + 38 + offset;
                    if (isAccumulateInt && i == 5) {
                        builder.append(getInt(bytes, begin, end));
                    } else if (isOtherInt && i < 5) {
                        builder.append(getInt(bytes, begin, end));
                    } else {
                        builder.append(getFloat(bytes, begin, end));
                    }
                }
            }
            if (columnList[6]) {
                builder.append(",");
                maxTime = maxTime & 0x3fff;
                builder.append(maxTime / 60);
                builder.append(":");
                builder.append(maxTime % 60);
            }
            if (columnList[7]) {
                builder.append(",");
                int minTime = getInt(bytes, 60 + offset, 62 + offset) & 0x3fff;
                builder.append(minTime / 60);
                builder.append(":");
                builder.append(minTime % 60);
            }
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
        float value = Float.intBitsToFloat(getInt(bytes));
        return getFormatFloat(value);
    }

    public static float getFormatFloat(float value) {
        String floatStr = String.valueOf(value);
        int intLenth = floatStr.split("[.]")[0].length();
        if (floatStr.startsWith("0")) {
            intLenth--;
        }
        if (intLenth > 7) {
            intLenth = 7;
        }
        float format = (float) Math.pow(10, (7 - intLenth));
        value = (float) (int) (value * format) / format;
        return value;
    }

    public static void main(String[] args) {
        System.out.println(getFormatFloat(0.12345678f));
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
