package com.vgomc.mchelper.view.setting;

import android.content.Context;

import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.BatteryChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;

/**
 * Created by weizhouh on 5/21/2015.
 */
public class StorageView extends BaseCollapsibleView {
    public StorageView(Context context) {
        super(context);
        setTitle(R.string.setting_battery);
        setContentView(new BatterContentView(context));
    }

    private class BatterContentView extends BaseCollapsibleContentView {

        private NoScrollListView mChannelListView;
        private BatteryChannelAdapter mBatterChannelAdapter;

        public BatterContentView(Context context) {
            super(context);
            initView();
            initListener();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_battery;
        }

        @Override
        protected void updateData() {
            mBatterChannelAdapter.notifyDataSetChanged();
        }

        private void initView() {
            mChannelListView = (NoScrollListView) findViewById(R.id.nslv_setting_battery);
            mBatterChannelAdapter = new BatteryChannelAdapter(mContext, Configuration.getInstance().isBatteryOrder);
            mChannelListView.setAdapter(mBatterChannelAdapter);
            mBatterChannelAdapter.setList(Configuration.getInstance().batteryList);
/*
            if (Configuration.getInstance().isBatteryOrder) {
                mOrderPatternRadioButton.setChecked(true);
            }
            else{
                mCustomPatternRadioButton.setChecked(true);
            }*/
        }

        private void initListener() {

        }
    }
}
