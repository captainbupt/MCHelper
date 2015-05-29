package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.RadioGroup;

import com.vgomc.mchelper.Entity.Battery;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.BatteryChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;
import com.vgomc.mchelper.widget.SettingBatteryEditView;
import com.vgomc.mchelper.widget.TimeEditView;

import org.holoeverywhere.app.AlertDialog;
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
        }

        private void initListener() {
            mChannelListView.setOnItemClickListener(new NoScrollListView.OnNoScrollItemClickListener() {
                @Override
                public void onItemClick(View v, Object item, final int position, long id) {
                    final SettingBatteryEditView editView = new SettingBatteryEditView(mContext, position);
                    new AlertDialog.Builder(mContext).setView(editView).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Battery battery = editView.getBattery();
                            Configuration.getInstance().batteryList.set(position, battery);
                            mBatterChannelAdapter.setList(Configuration.getInstance().batteryList);
                        }
                    }).create().show();
                }
            });
        }
    }
}
