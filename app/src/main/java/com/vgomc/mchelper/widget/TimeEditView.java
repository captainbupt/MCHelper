package com.vgomc.mchelper.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.TimeUtil;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.NumberPicker;
import org.holoeverywhere.widget.datetimepicker.DateTimePickerUtils;
import org.holoeverywhere.widget.datetimepicker.time.RadialPickerLayout;
import org.holoeverywhere.widget.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by weizhouh on 5/27/2015.
 */
public class TimeEditView extends LinearLayout {

    private Context mContext;
    private LinearLayout mHourLayout;
    private LinearLayout mMinuteLayout;
    private LinearLayout mSecondLayout;
    private LinearLayout mMillisecondLayout;
    private EditText mHourEditText;
    private EditText mMinuteEditText;
    private EditText mSecondEditText;
    private EditText mMillisecondEditText;

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

    public void setFormat(boolean hasHour, boolean hasMinute, boolean hasSecond, boolean hasMillisecond) {
        mHourLayout.setVisibility(hasHour ? View.VISIBLE : View.GONE);
        mMinuteLayout.setVisibility(hasMinute ? View.VISIBLE : View.GONE);
        mSecondLayout.setVisibility(hasSecond ? View.VISIBLE : View.GONE);
        mMillisecondLayout.setVisibility(hasMillisecond ? View.VISIBLE : View.GONE);
    }

    public void setTime(long time) {
        Calendar calendar = TimeUtil.long2calendar(time);
        mHourEditText.setText(calendar.get(Calendar.HOUR) + "");
        mMinuteEditText.setText(calendar.get(Calendar.MINUTE) + "");
        mSecondEditText.setText(calendar.get(Calendar.SECOND) + "");
        mMillisecondEditText.setText(calendar.get(Calendar.MILLISECOND) + "");
    }

    public long getTime() {
        String hourStr = mHourEditText.getText().toString();
        String minuteStr = mMinuteEditText.getText().toString();
        String secondStr = mSecondEditText.getText().toString();
        String millisecondStr = mMillisecondEditText.getText().toString();
        return TimeUtil.time2long(TextUtils.isEmpty(hourStr) ? 0 : Integer.parseInt(hourStr),
                TextUtils.isEmpty(minuteStr) ? 0 : Integer.parseInt(minuteStr),
                TextUtils.isEmpty(secondStr) ? 0 : Integer.parseInt(secondStr),
                TextUtils.isEmpty(millisecondStr) ? 0 : Integer.parseInt(millisecondStr));
    }

}
