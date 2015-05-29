package com.vgomc.mchelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.Entity.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.ChannelVariableAdapter;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * Created by weizhouh on 5/26/2015.
 */
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
                if (Configuration.getInstance().getChannelVariableCount() >= Configuration.getInstance().channelVariableMaxCount) {
                    ToastUtil.showToast(mContext, R.string.tip_variable_reach_max_count);
                    return;
                }
                showVariableDialog(-1, new Variable());
            }
        });
    }

    public void setData(String subject, int max_variable) {
        MAX_VARIABLE_COUNT = max_variable;
        this.mChannel = Configuration.getInstance().channelMap.get(subject);
        mVariableAdapter.setVariabletList(mChannel.variables);
    }

    public void removeAllVariable() {
        mChannel.variables.clear();
        mVariableAdapter.setVariabletList(mChannel.variables);
    }

    private void showVariableDialog(final int position, Variable variable) {
        final VariableEditView editView = new VariableEditView(mContext);
        editView.initData(mChannel.type, mChannel.subject + "通道", variable);
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
                        if (position >= 0) {
                            mChannel.variables.set(position, variable);
                        } else {
                            mChannel.variables.add(variable);
                        }
                    } else {
                        if (position >= 0) {
                            mChannel.variables.remove(position);
                        }
                    }
                    mVariableAdapter.setVariabletList(mChannel.variables);
                } else {
                    ToastUtil.showToast(mContext, R.string.tip_invalid_input);
                    try {
                        Field field = dialog.getClass()
                                .getSuperclass().getDeclaredField(
                                        "mShowing");
                        field.setAccessible(true);
                        //   将mShowing变量设为false，表示对话框已关闭
                        field.set(dialog, false);
                        dialog.dismiss();
                    } catch (Exception e) {
                    }
                }
            }
        }).create().show();
    }

}

