package com.vgomc.mchelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.vgomc.mchelper.entity.setting.Channel;
import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.ChannelVariableAdapter;
import com.vgomc.mchelper.utility.ToastUtil;

import android.widget.Button;
import android.widget.LinearLayout;

public class MultiVariableView extends LinearLayout {

    private Channel mChannel;
    private int MAX_VARIABLE_COUNT;

    private Context mContext;
    private Button mAddVariableButton;
    private NoScrollListView mVariableListView;
    private ChannelVariableAdapter mVariableAdapter;

    public MultiVariableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_channel_multi_variable, this);
        initView();
        initListener();
    }

    private void initView() {
        mAddVariableButton = (Button) findViewById(R.id.btn_view_setting_channel_multi_variable_add);
        mVariableListView = (NoScrollListView) findViewById(R.id.nslv_view_setting_channel_multi_variable);
        mVariableAdapter = new ChannelVariableAdapter(mContext);
        mVariableListView.setAdapter(mVariableAdapter);
    }

    private void initListener() {
        mVariableListView.setOnItemClickListener(new NoScrollListView.OnNoScrollItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position, long id) {
                showVariableDialog(position, (Variable) mVariableAdapter.getItem(position));
            }
        });
        mAddVariableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Configuration.getInstance().variableManager.isVariableMax()) {
                    ToastUtil.showToast(mContext, R.string.tip_variable_reach_max_count);
                    return;
                }
                showVariableDialog(-1, new Variable(mChannel.subject, false));
            }
        });
    }

    public void setData(String subject, int max_variable) {
        MAX_VARIABLE_COUNT = max_variable;
        this.mChannel = Configuration.getInstance().channelMap.get(subject);
        mVariableAdapter.setVariabletList(mChannel.getVariable());
    }

    public void removeAllVariable() {
        mVariableAdapter.setVariabletList(mChannel.getVariable());
    }

    private void showVariableDialog(final int position, Variable variable) {
        final VariableEditView editView = new VariableEditView(mContext);
        editView.initData(mChannel.type, mChannel.subject + "通道", mChannel.subject, mChannel.signalType, variable);
        new AlertDialog.Builder(mContext).setView(editView).setPositiveButton(R.string.dialog_confirm, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Variable variable = editView.getData();
                if (variable != null) {
                    if (variable.isVariableOn) {
                        if (mChannel.getVariableCountInSensor(variable.sensorAddress) >= MAX_VARIABLE_COUNT) {
                            ToastUtil.showToast(mContext, "添加失败，该传感器可支持变量数已达上限");
                            return;
                        }
                        mChannel.setVariable(variable);
                    } else {
                        mChannel.removeVariable(variable);
                    }
                    mVariableAdapter.setVariabletList(mChannel.getVariable());
                } else {
                    ToastUtil.showToast(mContext, R.string.tip_invalid_input);
                }
            }
        }).create().show();
    }

}

