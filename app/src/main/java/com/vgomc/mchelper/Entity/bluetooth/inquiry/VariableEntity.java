package com.vgomc.mchelper.entity.bluetooth.inquiry;

import android.content.Context;
import android.text.TextUtils;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.setting.Variable;
import com.vgomc.mchelper.entity.setting.VariableManager;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.ToastUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by weizhouh on 6/12/2015.
 */
public class VariableEntity extends BaseBluetoothEntity {

    public Variable[] variableArray;
    String fileName;
    Context context;

    public VariableEntity() {
    }

    public VariableEntity(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    @Override
    public String getRequest() {
        return "AT+VAR?";
    }

    @Override
    public boolean parseData(String data) {
        String[] datas = data.split(SEPERATOR);
        variableArray = new Variable[datas.length];
        try {
            for (int ii = 0; ii < datas.length; ii++) {
                String[] variableString = datas[ii].split(":");
                String[] variableInfo = variableString[1].split(",");
                String subject = variableInfo[1];
                Variable variable = new Variable(subject, true);
                variable.deviceIndex = Integer.parseInt(variableString[0]);
                variable.name = variableInfo[0].replaceAll("\"", "");
                variable.subjectName = variableInfo[1];
                variable.setDataType(variableInfo[2]);
                variable.sensorAddress = variableInfo[3].replaceAll("\"", "");
                variable.setRegisterAddress(Integer.parseInt(variableInfo[4]));
                variable.factors[0] = Float.parseFloat(variableInfo[5]);
                variable.factors[1] = Float.parseFloat(variableInfo[6]);
                variable.factors[2] = Float.parseFloat(variableInfo[7]);
                variable.factors[3] = Float.parseFloat(variableInfo[8]);
                if (variable.factors[0] == 0.0f && variable.factors[1] == 1.0f && variable.factors[2] == 0.0f && variable.factors[3] == 0.0f) {
                    variable.isFormulaOn = false;
                } else {
                    variable.isFormulaOn = true;
                }
                variableArray[ii] = variable;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (TextUtils.isEmpty(fileName) || context == null) {
            return true;
        }
        IOException ioException = null;
        try {
            writeNameToFile(context, fileName, variableArray);
        } catch (IOException e) {
            ioException = e;
            ToastUtil.showToast(context, e.toString());
            try {
                FileWriter fileWriter = new FileWriter(FileServiceProvider.getExternalPath(context) + File.separator + "crash.info");
                fileWriter.append(ioException.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e1) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }

    public void writeNameToFile(Context context, String fileName, Variable[] variableArray) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("变量组,存储表,时间,电池电压,备用电压,时钟电压,经度,纬度,海拔,设备状态,");
        for (int ii = 0; ii < VariableManager.variableMaxCount; ii++) {
            String name;
            if (ii < variableArray.length) {
                name = variableArray[ii].name;
            } else {
                name = "";
            }
            builder.append(name);
            builder.append("实时值,");
            builder.append(name);
            builder.append("平均值,");
            builder.append(name);
            builder.append("最大值,");
            builder.append(name);
            builder.append("最小值,");
            builder.append(name);
            builder.append("时段累积,");
            builder.append(name);
            builder.append("永久累积,");
            builder.append(name);
            builder.append("最大值时间,");
            builder.append(name);
            builder.append("最小值时间,");
        }
        builder.deleteCharAt(builder.length() - 1);
        FileServiceProvider.saveRecord(context, fileName, builder.toString());
    }
}
