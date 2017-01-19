package com.vgomc.mchelper.entity.setting;

/**
 * Created by weizhouh on 5/24/2015.
 */
public class Variable {
    public final static int COUNT_FACTORS = 4;
    public final static String[] DATA_TYPE = new String[]{"BIT", "U16", "S16", "U32M", "S32M", "U32L", "S32L", "FP32M", "FP32L"};

    public int deviceIndex;
    public int index = -1;
    public String subjectName = null;
    public boolean isVariableOn = true;
    public String name = "默认名称";
    public boolean isFormulaOn = false;
    public float[] factors = new float[]{0f, 1f, 0f, 0f};
    public String sensorAddress = "1";
    public int registerAddress = 1;
    public int registerType = 0;
    public int dataType = 0;

    public Variable() {
    }

    ;

    public Variable(String subjectName, boolean isVariableOn) {
        this.subjectName = subjectName;
        this.isVariableOn = isVariableOn;
    }

    public Variable(String subjectName, boolean isVariableOn, int registerAddress) {
        this(subjectName, isVariableOn);
        setRegisterAddress(registerAddress);
    }

    public void setDataType(String type) throws Exception {
        for (int ii = 0; ii < DATA_TYPE.length; ii++) {
            if (DATA_TYPE[ii].equals(type)) {
                dataType = ii;
                return;
            }
        }
        throw new Exception("不支持的数据类型:" + type);
    }

    public void setRegisterAddress(int registerAddress) {
        if (subjectName.equals(Channel.SUBJECT_RS485)) {
            this.registerAddress = registerAddress % 100000;
            this.registerType = registerAddress / 100000;
            if (this.registerType >= 3) {
                this.registerType--;
            }
        } else {
            this.registerAddress = registerAddress;
        }
    }

    public int getRegisterAddress() {
        if (subjectName.equals(Channel.SUBJECT_RS485)) {
            int address = registerAddress + registerType * 100000;
            if (registerType >= 2) {
                address += 100000;
            }
            return address;
        } else {
            return registerAddress;
        }
    }
}
