package com.vgomc.mchelper.Activity.Setting;

import android.os.Bundle;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.widget.VariableEditView;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.EditText;

/**
 * Created by weizhouh on 5/24/2015.
 */
public class SHTChannelActivity extends BaseActivity {

    private Channel mSHTChannel;
    private EditText mWarmTimeEditText;
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
        mWarmTimeEditText = (EditText) findViewById(R.id.et_activity_setting_channel_sht_warm_time);
        mTemperatureVariableEditView = (VariableEditView) findViewById(R.id.vev_activity_setting_channel_sht_temperature);
        mHumidityVariableEditView = (VariableEditView) findViewById(R.id.vev_activity_setting_channel_sht_humidity);
        mDewPointVariableEditView = (VariableEditView) findViewById(R.id.vev_activity_setting_channel_sht_dew_point);
    }

    private void initData() {
        mSHTChannel = Configuration.getInstance().channelMap.get(Channel.SUBJECT_SHT);
        mWarmTimeEditText.setText(mSHTChannel.warmTime + "");
        mTemperatureVariableEditView.initData(mSHTChannel.type, getResources().getString(R.string.setting_channel_sht_temperature), mSHTChannel.variables.get(0));
        mHumidityVariableEditView.initData(mSHTChannel.type, getResources().getString(R.string.setting_channel_sht_humidity), mSHTChannel.variables.get(1));
        mDewPointVariableEditView.initData(mSHTChannel.type, getResources().getString(R.string.setting_channel_sht_dew_point), mSHTChannel.variables.get(2));
    }

    @Override
    public void finish() {
        super.finish();
        mSHTChannel.warmTime = Integer.parseInt(mWarmTimeEditText.getText().toString());
        mSHTChannel.variables.set(0, mTemperatureVariableEditView.getData());
        mSHTChannel.variables.set(1, mHumidityVariableEditView.getData());
        mSHTChannel.variables.set(2, mDewPointVariableEditView.getData());
        Configuration.getInstance().channelMap.put(Channel.SUBJECT_SHT, mSHTChannel);
    }
}
