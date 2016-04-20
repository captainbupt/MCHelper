package com.vgomc.mchelper.activity.setting;

import android.os.Bundle;

import com.vgomc.mchelper.entity.setting.Channel;
import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.widget.MultiVariableView;

import android.widget.TextView;

/**
 * Created by weizhouh on 5/25/2015.
 */
public class SDIChannelActivity extends BaseActivity {

    private Channel mSDIChannel;

    private TextView mWarmTimeTextView;
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
        Configuration.getInstance().channelMap.put(Channel.SUBJECT_SDI, mSDIChannel);
    }

    private void initView() {
        mWarmTimeTextView = (TextView) findViewById(R.id.tv_activity_setting_channel_sdi_warm_time);
        mVariableListView = (MultiVariableView) findViewById(R.id.mvv_activity_setting_channel_sdi_variables);
    }

    private void initDate() {
        mSDIChannel = Configuration.getInstance().channelMap.get(Channel.SUBJECT_SDI);
        mWarmTimeTextView.setText(mSDIChannel.getWarmTime(mContext));
        mVariableListView.setData(mSDIChannel.subject, 9);
    }

}
