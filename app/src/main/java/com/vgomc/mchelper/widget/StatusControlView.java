package com.vgomc.mchelper.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.vgomc.mchelper.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 6/14/2015.
 */
public class StatusControlView extends LinearLayout {

    private Context mContext;
    private TextView mSubjectTextView;
    private Button mLeftButton;
    private Button mRightButton;
    private TextView mStatusTextView;


    public StatusControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.adapter_status_control, this);
        mSubjectTextView = (TextView) findViewById(R.id.tv_adapter_status_control_subject);
        mLeftButton = (Button) findViewById(R.id.btn_adapter_status_control_left);
        mRightButton = (Button) findViewById(R.id.btn_adapter_status_control_right);
        mStatusTextView = (TextView) findViewById(R.id.tv_adapter_status_control_status);
    }

    public void initData(int subjectResId, int leftButtonResId, int rightButtonResId) {
        mSubjectTextView.setText(subjectResId);
        mLeftButton.setText(leftButtonResId);
        mRightButton.setText(rightButtonResId);
    }

    public void initData(String subject, int leftButtonResId, int rightButtonResId) {
        mSubjectTextView.setText(subject);
        mLeftButton.setText(leftButtonResId);
        mRightButton.setText(rightButtonResId);
    }

    public void setListener(OnClickListener leftListener, OnClickListener rightListener) {
        mLeftButton.setOnClickListener(leftListener);
        mRightButton.setOnClickListener(rightListener);
    }

    public void setStatus(String status) {
        mStatusTextView.setText(status);
    }

}
