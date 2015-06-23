package com.vgomc.mchelper.activity.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.vgomc.mchelper.entity.setting.Channel;
import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.RS485Channel;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.utility.BigNumberPickerDialog;
import com.vgomc.mchelper.widget.MultiVariableView;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/25/2015.
 */
public class RS485ChannelActivity extends BaseActivity {

    private RS485Channel mRS485Channel;

    private RadioGroup mModeReadioGroup;
    private RadioButton mModeMasterRadioButton;
    private RadioButton mModeSlaveRadioButton;
    private RadioGroup mProtocolRadioGroup;
    private RadioButton mProtocolRTURadioButton;
    private RadioButton mProtocolASCIIRadioButton;
    private EditText mSlaveAddressEditText;
    private Spinner mBaudRateSpinner;
    private ArrayAdapter<String> mBaudRateAdapter;
    private TextView mWarmTimeTextView;
    private LinearLayout mVariableLayout;
    private MultiVariableView mVariableListView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_setting_channel_rs485);
        initView();
        initListener();
        initDate();
    }

    @Override
    public void finish() {
        super.finish();
        mRS485Channel.mode = mModeReadioGroup.getCheckedRadioButtonId() == R.id.rb_activity_setting_channel_rs485_mode_master ? RS485Channel.TYPE_MODE_MASTER : RS485Channel.TYPE_MODE_SLAVE;
        mRS485Channel.protocol = mProtocolRadioGroup.getCheckedRadioButtonId() == R.id.rb_activity_setting_channel_rs485_protocol_rtu ? RS485Channel.TYPE_PROTOCOL_RTU : RS485Channel.TYPE_PROTOCOL_ASCII;
        mRS485Channel.slaveAddress = Integer.parseInt(mSlaveAddressEditText.getText().toString());
        mRS485Channel.baudRate = Integer.parseInt(mBaudRateAdapter.getItem(mBaudRateSpinner.getSelectedItemPosition()));
        Configuration.getInstance().channelMap.put(Channel.SUBJECT_RS485, mRS485Channel);
    }

    private void initView() {
        mModeReadioGroup = (RadioGroup) findViewById(R.id.rg_activity_setting_channel_rs485_mode);
        mModeMasterRadioButton = (RadioButton) findViewById(R.id.rb_activity_setting_channel_rs485_mode_master);
        mModeSlaveRadioButton = (RadioButton) findViewById(R.id.rb_activity_setting_channel_rs485_mode_slave);
        mProtocolRadioGroup = (RadioGroup) findViewById(R.id.rg_activity_setting_channel_rs485_protocol);
        mProtocolRTURadioButton = (RadioButton) findViewById(R.id.rb_activity_setting_channel_rs485_protocol_rtu);
        mProtocolASCIIRadioButton = (RadioButton) findViewById(R.id.rb_activity_setting_channel_rs485_protocol_ascii);
        mSlaveAddressEditText = (EditText) findViewById(R.id.et_activity_setting_channel_rs485_slave_address);
        mBaudRateSpinner = (Spinner) findViewById(R.id.sp_activity_setting_channel_rs485_baud);
        mWarmTimeTextView = (TextView) findViewById(R.id.tv_activity_setting_channel_rs485_warm_time);
        mBaudRateAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setting_channel_rs485_baud));
        mBaudRateSpinner.setAdapter(mBaudRateAdapter);
        mVariableLayout = (LinearLayout) findViewById(R.id.ll_activity_setting_channel_rs485_variables);
        mVariableListView = (MultiVariableView) findViewById(R.id.mvv_activity_setting_channel_rs485_variables);
    }

    private void initListener() {
        mModeReadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_activity_setting_channel_rs485_mode_slave) {
                    new AlertDialog.Builder(mContext).setTitle("确认转为从机模式？").setMessage("已有变量将会被清除(RS485和SDI)").setPositiveButton("确认", new AlertDialog.OnClickListener(

                    ) {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setSlaveMode();
                        }
                    }).setNegativeButton("取消", new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mModeMasterRadioButton.setChecked(true);
                        }
                    }).create().show();
                } else {
                    setMasterMode();
                }
            }
        });
        mSlaveAddressEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    BigNumberPickerDialog.getBigNumberPickerDialog(mContext, 3, 0, 255, Integer.parseInt(mSlaveAddressEditText.getText().toString()), mSlaveAddressEditText, getResources().getString(R.string.setting_channel_rs485_slave_address)).show();
                }
                return true;
            }
        });
    }

    private void initDate() {
        mRS485Channel = (RS485Channel) Configuration.getInstance().channelMap.get(Channel.SUBJECT_RS485);
        if (mRS485Channel.mode == RS485Channel.TYPE_MODE_MASTER) {
            mModeMasterRadioButton.setChecked(true);
            setMasterMode();
        } else {
            mModeSlaveRadioButton.setChecked(true);
            setSlaveMode();
        }
        if (mRS485Channel.protocol == RS485Channel.TYPE_PROTOCOL_RTU) {
            mProtocolRTURadioButton.setChecked(true);
        } else {
            mProtocolASCIIRadioButton.setChecked(true);
        }
        mSlaveAddressEditText.setText(mRS485Channel.slaveAddress + "");
        mBaudRateSpinner.setSelection(0);
        for (int ii = 0; ii < mBaudRateAdapter.getCount(); ii++) {
            if (mRS485Channel.baudRate == Integer.parseInt(mBaudRateAdapter.getItem(ii))) {
                mBaudRateSpinner.setSelection(ii);
            }
        }
        mWarmTimeTextView.setText(mRS485Channel.getWarmTime(mContext));
        mVariableListView.setData(mRS485Channel.subject, 32);
    }

    private void setSlaveMode() {
        mVariableLayout.setVisibility(View.GONE);
        Configuration.getInstance().variableManager.clear(mRS485Channel.subject);
        Configuration.getInstance().variableManager.clear(Channel.SUBJECT_SDI);
        mVariableListView.removeAllVariable();
    }

    private void setMasterMode() {
        mVariableLayout.setVisibility(View.VISIBLE);
    }
}
