package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.internal.view.menu.ContextMenuCallbackGetter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.dialog.BigNumberPickerDialog;
import com.vgomc.mchelper.widget.MyBigNumberPicker;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/20/2015.
 */
public class SystemInfoView extends BaseCollapsibleView {

    public SystemInfoView(Context context) {
        super(context);
        setContentView(new SystemInfoContentView(context));
        setTitle(R.string.setting_system_info_title);
    }


    public class SystemInfoContentView extends BaseCollapsibleContentView {
        private EditText mNameEditText;
        private EditText mPasswordEditText;
        private TextView mTimeZoneTextView;
        private EditText mBluetoothTimeEditText;
        private TextView mBluetoothTimeUnitTextView;
        private Switch mBluetoothTimeSwitch;

        public SystemInfoContentView(Context context) {
            super(context);
            initView();
            updateData();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_system_info;
        }

        @Override
        protected void updateData() {
            mNameEditText.setText(Configuration.getInstance().name);
            mPasswordEditText.setText(Configuration.getInstance().password);
            mTimeZoneTextView.setText(Configuration.getInstance().timeZone + "");
            mBluetoothTimeSwitch.setChecked(Configuration.getInstance().bluetoothTimeOn);
            mBluetoothTimeEditText.setText(Configuration.getInstance().bluetoothTime + "");
            if (Configuration.getInstance().bluetoothTimeOn) {
                showBluetooth();
            } else {
                hideBluetooth();
            }
        }

        private void initView() {
            mNameEditText = (EditText) findViewById(R.id.et_setting_system_info_name);
            mPasswordEditText = (EditText) findViewById(R.id.et_setting_system_info_password);
            mTimeZoneTextView = (TextView) findViewById(R.id.tv_setting_system_info_zone);
            mBluetoothTimeEditText = (EditText) findViewById(R.id.et_setting_system_info_bluetooth_time);
            mBluetoothTimeUnitTextView = (TextView) findViewById(R.id.tv_setting_system_info_bluetooth_time_unit);
            mBluetoothTimeSwitch = (Switch) findViewById(R.id.sw_setting_system_info_bluetooth_time);
            mBluetoothTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        showBluetooth();
                    } else {
                        hideBluetooth();
                    }
                }
            });
            mBluetoothTimeEditText.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        final MyBigNumberPicker numberPicker = new MyBigNumberPicker(mContext, 1440, 1, 4, Configuration.getInstance().bluetoothTime);
                        new AlertDialog.Builder(mContext).setTitle(R.string.setting_system_info_content_bluetooth_time).setView(numberPicker)
                                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int time = numberPicker.getValue();
                                        mBluetoothTimeEditText.setText(time + "");
                                        Configuration.getInstance().bluetoothTime = time;
                                    }
                                }).create().show();
                    }
                    return true;
                }
            });
        }

        private void hideBluetooth() {
            mBluetoothTimeEditText.setVisibility(View.GONE);
            mBluetoothTimeUnitTextView.setVisibility(View.GONE);
        }

        private void showBluetooth() {
            mBluetoothTimeEditText.setVisibility(View.VISIBLE);
            mBluetoothTimeUnitTextView.setVisibility(View.VISIBLE);
        }
    }
}
