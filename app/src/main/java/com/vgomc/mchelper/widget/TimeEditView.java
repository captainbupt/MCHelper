package com.vgomc.mchelper.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.TimeUtil;

public class TimeEditView extends LinearLayout {

    private int mMode;
    private Context mContext;
    private LinearLayout mHourLayout;
    private LinearLayout mMinuteLayout;
    private LinearLayout mSecondLayout;
    private LinearLayout mMillisecondLayout;
    private EditText mHourEditText;
    private EditText mMinuteEditText;
    private EditText mSecondEditText;
    private EditText mMillisecondEditText;

    private OnTouchListener mOnTouchListener;

    public TimeEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_time, this);
        initView();
    }

    private void initView() {
        mHourLayout = (LinearLayout) findViewById(R.id.ll_view_setting_time_hour);
        mMinuteLayout = (LinearLayout) findViewById(R.id.ll_view_setting_time_minute);
        mSecondLayout = (LinearLayout) findViewById(R.id.ll_view_setting_time_second);
        mMillisecondLayout = (LinearLayout) findViewById(R.id.ll_view_setting_time_millisecond);
        mHourEditText = (EditText) findViewById(R.id.et_view_setting_time_hour);
        mMinuteEditText = (EditText) findViewById(R.id.et_view_setting_time_minute);
        mSecondEditText = (EditText) findViewById(R.id.et_view_setting_time_second);
        mMillisecondEditText = (EditText) findViewById(R.id.et_view_setting_time_millisecond);
    }

    public void setMode(int mode) {
        this.mMode = mode;
        if (mode == MyTimePickerView.MODE_MSM) {
            mHourLayout.setVisibility(View.GONE);
            mMinuteLayout.setVisibility(View.VISIBLE);
            mSecondLayout.setVisibility(View.VISIBLE);
            mMillisecondLayout.setVisibility(View.VISIBLE);
        } else if (mode == MyTimePickerView.MODE_HM) {
            mHourLayout.setVisibility(View.VISIBLE);
            mMinuteLayout.setVisibility(View.VISIBLE);
            mSecondLayout.setVisibility(View.GONE);
            mMillisecondLayout.setVisibility(View.GONE);
        } else {
            mHourLayout.setVisibility(View.VISIBLE);
            mMinuteLayout.setVisibility(View.VISIBLE);
            mSecondLayout.setVisibility(View.VISIBLE);
            mMillisecondLayout.setVisibility(View.GONE);
        }
    }

    public void setTime(long time) {
        int[] timeArray = TimeUtil.long2timeArray(time);
        mHourEditText.setText(timeArray[0] + "");
        mMinuteEditText.setText(timeArray[1] + "");
        mSecondEditText.setText(timeArray[2] + "");
        mMillisecondEditText.setText(timeArray[3] + "");
    }

    public long getTime() {
        String hourStr = mHourEditText.getText().toString();
        String minuteStr = mMinuteEditText.getText().toString();
        String secondStr = mSecondEditText.getText().toString();
        String millisecondStr = mMillisecondEditText.getText().toString();
        if (mMode == MyTimePickerView.MODE_MSM) {
            hourStr = "0";
        } else if (mMode == MyTimePickerView.MODE_HM) {
            secondStr = "0";
            millisecondStr = "0";
        } else {
            millisecondStr = "0";
        }
        return TimeUtil.time2long(TextUtils.isEmpty(hourStr) ? 0 : Integer.parseInt(hourStr),
                TextUtils.isEmpty(minuteStr) ? 0 : Integer.parseInt(minuteStr),
                TextUtils.isEmpty(secondStr) ? 0 : Integer.parseInt(secondStr),
                TextUtils.isEmpty(millisecondStr) ? 0 : Integer.parseInt(millisecondStr));
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mOnTouchListener != null) {
            return mOnTouchListener.onTouch(this, ev);
        }
        return super.onInterceptTouchEvent(ev);
    }
}
