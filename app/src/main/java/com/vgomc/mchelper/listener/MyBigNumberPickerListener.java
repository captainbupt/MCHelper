package com.vgomc.mchelper.listener;

import com.vgomc.mchelper.widget.MyBigNumberPicker;

import android.widget.NumberPicker;

/**
 * Created by weizhouh on 5/28/2015.
 */
public class MyBigNumberPickerListener implements NumberPicker.OnValueChangeListener {

    private int mPosition;
    private int[] mMaxDigits;
    private int[] mMinDigits;
    private int mDigitNumber;
    private NumberPicker[] mNumberPickers;
    private int[] mRangeMaxs;
    private int[] mRangeMins;
    private MyBigNumberPicker.OnBigNumberValueChangeListener mOnBigNumberValueChangeListener;

    public MyBigNumberPickerListener(int mPosition, int[] mMaxDigits, int[] mMinDigits, int mDigitNumber, NumberPicker[] mNumberPickers) {
        this.mPosition = mPosition;
        this.mMaxDigits = mMaxDigits;
        this.mMinDigits = mMinDigits;
        this.mDigitNumber = mDigitNumber;
        this.mNumberPickers = mNumberPickers;
        mRangeMaxs = new int[mDigitNumber];
        mRangeMins = new int[mDigitNumber];
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mRangeMins[ii] = 0;
            mRangeMaxs[ii] = 9;
        }
    }

    public MyBigNumberPickerListener(int mPosition, int[] mMaxDigits, int[] mMinDigits, int mDigitNumber, NumberPicker[] mNumberPickers, MyBigNumberPicker.OnBigNumberValueChangeListener mOnBigNumberValueChangeListener) {
        this.mPosition = mPosition;
        this.mMaxDigits = mMaxDigits;
        this.mMinDigits = mMinDigits;
        this.mDigitNumber = mDigitNumber;
        this.mNumberPickers = mNumberPickers;
        mRangeMaxs = new int[mDigitNumber];
        mRangeMins = new int[mDigitNumber];
        for (int ii = 0; ii < mDigitNumber; ii++) {
            mRangeMins[ii] = 0;
            mRangeMaxs[ii] = 9;
        }
        this.mOnBigNumberValueChangeListener = mOnBigNumberValueChangeListener;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if (newVal == mMaxDigits[mPosition] && isAfterMax(mPosition)) {
            for (int ii = mPosition + 1; ii < mDigitNumber; ii++) {
                mNumberPickers[ii].setMaxValue(mMaxDigits[ii]);
            }
        } else {
            for (int ii = mPosition + 1; ii < mDigitNumber; ii++) {
                mNumberPickers[ii].setMaxValue(mRangeMaxs[ii]);
            }
        }
        if (newVal == mMinDigits[mPosition] && isBeforeMin(mPosition)) {
            for (int ii = mPosition + 1; ii < mDigitNumber; ii++) {
                mNumberPickers[ii].setMinValue(mMinDigits[ii]);
            }
        } else {
            for (int ii = mPosition + 1; ii < mDigitNumber; ii++) {
                mNumberPickers[ii].setMinValue(mRangeMins[ii]);
            }
        }
        if (mOnBigNumberValueChangeListener != null) {
            int value = 0;
            for (int ii = 0; ii < mDigitNumber; ii++) {
                value *= 10;
                value += mNumberPickers[ii].getValue();
            }
            mOnBigNumberValueChangeListener.onValueChange(value);
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

