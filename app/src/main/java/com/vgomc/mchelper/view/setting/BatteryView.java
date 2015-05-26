package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.widget.RadioGroup;

import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.BatteryChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;

import org.holoeverywhere.widget.RadioButton;

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

        private RadioGroup mPatternRadioGroup;
        private RadioButton mOrderPatternRadioButton;
        private RadioButton mCustomPatternRadioButton;
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
            mPatternRadioGroup = (RadioGroup) findViewById(R.id.rg_view_setting_battery_pattern);
            mOrderPatternRadioButton = (RadioButton) findViewById(R.id.rb_adapter_setting_battery_mode_order);
            mCustomPatternRadioButton = (RadioButton) findViewById(R.id.rb_adapter_setting_battery_mode_always);
            mChannelListView = (NoScrollListView) findViewById(R.id.nslv_setting_battery);
            mBatterChannelAdapter = new BatteryChannelAdapter(mContext, Configuration.getInstance().isBatteryOrder);
            mChannelListView.setAdapter(mBatterChannelAdapter);
            mBatterChannelAdapter.setList(Configuration.getInstance().batteryList);

            if (Configuration.getInstance().isBatteryOrder) {
                mOrderPatternRadioButton.setChecked(true);
            }
            else{
                mCustomPatternRadioButton.setChecked(true);
            }
        }

        private void initListener() {
            mPatternRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    boolean isOrder = checkedId == R.id.rb_view_setting_battery_pattern_order;
                    if (!isOrder) {
                        // 系统自动生成模式
                    }
                    Configuration.getInstance().isBatteryOrder = isOrder;
                    mBatterChannelAdapter.setIsOrder(isOrder);
                }
            });
        }
    }
}
