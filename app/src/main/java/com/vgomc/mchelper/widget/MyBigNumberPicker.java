package com.vgomc.mchelper.widget;

import android.content.Context;

import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.NumberPicker;

/**
 * Created by weizhouh on 5/27/2015.
 */
public class MyBigNumberPicker extends LinearLayout {

    private int mMax;
    private int mMin;
    private int mDigitNumber;
    private int mTextSize;
    private Context mContext;
    private NumberPicker[] mNumberPickers;
    private int[] mMaxDigits;
    private int[] mMinDigits;
    private int[] mRangeMaxs;
    private int[] mRangeMins;

    public MyBigNumberPicker(Context context, int mMax, int mMin, int mDigitNumber) {
        super(context);
        this.mMax = mMax;
        this.mMin = mMin;
        this.mDigitNumber = mDigitNumber;
        this.mContext = context;
        initView();
        initListener();
    }
/*
    public MyBigNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.MyBigNumberPicker);
        this.mTextSize = a.getDimensionPixelSize(R.styleable.MyBigNumberPicker_textSize, context.getResources().getDimensionPixelSize(R.dimen.text_size_medium));
        this.mDigitNumber = a.getInteger(R.styleable.MyBigNumberPicker_digitNum, 0);
        this.mMax = a.getInteger(R.styleable.MyBigNumberPicker_maxValue, 0);
        this.mMin = a.getInteger(R.styleable.MyBigNumberPicker_minValue, 0);
        if (this.mDigitNumber <= 0)
            return;
        initView();
        initListener();
    }*/

    private void initView() {

        mNumberPickers = new NumberPicker[mDigitNumber];
        mMaxDigits = int2array(mMax, mDigitNumber);
        mMinDigits = int2array(mMin, mDigitNumber);
        mRangeMaxs = new int[mDigitNumber];
        mRangeMins = new int[mDigitNumber];
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mNumberPickers[ii] = new NumberPicker(mContext);
            mNumberPickers[ii].setLayoutParams(lp);
            addView(mNumberPickers[ii]);
            mNumberPickers[ii].setMaxValue(mMaxDigits[ii]);
            mNumberPickers[ii].setMinValue(mMinDigits[ii]);
            mRangeMaxs[ii] = 9;
            mRangeMins[ii] = 0;
        }
    }

    private void initListener() {
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mNumberPickers[ii].setOnValueChangedListener(new MyValueChangedListener(ii));
        }
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mNumberPickers[ii].setValue(mMaxDigits[ii]);
        }
    }

    private int[] int2array(int number, int size) {
        int[] result = new int[size];
        for (int ii = size - 1; ii >= 0; ii--) {
            result[ii] = number % 10;
            number /= 10;
        }
        return result;
    }

    class MyValueChangedListener implements NumberPicker.OnValueChangeListener {

        int position;

        public MyValueChangedListener(int position) {
            this.position = position;
        }

        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (newVal == mMaxDigits[position] && isAfterMax(position)) {
                for (int ii = position + 1; ii < mDigitNumber; ii++) {
                    mNumberPickers[ii].setMaxValue(mMaxDigits[ii]);
                }
            } else {
                for (int ii = position + 1; ii < mDigitNumber; ii++) {
                    mNumberPickers[ii].setMaxValue(mRangeMaxs[ii]);
                }
            }
            if (newVal == mMinDigits[position] && isBeforeMin(position)) {
                for (int ii = position + 1; ii < mDigitNumber; ii++) {
                    mNumberPickers[ii].setMinValue(mMinDigits[ii]);
                }
            } else {
                for (int ii = position + 1; ii < mDigitNumber; ii++) {
                    mNumberPickers[ii].setMinValue(mRangeMins[ii]);
                }
            }
        }

        private boolean isBeforeMin(int position) {
            boolean result = true;
            for (int ii = 0; ii < position; ii++) {
                if (mNumberPickers[ii].getValue() != mMinDigits[ii])
                    result = false;
            }
            return result;
        }

        private boolean isAfterMax(int position) {
            boolean result = true;
            for (int ii = 0; ii < position; ii++) {
                if (mNumberPickers[ii].getValue() != mMaxDigits[ii])
                    result = false;
            }
            return result;
        }
    }

}
