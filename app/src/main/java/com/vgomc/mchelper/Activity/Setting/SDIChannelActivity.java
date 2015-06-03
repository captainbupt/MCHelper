package com.vgomc.mchelper.activity.setting;

import android.os.Bundle;

import com.vgomc.mchelper.Entity.setting.Channel;
import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.widget.MultiVariableView;

import org.holoeverywhere.widget.EditText;

/**
 * Created by weizhouh on 5/25/2015.
 */
public class SDIChannelActivity extends BaseActivity {

    private Channel mSDIChannel;

    private EditText mWarmTimeEditText;
    private MultiVariableView mVariableListView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_setting_channel_sdi);
        initView();
        initDate();
    }

    @Override
    public void finish() {
        super.finish();
        mSDIChannel.warmTime = Integer.parseInt(mWarmTimeEditText.getText().toString());
        Configuration.getInstance().channelMap.put(Channel.SUBJECT_SDI, mSDIChannel);
    }

    private void initView() {
        mWarmTimeEditText = (EditText) findViewById(R.id.et_activity_setting_channel_sdi_warm_time);
        mVariableListView = (MultiVariableView) findViewById(R.id.mvv_activity_setting_channel_sdi_variables);
    }

    private void initDate() {
        mSDIChannel = Configuration.getInstance().channelMap.get(Channel.SUBJECT_SDI);
        mWarmTimeEditText.setText(mSDIChannel.warmTime + "");
        mVariableListView.setData(mSDIChannel.subject, 9);
    }

}
