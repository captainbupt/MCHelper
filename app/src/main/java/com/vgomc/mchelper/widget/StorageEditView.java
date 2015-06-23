package com.vgomc.mchelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;

import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.Storage;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.BigNumberPickerDialog;
import com.vgomc.mchelper.utility.TimeUtil;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/30/2015.
 */
public class StorageEditView extends LinearLayout {

    private Context mContext;
    private int mPosition;
    private Storage mStorage;
    private TextView mSubjectTextView;
    private Switch mModeSwitch;
    private EditText mBeginEditText;
    private EditText mEndEditText;
    private EditText mIntervalEditText;
    private Switch mSendSwitch;
    private LinearLayout mContentLayout;

    public StorageEditView(Context context, int position) {
        super(context);
        this.mContext = context;
        this.mPosition = position;
        this.mStorage = (Storage) Configuration.getInstance().storageList.get(position);
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_storage_edit, this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mSubjectTextView = (TextView) findViewById(R.id.tv_view_setting_storage_subject);
        mModeSwitch = (Switch) findViewById(R.id.sw_view_setting_storage_mode);
        mBeginEditText = (EditText) findViewById(R.id.et_view_setting_storage_begin);
        mEndEditText = (EditText) findViewById(R.id.et_view_setting_storage_end);
        mIntervalEditText = (EditText) findViewById(R.id.et_view_setting_storage_interval);
        mSendSwitch = (Switch) findViewById(R.id.sw_view_setting_storage_send);
        mContentLayout = (LinearLayout) findViewById(R.id.ll_view_setting_storage_content);
    }

    private void initListener() {
        mModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mContentLayout.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        mIntervalEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    BigNumberPickerDialog.getBigNumberPickerDialog(mContext, 4, 0, 1440,Integer.parseInt(mIntervalEditText.getText().toString()), mIntervalEditText, getResources().getString(R.string.setting_storage_interval)).show();
                }
                return true;
            }
        });
        mBeginEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final MyTimePickerView timePickerView = new MyTimePickerView(mContext, MyTimePickerView.MODE_HM, mStorage.beginTime, mStorage.endTime, 0l);
                    new AlertDialog.Builder(mContext).setTitle(R.string.setting_time_picker_tip_begin).setView(timePickerView)
                            .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long time = timePickerView.getTime();
                                    mStorage.beginTime = time;
                                    mBeginEditText.setText(getTimeText(time));
                                }
                            }).create().show();
                }
                return true;
            }
        });
        mEndEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final MyTimePickerView timePickerView = new MyTimePickerView(mContext, MyTimePickerView.MODE_HM, mStorage.endTime, TimeUtil.time2long(24, 0, 0, 0), mStorage.beginTime);
                    new AlertDialog.Builder(mContext).setTitle(R.string.setting_time_picker_tip_end).setView(timePickerView)
                            .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long time = timePickerView.getTime();
                                    mStorage.endTime = time;
                                    mEndEditText.setText(getTimeText(time));
                                }
                            }).create().show();
                }
                return true;
            }
        });
    }

    private void initData() {
        mSubjectTextView.append((mPosition + 1) + "");
        mModeSwitch.setChecked(mStorage.isOn);
        mContentLayout.setVisibility(mStorage.isOn ? View.VISIBLE : View.GONE);
        mBeginEditText.setText(getTimeText(mStorage.beginTime));
        mEndEditText.setText(getTimeText(mStorage.endTime));
        mIntervalEditText.setText(mStorage.interval + "");
        mSendSwitch.setChecked(mStorage.isSend);
    }

    private String getTimeText(long time) {
        int[] timeArray = TimeUtil.long2timeArray(time);
        return String.format("%2d:%2d", timeArray[0], timeArray[1]);
    }

    public Storage getStorage() {
        mStorage.isOn = mModeSwitch.isChecked();
        mStorage.isSend = mSendSwitch.isChecked();
        mStorage.interval = Integer.parseInt(mIntervalEditText.getText().toString());
        return mStorage;
    }

}
