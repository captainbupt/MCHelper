package com.vgomc.mchelper.entity.setting;

import android.content.Context;

import com.vgomc.mchelper.base.AppApplication;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.FileUtil;
import com.vgomc.mchelper.utility.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by weizhouh on 6/13/2015.
 */
public class VariableManager implements Serializable {
    // 最大variable数量
    public final static int variableMaxCount = 17;

    Map<String, List<Variable>> mVariableMap;
    // 自己维护一个index， 保证每个variable都有唯一ID，不论是否打开
    private int mMaxIndex;

    public VariableManager() {
        mVariableMap = new HashMap<>();
        mMaxIndex = 0;
    }

    public int getMaxIndex() {
        return mMaxIndex;
    }

    public void clear() {
        mMaxIndex = 0;
        for (List<Variable> list : mVariableMap.values()) {
            if (list != null) {
                list.clear();
            }
        }
    }

    private void addVariable(Variable variable) {
        List<Variable> variableList = mVariableMap.get(variable.subjectName);
        if (variableList == null) {
            variableList = new ArrayList<>();
        }
        variable.index = mMaxIndex;
        variableList.add(variable);
        mVariableMap.put(variable.subjectName, variableList);
        mMaxIndex++;
    }

    public void setVariable(Variable variable) {
        if (variable.index == -1 || variable.index >= mMaxIndex) {
            addVariable(variable);
            return;
        }
        List<Variable> variableList = mVariableMap.get(variable.subjectName);
        if (variableList == null) {
            variableList = new ArrayList<>();
        }
        int position = 0;
        for (Variable tmpVariable : variableList) {
            if (tmpVariable.index == variable.index) {
                variableList.set(position, variable);
                return;
            }
            position++;
        }
        variableList.add(variable);
    }

    public void deleteVariable(String subject, int index) {
        List<Variable> variableList = mVariableMap.get(subject);
        if (variableList != null) {
            int position = 0;
            for (Variable variable : variableList) {
                if (variable.index == index) {
                    variableList.remove(position);
                    return;
                }
                position++;
            }
        }
    }

    public Variable getVariable(int index) {
        for (List<Variable> list : mVariableMap.values()) {
            if (list != null) {
                for (Variable variable : list) {
                    if (variable.index == index) {
                        return variable;
                    }
                }
            }
        }
        return null;
    }

    public Variable getVariableByDeviceIndex(int index) {
        for (List<Variable> list : mVariableMap.values()) {
            if (list != null) {
                for (Variable variable : list) {
                    if (variable.deviceIndex == index) {
                        return variable;
                    }
                }
            }
        }
        return null;
    }

    public List<Variable> getVariableList(String subject) {
        return mVariableMap.get(subject);
    }

    public List<Variable> getAllVariableList() {
        List<Variable> variableList = new ArrayList<>();
        int index = 1;
        for (int ii = 0; ii < Channel.SUBJECTS.length; ii++) {
            List<Variable> list = mVariableMap.get(Channel.SUBJECTS[ii]);
            if (list != null && list.size() > 0) {
                for (Variable variable : list) {
                    if (variable.isVariableOn) {
                        variable.deviceIndex = index;
                        variableList.add(variable);
                        index++;
                    }
                }
            }
        }
        return variableList;
    }

    public boolean isVariableMax(Variable currentVariable) {
        int count = 0;
        boolean isCurrentVariableOn = false;
        for (List<Variable> list : mVariableMap.values()) {
            if (list != null) {
                for (Variable variable : list) {
                    if (variable.isVariableOn) {
                        if (currentVariable != null && currentVariable.index == variable.index) {
                            //当前变量已经打开，只是临时修改，因此不需要考虑满格的问题
                            isCurrentVariableOn = true;
                        }
                        count++;
                    }
                }
            }
        }

        return !isCurrentVariableOn && count >= variableMaxCount;
    }

    public void clear(String subject) {
        List<Variable> variableList = mVariableMap.get(subject);
        if (variableList != null) {
            variableList.clear();
        }
    }

}
