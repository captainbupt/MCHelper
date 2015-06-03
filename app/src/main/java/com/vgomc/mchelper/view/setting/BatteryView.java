package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.vgomc.mchelper.Entity.setting.Battery;
import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.BatteryChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;
import com.vgomc.mchelper.widget.BatteryEditView;

import org.holoeverywhere.app.AlertDialog;

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
            mBatterChannelAdapter = new BatteryChannelAdapter(mContext);
            mChannelListView.setAdapter(mBatterChannelAdapter);
            mBatterChannelAdapter.setList(Configuration.getInstance().batteryList);
        }

        private void initListener() {
            mChannelListView.setOnItemClickListener(new NoScrollListView.OnNoScrollItemClickListener() {
                @Override
                public void onItemClick(View v, Object item, final int position, long id) {
                    final BatteryEditView editView = new BatteryEditView(mContext, position);
                    new AlertDialog.Builder(mContext).setView(editView).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Battery battery = editView.getBattery();
                            Configuration.getInstance().batteryList.set(position, battery);
                            updateLaterBatteryTime(position);
                            mBatterChannelAdapter.setList(Configuration.getInstance().batteryList);
                        }
                    }).create().show();
                }
            });
        }
    }

    private void updateLaterBatteryTime(int position) {
        for (int ii = position; ii < Configuration.getInstance().batteryList.size() - 1; ii++) {
            Battery currentBattery = (Battery) Configuration.getInstance().batteryList.get(position);
            Battery nextBattery = (Battery) Configuration.getInstance().batteryList.get(position + 1);
            if (nextBattery.isOrder) {
                nextBattery.startTime = currentBattery.startTime + currentBattery.liveTime;
                if (nextBattery.startTime + nextBattery.liveTime > Battery.MAX_TIME) {
                    nextBattery.liveTime = Battery.MAX_TIME - nextBattery.startTime;
                }
                Configuration.getInstance().batteryList.set(position + 1, nextBattery);
            }
        }
    }
}
