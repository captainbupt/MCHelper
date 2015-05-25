package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.widget.DefaultFormulaTextView;

import org.holoeverywhere.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/25/2015.
 */
public class ChannelVariableAdapter extends MyBaseAdapter {

    public ChannelVariableAdapter(Context context, List<Variable> list) {
        super(context);
        mList = new ArrayList<>();
        for (Object o : list) {
            Variable variable = (Variable) o;
            if (variable.isVariableOn) {
                mList.add(variable);
            }
        }
    }

    public ChannelVariableAdapter(Context context) {
        super(context);
    }

    public void setVariabletList(List<Variable> list) {
        mList = new ArrayList<>();
        for (Object o : list) {
            Variable variable = (Variable) o;
            if (variable.isVariableOn) {
                mList.add(variable);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_setting_channel_multiple_variable, null);
        }
        Variable variable = (Variable) getItem(position);
        TextView variableNameTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_multiple_variable_name);
        DefaultFormulaTextView defaultFormulaTextView = (DefaultFormulaTextView) convertView.findViewById(R.id.dftv_adapter_setting_channel_multiple_variable_formula);
        variableNameTextView.setText(variable.name);
        defaultFormulaTextView.setText(variable.factors);
        return convertView;
    }
}