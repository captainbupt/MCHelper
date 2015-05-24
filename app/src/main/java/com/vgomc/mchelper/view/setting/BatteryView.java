package com.vgomc.mchelper.view.setting;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.BatteryChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.Switch;

/**
 * Created by weizhouh on 5/21/2015.
 */
public class BatteryView extends BaseCollapsibleView {
    public BatteryView(Context context) {
        super(context);
        setTitle(R.string.setting_battery);
        setContentView(new BatterContentView(context));
    }

    private class BatterContentView extends BaseCollapsibleContentView {

        private Switch mPatternSwitch;
        private NoScrollListView mChannelListView;
        private BatteryChannelAdapter mBatterChannelAdapter;

        public BatterContentView(Context context) {
            super(context);
            initView();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_battery;
        }

        @Override
        protected void updateData() {

        }

        private void initView() {
            mPatternSwitch = (Switch) findViewById(R.id.sw_setting_battery_pattern);
            mChannelListView = (NoScrollListView) findViewById(R.id.nslv_setting_battery);
            mBatterChannelAdapter = new BatteryChannelAdapter(mContext, true);
            mChannelListView.setAdapter(mBatterChannelAdapter);
        }
    }
}
