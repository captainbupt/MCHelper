package com.vgomc.mchelper.view.setting;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.BatteryChannelAdapter;
import com.vgomc.mchelper.adapter.setting.SingleChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;

import org.holoeverywhere.widget.Switch;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class ChannelSingleView extends BaseCollapsibleView {
    public ChannelSingleView(Context context) {
        super(context);
        setTitle(R.string.setting_channel);
        setContentView(new ChannelSingleContentView(context));
    }

    private class ChannelSingleContentView extends BaseCollapsibleContentView {

        private NoScrollListView mSingleListView;
        private NoScrollListView mMultipleListView;

        public ChannelSingleContentView(Context context) {
            super(context);
            initView();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_channel;
        }

        private void initView() {
            mSingleListView = (NoScrollListView) findViewById(R.id.nslv_setting_channel_single);
            mMultipleListView = (NoScrollListView) findViewById(R.id.nslv_setting_channel_multiple);
            mSingleListView.setAdapter(new SingleChannelAdapter(mContext));
        }
    }
}
