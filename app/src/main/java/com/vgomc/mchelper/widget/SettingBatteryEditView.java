package com.vgomc.mchelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.vgomc.mchelper.Entity.Battery;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.RadioButton;

/**
 * Created by weizhouh on 5/29/2015.
 */

public class SettingBatteryEditView extends LinearLayout {

    private Context mContext;
    private RadioGroup mModeRadioGroup;
    private RadioGroup mPatternRadioGroup;
    private RadioButton mModeAutoRadioButton;
    private RadioButton mModeAlwaysRadioButton;
    private RadioButton mPatternOrderRadioButton;
    private RadioButton mPatternCustomRadioButton;
    private TimeEditView mBeginTimeEditView;
    private TimeEditView mLiveTimeEditView;
    private LinearLayout mContentLayout;

    private int mPosition;
    private long mLastBatteryEndTime;
    private Battery mBattery;

    public SettingBatteryEditView(Context context, int position) {
        super(context);
        this.mContext = context;
        this.mPosition = position;
        this.mBattery = (Battery) Configuration.getInstance().batteryList.get(position);
        if (position == 0) {
            mLastBatteryEndTime = 0;
        } else {
            Battery lastBattery = (Battery) Configuration.getInstance().batteryList.get(position - 1);
            mLastBatteryEndTime = lastBattery.startTime + lastBattery.liveTime;
        }
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_battery_edit, this);
        initView();
        initListener();
    }

    private void initView() {
        mModeRadioGroup = (RadioGroup) findViewById(R.id.rg_view_setting_battery_edit_mode);
        mPatternRadioGroup = (RadioGroup) findViewById(R.id.rg_view_setting_battery_edit_pattern);
        mModeAutoRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_battery_edit_mode_auto);
        mModeAlwaysRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_battery_edit_mode_always);
        mPatternOrderRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_battery_edit_pattern_order);
        mPatternCustomRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_battery_edit_pattern_custom);
        mBeginTimeEditView = (TimeEditView) findViewById(R.id.tev_view_setting_battery_edit_time_begin);
        mLiveTimeEditView = (TimeEditView) findViewById(R.id.tev_view_setting_battery_edit_time_last);
        mContentLayout = (LinearLayout) findViewById(R.id.ll_view_setting_battery_edit_detail);
    }

    private void initListener() {
        mModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_view_setting_battery_edit_mode_auto) {
                    mContentLayout.setVisibility(View.VISIBLE);
                } else {
                    mContentLayout.setVisibility(View.GONE);
                }
            }
        });
        mPatternRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_view_setting_battery_edit_pattern_order) {
                    mBeginTimeEditView.setTime(mLastBatteryEndTime);
                    mBeginTimeEditView.setEnabled(false);
                } else {
                    mBeginTimeEditView.setEnabled(true);
                }
            }
        });
        mBeginTimeEditView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final MyTimePickerView view = new MyTimePickerView(mContext, MyTimePickerView.MODE_MSM, mBattery.startTime, Battery.MAX_TIME);
                new AlertDialog.Builder(mContext).setTitle(R.string.setting_time_picker_tip_begin).setView(view).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long startTime = view.getTime();
                        mBeginTimeEditView.setTime(startTime);
                        if (startTime + mLiveTimeEditView.getTime() > Battery.MAX_TIME) {
                            long liveTime = Battery.MAX_TIME - startTime;
                            mLiveTimeEditView.setTime(liveTime);
                        }
                    }
                }).create().show();
                return true;
            }
        });
        mLiveTimeEditView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final MyTimePickerView view = new MyTimePickerView(mContext, MyTimePickerView.MODE_MSM, mBattery.liveTime, Battery.MAX_TIME - mBattery.startTime);
                new AlertDialog.Builder(mContext).setTitle(R.string.setting_time_picker_tip_begin).setView(view).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long liveTime = view.getTime();
                        mLiveTimeEditView.setTime(liveTime);
                    }
                }).create().show();
                return true;
            }
        });
    }

    public Battery getBattery() {
        mBattery.isAlwaysOn = mModeRadioGroup.getCheckedRadioButtonId() == R.id.rb_view_setting_battery_edit_mode_always;
        mBattery.isOrder = mPatternRadioGroup.getCheckedRadioButtonId() == R.id.rb_view_setting_battery_edit_pattern_order;
        mBattery.startTime = mBeginTimeEditView.getTime();
        mBattery.liveTime = mLiveTimeEditView.getTime();
        return mBattery;
    }

}
