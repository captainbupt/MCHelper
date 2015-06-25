package com.vgomc.mchelper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import com.vgomc.mchelper.listener.MyBigNumberPickerListener;
import com.vgomc.mchelper.utility.ClassUtil;

import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.NumberPicker;

/**
 * Created by weizhouh on 5/27/2015.
 */
public class MyBigNumberPicker extends LinearLayout {

    private int mDigitNumber;
    private int mTextSize;
    private Context mContext;
    private NumberPicker[] mNumberPickers;
    private NumberPicker.OnValueChangeListener[] mListeners;
    private int[] mMaxDigits;
    private int[] mMinDigits;
    private OnBigNumberValueChangeListener mOnBigNumberValueChangeListener;

    public MyBigNumberPicker(Context context, int max, int min, int digitNumber, int current) {
        super(context);
        setGravity(Gravity.CENTER_HORIZONTAL);
        this.mMaxDigits = ClassUtil.int2array(max, digitNumber);
        this.mMinDigits = ClassUtil.int2array(min, digitNumber);
        this.mDigitNumber = digitNumber;
        this.mContext = context;
        initView();
        initListener();
        setValue(current);
    }

    public MyBigNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setGravity(Gravity.CENTER_HORIZONTAL);
    }

    public void setData(int max, int min, int digitNumber, int current) {
        this.mMaxDigits = ClassUtil.int2array(max, digitNumber);
        this.mMinDigits = ClassUtil.int2array(min, digitNumber);
        this.mDigitNumber = digitNumber;
        initView();
        initListener();
        setValue(current);
    }

    public void setData(int max, int min, int digitNumber) {
        this.mMaxDigits = ClassUtil.int2array(max, digitNumber);
        this.mMinDigits = ClassUtil.int2array(min, digitNumber);
        this.mDigitNumber = digitNumber;
        initView();
        initListener();
    }

    private void initView() {
        LayoutParams lp = new LayoutParams(60, LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 5, 0);
        mNumberPickers = new NumberPicker[mDigitNumber];
        mListeners = new NumberPicker.OnValueChangeListener[mDigitNumber];
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mNumberPickers[ii] = new NumberPicker(mContext);
            mNumberPickers[ii].setLayoutParams(lp);
            addView(mNumberPickers[ii]);
            mNumberPickers[ii].setMaxValue(mMaxDigits[ii]);
            mNumberPickers[ii].setMinValue(mMinDigits[ii]);
        }
    }

    private void initListener() {
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mListeners[ii] = new MyBigNumberPickerListener(ii, mMaxDigits, mMinDigits, mDigitNumber, mNumberPickers, mOnBigNumberValueChangeListener);
            mNumberPickers[ii].setOnValueChangedListener(mListeners[ii]);
        }
    }

    public void setOnBigNumberValueChangeListener(OnBigNumberValueChangeListener mOnBigNumberValueChangeListener) {
        this.mOnBigNumberValueChangeListener = mOnBigNumberValueChangeListener;
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mNumberPickers[ii].setOnValueChangedListener(new MyBigNumberPickerListener(ii, mMaxDigits, mMinDigits, mDigitNumber, mNumberPickers, mOnBigNumberValueChangeListener));
        }
    }

    public OnBigNumberValueChangeListener getOnBigNumberValueChangeListener() {
        return mOnBigNumberValueChangeListener;
    }

    public int getValue() {
        int[] values = new int[mDigitNumber];
        for (int ii = 0; ii < mDigitNumber; ii++) {
            values[ii] = mNumberPickers[ii].getValue();
        }
        return ClassUtil.array2int(values);
    }

    public void setValue(int value) {
        int[] values = ClassUtil.int2array(value, mDigitNumber);
        for (int ii = 0; ii < mDigitNumber; ii++) {
            int oldValue = mNumberPickers[ii].getValue();
            mNumberPickers[ii].setValue(values[ii]);
            mListeners[ii].onValueChange(mNumberPickers[ii], oldValue, values[ii]);
        }
    }

    public void setMaxValue(int max) {
        mMaxDigits = ClassUtil.int2array(max, mDigitNumber);
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mNumberPickers[ii].setMaxValue(mMaxDigits[ii]);
        }
        initListener();
    }

    public void setMinValue(int min) {
        this.mMinDigits = ClassUtil.int2array(min, mDigitNumber);
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mNumberPickers[ii].setMinValue(mMinDigits[ii]);
        }
        initListener();
    }

    public interface OnBigNumberValueChangeListener {
        void onValueChange(int newVal);
    }
}
