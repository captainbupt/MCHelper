package com.vgomc.mchelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.Entity.Measuring;
import com.vgomc.mchelper.Entity.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.dialog.BigNumberPickerDialog;
import com.vgomc.mchelper.utility.TimeUtil;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/30/2015.
 */
public class MeasuringEditView extends LinearLayout {

    private Context mContext;
    private int mPosition;
    private Measuring mMeasuring;
    private TextView mSubjectTextView;
    private Switch mModeSwitch;
    private EditText mBeginEditText;
    private EditText mEndEditText;
    private EditText mIntervalEditText;
    private TextView mVariableTextView;
    private LinearLayout mContentLayout;
    private List<String> mVariableItems;
    private List<Boolean> mVariableSelections;

    public MeasuringEditView(Context context, int position) {
        super(context);
        this.mContext = context;
        this.mPosition = position;
        this.mMeasuring = (Measuring) Configuration.getInstance().measuringList.get(position);
        setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_measuring_edit, this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mSubjectTextView = (TextView) findViewById(R.id.tv_view_setting_measuring_subject);
        mModeSwitch = (Switch) findViewById(R.id.sw_view_setting_measuring_mode);
        mBeginEditText = (EditText) findViewById(R.id.et_view_setting_measuring_begin);
        mEndEditText = (EditText) findViewById(R.id.et_view_setting_measuring_end);
        mIntervalEditText = (EditText) findViewById(R.id.et_view_setting_measuring_interval);
        mVariableTextView = (TextView) findViewById(R.id.tv_view_setting_measuring_variable);
        mContentLayout = (LinearLayout) findViewById(R.id.ll_view_setting_measuring_content);
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
                    BigNumberPickerDialog.getBigNumberPickerDialog(mContext, 4, 0, 1440, Integer.parseInt(mIntervalEditText.getText().toString()), mIntervalEditText, getResources().getString(R.string.setting_measuring_interval)).show();
                }
                return true;
            }
        });
        mBeginEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final MyTimePickerView timePickerView = new MyTimePickerView(mContext, MyTimePickerView.MODE_HM, mMeasuring.beginTime, mMeasuring.endTime, 0l);
                    new AlertDialog.Builder(mContext).setTitle(R.string.setting_time_picker_tip_begin).setView(timePickerView)
                            .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long time = timePickerView.getTime();
                                    mMeasuring.beginTime = time;
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
                    final MyTimePickerView timePickerView = new MyTimePickerView(mContext, MyTimePickerView.MODE_HM, mMeasuring.endTime, TimeUtil.time2long(24, 0, 0, 0), mMeasuring.beginTime);
                    new AlertDialog.Builder(mContext).setTitle(R.string.setting_time_picker_tip_end).setView(timePickerView)
                            .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    long time = timePickerView.getTime();
                                    mMeasuring.endTime = time;
                                    mEndEditText.setText(getTimeText(time));
                                }
                            }).create().show();
                }
                return true;
            }
        });
        mVariableTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<Boolean> selectionList = new ArrayList<>(mVariableSelections);
                boolean[] mSelectionArray = new boolean[selectionList.size()];
                for (int ii = 0; ii < mSelectionArray.length; ii++) {
                    mSelectionArray[ii] = selectionList.get(ii);
                }
                new AlertDialog.Builder(mContext).setTitle(R.string.setting_measuring_variables)
                        .setMultiChoiceItems(mVariableItems.toArray(new String[mVariableItems.size()]), mSelectionArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                selectionList.set(which, isChecked);
                            }
                        })
                        .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mVariableSelections = new ArrayList<>(selectionList);
                                updateSelection();
                            }
                        }).create().show();
            }
        });
    }

    private void initData() {
        mSubjectTextView.append((mPosition + 1) + "");
        mModeSwitch.setChecked(mMeasuring.isOn);
        mContentLayout.setVisibility(mMeasuring.isOn ? View.VISIBLE : View.GONE);
        mBeginEditText.setText(getTimeText(mMeasuring.beginTime));
        mEndEditText.setText(getTimeText(mMeasuring.endTime));
        mIntervalEditText.setText(mMeasuring.interval + "");
        mVariableTextView.setText(mMeasuring.getVariableNames("\n"));
        mVariableItems = new ArrayList<>();
        mVariableSelections = new ArrayList<>();
        for (Channel channel : Configuration.getInstance().channelMap.values()) {
            for (int ii = 0; ii < channel.variables.size(); ii++) {
                if (channel.variables.get(ii).isVariableOn) {
                    mVariableItems.add(channel.variables.get(ii).name + "-" + channel.subject);
                    boolean isSelected = false;
                    for (int jj = 0; jj < mMeasuring.channelList.size() && !isSelected; jj++) {
                        if (channel.subject.equals(mMeasuring.channelList.get(jj).subject) && mMeasuring.variablePositionList.get(jj) == ii) {
                            isSelected = true;
                        }
                    }
                    mVariableSelections.add(isSelected);
                }
            }
        }
    }

    private void updateSelection() {
        int ii = 0;
        mMeasuring.channelList.clear();
        mMeasuring.variablePositionList.clear();
        for (Channel channel : Configuration.getInstance().channelMap.values()) {
            for (int jj = 0; jj < channel.variables.size(); jj++, ii++) {
                if (channel.variables.get(ii).isVariableOn) {
                    if (mVariableSelections.get(ii)) {
                        mMeasuring.channelList.add(channel);
                        mMeasuring.variablePositionList.add(jj);
                    }
                }
            }
        }
        mVariableTextView.setText(mMeasuring.getVariableNames("\n"));
    }

    private String getTimeText(long time) {
        int[] timeArray = TimeUtil.long2timeArray(time);
        return String.format("%2d:%2d", timeArray[0], timeArray[1]);
    }

    public Measuring getMeasuring() {
        mMeasuring.isOn = mModeSwitch.isChecked();
        mMeasuring.interval = Integer.parseInt(mIntervalEditText.getText().toString());
        return mMeasuring;
    }

}
