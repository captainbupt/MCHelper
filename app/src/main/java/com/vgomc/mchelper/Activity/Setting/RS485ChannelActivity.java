package com.vgomc.mchelper.Activity.Setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.Entity.RS485Channel;
import com.vgomc.mchelper.Entity.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.ChannelVariableAdapter;
import com.vgomc.mchelper.base.BaseActivity;
import com.vgomc.mchelper.widget.NoScrollListView;
import com.vgomc.mchelper.widget.VariableEditView;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.Spinner;

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
    private EditText mWarmTimeEditText;
    private Button mAddVariableButton;
    private NoScrollListView mVariableListView;
    private ChannelVariableAdapter mVariableAdapter;
    private LinearLayout mVariableLayout;

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
        mRS485Channel.warmTime = Integer.parseInt(mWarmTimeEditText.getText().toString());
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
        mWarmTimeEditText = (EditText) findViewById(R.id.et_activity_setting_channel_rs485_warm_time);
        mAddVariableButton = (Button) findViewById(R.id.btn_activity_setting_channel_rs485_add);
        mVariableListView = (NoScrollListView) findViewById(R.id.nslv_activity_setting_channel_rs485_variable);
        mVariableAdapter = new ChannelVariableAdapter(mContext);
        mVariableListView.setAdapter(mVariableAdapter);
        mBaudRateAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setting_channel_rs485_baud));
        mBaudRateSpinner.setAdapter(mBaudRateAdapter);
        mVariableLayout = (LinearLayout) findViewById(R.id.ll_activity_setting_channel_rs485_variables);
    }

    private void initListener() {
        mModeReadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_activity_setting_channel_rs485_mode_slave) {
                    new AlertDialog.Builder(mContext).setTitle("确认转为从机模式？").setMessage("已有变量将会被清除").setPositiveButton("确认", new AlertDialog.OnClickListener(

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
                    ;
                } else {
                    setMasterMode();
                }
            }
        });
        mAddVariableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVariableDialog(-1, new Variable());
            }
        });

        mVariableListView.setOnItemClickListener(new NoScrollListView.OnNoScrollItemClickListener() {
            @Override
            public void onItemClick(View v, Object item, int position, long id) {
                showVariableDialog(position, (Variable) mVariableAdapter.getItem(position));
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
        mWarmTimeEditText.setText(mRS485Channel.warmTime + "");
        mVariableAdapter.setVariabletList(mRS485Channel.variables);
    }

    private void showVariableDialog(final int position, Variable variable) {
        final VariableEditView editView = new VariableEditView(mContext);
        editView.initData(Channel.TYPE_RS485, Channel.SUBJECT_RS485 + "通道", variable);
        new AlertDialog.Builder(mContext).setView(editView).setPositiveButton(R.string.dialog_confirm, new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Variable variable = editView.getData();
                if (position >= 0) {
                    mRS485Channel.variables.set(position, variable);
                } else {
                    mRS485Channel.variables.add(variable);
                }
                mVariableAdapter.setVariabletList(mRS485Channel.variables);
            }
        }).create().show();
    }

    private void setSlaveMode() {
        mVariableLayout.setVisibility(View.GONE);
        mRS485Channel.variables.clear();
    }

    private void setMasterMode() {
        mVariableLayout.setVisibility(View.VISIBLE);
    }
}
