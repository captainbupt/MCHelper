package com.vgomc.mchelper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.Entity.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/24/2015.
 */
public class VariableEditView extends LinearLayout {

    private Context mContext;
    private TextView mSubjectTextView;
    private Switch mVariableSwitch;
    private LinearLayout mContentLayout;
    private EditText mVariableNameEditText;
    private LinearLayout mSensorAddressLayout;
    private EditText mSensorAddressEditText;
    private LinearLayout mRegisterTypeLayout;
    private Spinner mRegisterTypeSpinner;
    private LinearLayout mRegisterAddressLayout;
    private EditText mRegisterAddressEditText;
    private LinearLayout mDataTypeLayout;
    private Spinner mDataTypeSpinner;
    private LinearLayout mSignalTypeLayout;
    private RadioGroup mSignalTypeRadioGroup;
    private RadioButton mSignalVoltageRadioButton;
    private RadioButton mSignalCurrentRadioButton;
    private DefaultFormulaEditView mFormulaEditView;
    private ArrayAdapter<String> registerTypeAdapter;
    private ArrayAdapter<String> dataTypeAdapter1;
    private ArrayAdapter<String> dataTypeAdapter2;

    private int currentRegisterType;

    public VariableEditView(Context context) {
        super(context);
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_channel_variable, this);
        initView();
        initListener();
    }

    public VariableEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_channel_variable, this);
        initView();
        initListener();
    }

    private void initView() {
        mSubjectTextView = (TextView) findViewById(R.id.tv_view_setting_channel_variable_subject);
        mVariableSwitch = (Switch) findViewById(R.id.sw_view_setting_channel_variable);
        mContentLayout = (LinearLayout) findViewById(R.id.ll_view_setting_channel_content);
        mVariableNameEditText = (EditText) findViewById(R.id.et_view_setting_channel_variable_name);
        mSensorAddressLayout = (LinearLayout) findViewById(R.id.ll_view_setting_channel_variable_sensor_address);
        mSensorAddressEditText = (EditText) findViewById(R.id.et_view_setting_channel_variable_sensor_address);
        mRegisterTypeLayout = (LinearLayout) findViewById(R.id.ll_view_setting_channel_variable_register_type);
        mRegisterTypeSpinner = (Spinner) findViewById(R.id.sp_view_setting_channel_variable_register_type);
        mRegisterAddressLayout = (LinearLayout) findViewById(R.id.ll_view_setting_channel_variable_register_address);
        mRegisterAddressEditText = (EditText) findViewById(R.id.et_view_setting_channel_variable_register_address);
        mDataTypeLayout = (LinearLayout) findViewById(R.id.ll_view_setting_channel_variable_data_type);
        mDataTypeSpinner = (Spinner) findViewById(R.id.sp_view_setting_channel_variable_data_type);
        mSignalTypeLayout = (LinearLayout) findViewById(R.id.ll_view_setting_channel_variable_signal_type);
        mSignalTypeRadioGroup = (RadioGroup) findViewById(R.id.rg_view_setting_channel_signal_type);
        mSignalVoltageRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_channel_signal_type_voltage);
        mSignalCurrentRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_channel_signal_type_current);
        mFormulaEditView = (DefaultFormulaEditView) findViewById(R.id.dfev_view_setting_channel);

        registerTypeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setting_channel_variable_register_type_array));
        mRegisterTypeSpinner.setAdapter(registerTypeAdapter);

        dataTypeAdapter1 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setting_channel_variable_data_type_array_1));
        dataTypeAdapter2 = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.setting_channel_variable_data_type_array_2));
    }

    private void initListener() {

        mRegisterTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int type = position / 2;
                if (currentRegisterType != type) {
                    if (type == 0) {
                        mDataTypeSpinner.setAdapter(dataTypeAdapter1);
                    } else if (type == 1) {
                        mDataTypeSpinner.setAdapter(dataTypeAdapter2);
                    }
                    mDataTypeSpinner.setSelection(0);
                    currentRegisterType = type;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mRegisterTypeSpinner.setSelection(0);
            }
        });
    }

    public void initData(int type, String subject, Variable variable) {
        showView(type);
        mSubjectTextView.setText(subject);
        mVariableSwitch.setOnCheckedChangeListener(null);
        mVariableSwitch.setChecked(variable.isVariableOn);
        mVariableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (Configuration.getInstance().getChannelVariableCount() < Configuration.getInstance().channelVariableMaxCount) {
                        mContentLayout.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtil.showToast(mContext, R.string.tip_variable_reach_max_count);
                        mVariableSwitch.setChecked(false);
                    }
                } else {
                    mContentLayout.setVisibility(View.GONE);
                }
            }
        });
        if (variable.isVariableOn) {
            mContentLayout.setVisibility(View.VISIBLE);
        } else {
            mContentLayout.setVisibility(View.GONE);
        }
        mVariableNameEditText.setText(variable.name);
        mSensorAddressEditText.setText(variable.sensorAddress + "");
        mRegisterTypeSpinner.setSelection(variable.registerType);
        mRegisterAddressEditText.setText(variable.registerAddress + "");
        if (variable.registerType / 2 == 0) {
            mDataTypeSpinner.setAdapter(dataTypeAdapter1);
            mDataTypeSpinner.setSelection(variable.dataType);
        } else if (variable.registerType / 2 == 1) {
            mDataTypeSpinner.setAdapter(dataTypeAdapter2);
            mDataTypeSpinner.setSelection(variable.dataType);
        }
        if (variable.signalType == Variable.TYPE_SIGNAL_VOLTAGE) {
            mSignalVoltageRadioButton.setChecked(true);
        } else if (variable.signalType == Variable.TYPE_SIGNAL_CURRENT) {
            mSignalCurrentRadioButton.setChecked(true);
        }
        mFormulaEditView.setFactors(variable.isFormulaOn, variable.factors);
    }

    public Variable getData() {
        Variable variable = new Variable();
        variable.isVariableOn = mVariableSwitch.isChecked();
        variable.name = mVariableNameEditText.getText().toString();
        variable.sensorAddress = Integer.parseInt(mSensorAddressEditText.getText().toString());
        variable.registerType = mRegisterTypeSpinner.getSelectedItemPosition();
        variable.registerAddress = Integer.parseInt(mRegisterAddressEditText.getText().toString());
        variable.dataType = mDataTypeSpinner.getSelectedItemPosition();
        variable.signalType = mSignalTypeRadioGroup.getCheckedRadioButtonId() == R.id.rb_view_setting_channel_signal_type_current ? Variable.TYPE_SIGNAL_CURRENT : Variable.TYPE_SIGNAL_VOLTAGE;
        variable.isFormulaOn = mFormulaEditView.isOn();
        variable.factors = mFormulaEditView.getFactors();
        return variable;
    }

    public void showView(int type) {
        if (type == Channel.TYPE_P || type == Channel.TYPE_SHT) {
            mSensorAddressLayout.setVisibility(View.GONE);
            mRegisterTypeLayout.setVisibility(View.GONE);
            mRegisterAddressLayout.setVisibility(View.GONE);
            mDataTypeLayout.setVisibility(View.GONE);
            mSignalTypeLayout.setVisibility(View.GONE);
        } else if (type == Channel.TYPE_AN0 || type == Channel.TYPE_AN) {
            mSensorAddressLayout.setVisibility(View.GONE);
            mRegisterTypeLayout.setVisibility(View.GONE);
            mRegisterAddressLayout.setVisibility(View.GONE);
            mDataTypeLayout.setVisibility(View.GONE);
        } else if (type == Channel.TYPE_RS485) {
            mSignalTypeLayout.setVisibility(View.GONE);
        } else if (type == Channel.TYPE_SDI) {
            mSignalTypeLayout.setVisibility(View.GONE);
            mRegisterTypeLayout.setVisibility(View.GONE);
            mDataTypeLayout.setVisibility(View.GONE);
        }
    }
}
