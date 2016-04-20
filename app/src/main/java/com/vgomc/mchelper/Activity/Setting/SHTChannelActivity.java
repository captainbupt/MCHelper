package com.vgomc.mchelper.activity.setting;

import android.os.Bundle;

import com.vgomc.mchelper.entity.setting.Channel;
import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.SHTChannel;
import com.vgomc.mchelper.entity.setting.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.widget.VariableEditView;

import android.widget.TextView;

import java.util.List;

/**
 * Created by weizhouh on 5/24/2015.
 */
public class SHTChannelActivity extends BaseActivity {

    private SHTChannel mSHTChannel;
    private TextView mWarmTimeTextView;
    private VariableEditView mTemperatureVariableEditView;
    private VariableEditView mHumidityVariableEditView;
    private VariableEditView mDewPointVariableEditView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_setting_channel_sht);
        initView();
        initData();
    }

    private void initView() {
        mWarmTimeTextView = (TextView) findViewById(R.id.tv_activity_setting_channel_sht_warm_time);
        mTemperatureVariableEditView = (VariableEditView) findViewById(R.id.vev_activity_setting_channel_sht_temperature);
        mHumidityVariableEditView = (VariableEditView) findViewById(R.id.vev_activity_setting_channel_sht_humidity);
        mDewPointVariableEditView = (VariableEditView) findViewById(R.id.vev_activity_setting_channel_sht_dew_point);
    }

    private void initData() {
        mSHTChannel = (SHTChannel) Configuration.getInstance().channelMap.get(Channel.SUBJECT_SHT);
        mWarmTimeTextView.setText(mSHTChannel.getWarmTime(mContext));
        List<Variable> variableList = mSHTChannel.getVariable();
        mTemperatureVariableEditView.initData(mSHTChannel.type, getResources().getString(R.string.setting_channel_sht_temperature), Channel.SUBJECT_SHT, Channel.TYPE_SIGNAL_NORMAL, variableList.get(0));
        mHumidityVariableEditView.initData(mSHTChannel.type, getResources().getString(R.string.setting_channel_sht_humidity), Channel.SUBJECT_SHT, Channel.TYPE_SIGNAL_NORMAL, variableList.get(1));
        mDewPointVariableEditView.initData(mSHTChannel.type, getResources().getString(R.string.setting_channel_sht_dew_point), Channel.SUBJECT_SHT, Channel.TYPE_SIGNAL_NORMAL, variableList.get(2));
    }

    @Override
    public void finish() {
        super.finish();
        mSHTChannel.setVariable(mTemperatureVariableEditView.getData());
        mSHTChannel.setVariable(mHumidityVariableEditView.getData());
        mSHTChannel.setVariable(mDewPointVariableEditView.getData());
        Configuration.getInstance().channelMap.put(Channel.SUBJECT_SHT, mSHTChannel);
    }
}
