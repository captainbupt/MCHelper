package com.vgomc.mchelper.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.listener.MyBigNumberValueChangeListener;
import com.vgomc.mchelper.utility.TimeUtil;

import android.widget.LinearLayout;

public class MyTimePickerView extends LinearLayout {

    public static final int MODE_MSM = 1;
    public static final int MODE_HM = 2;
    public static final int MODE_HMS = 3;

    private int mMode;
    private LinearLayout mHourLinearLayout;
    private LinearLayout mMinuteLinearLayout;
    private LinearLayout mSecondLinearLayout;
    private LinearLayout mMillisecondLinearLayout;
    private MyBigNumberPicker[] mNumberPickers;
    private int[] mMaxDigits;
    private int[] mMinDigits;
    private int[] mRangeMaxs;
    private int[] mRangeMins;

    public MyTimePickerView(Context context, int mode, final long current, final long max, long min) {
        super(context);
        setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dialog_time_picker, this);
        mRangeMaxs = new int[]{24, 59, 59, 999};
        mRangeMins = new int[]{0, 0, 0, 0};
        mMaxDigits = TimeUtil.long2timeArray(max);
        mMinDigits = TimeUtil.long2timeArray(min);
        mMode = mode;
        initView(mode);
        initListener();
        initData();
        //setTime(max);
        setTime(current);
    }

    private void initView(int mode) {
        mHourLinearLayout = (LinearLayout) findViewById(R.id.ll_dialog_time_picker_layout_hour);
        mMinuteLinearLayout = (LinearLayout) findViewById(R.id.ll_dialog_time_picker_layout_minute);
        mSecondLinearLayout = (LinearLayout) findViewById(R.id.ll_dialog_time_picker_layout_second);
        mMillisecondLinearLayout = (LinearLayout) findViewById(R.id.ll_dialog_time_picker_layout_millisecond);
        mNumberPickers = new MyBigNumberPicker[4];
        mNumberPickers[0] = (MyBigNumberPicker) findViewById(R.id.mbnp_dialog_time_picker_hour);
        mNumberPickers[1] = (MyBigNumberPicker) findViewById(R.id.mbnp_dialog_time_picker_minute);
        mNumberPickers[2] = (MyBigNumberPicker) findViewById(R.id.mbnp_dialog_time_picker_second);
        mNumberPickers[3] = (MyBigNumberPicker) findViewById(R.id.mbnp_dialog_time_picker_millisecond);
        if (mode == MODE_HM) {
            mSecondLinearLayout.setVisibility(View.GONE);
            mMillisecondLinearLayout.setVisibility(View.GONE);
        } else if (mode == MODE_MSM) {
            mHourLinearLayout.setVisibility(View.GONE);
        } else if (mode == MODE_HMS) {
            mMillisecondLinearLayout.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        for (int ii = 0; ii < mNumberPickers.length - 1; ii++) {
            mNumberPickers[ii].setOnBigNumberValueChangeListener(new MyBigNumberValueChangeListener(ii, mMaxDigits, mMinDigits, mRangeMaxs, mRangeMins, 4, mNumberPickers));
        }
        mNumberPickers[3].setOnBigNumberValueChangeListener(new MyBigNumberValueChangeListener(3, mMaxDigits, mMinDigits, mRangeMaxs, mRangeMins, 4, mNumberPickers));
    }

    private void initData() {
        for (int ii = 0; ii < mNumberPickers.length - 1; ii++) {
            mNumberPickers[ii].setData(mMaxDigits[ii], mMinDigits[ii], 2);
            //mNumberPickers[ii].getOnBigNumberValueChangeListener().onValueChange(timeArray[ii]);
        }
        mNumberPickers[3].setData(mMaxDigits[3], mMinDigits[3], 3);
        //mNumberPickers[3].getOnBigNumberValueChangeListener().onValueChange(timeArray[3]);
    }

    public void setTime(long current) {
        int[] timeArray = TimeUtil.long2timeArray(current);
        for (int ii = 0; ii < timeArray.length; ii++) {
            mNumberPickers[ii].setValue(timeArray[ii]);
            mNumberPickers[ii].getOnBigNumberValueChangeListener().onValueChange(timeArray[ii]);
        }
    }

    public long getTime() {
        if (mMode == MODE_MSM) {
            return TimeUtil.time2long(0, mNumberPickers[1].getValue(), mNumberPickers[2].getValue(), mNumberPickers[3].getValue());
        } else if (mMode == MODE_HM) {
            return TimeUtil.time2long(mNumberPickers[0].getValue(), mNumberPickers[1].getValue(), 0, 0);
        } else {
            return TimeUtil.time2long(mNumberPickers[0].getValue(), mNumberPickers[1].getValue(), mNumberPickers[2].getValue(), 0);
        }
    }
}
