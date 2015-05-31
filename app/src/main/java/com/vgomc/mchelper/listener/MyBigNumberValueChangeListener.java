package com.vgomc.mchelper.listener;

import com.vgomc.mchelper.widget.MyBigNumberPicker;

import org.holoeverywhere.widget.NumberPicker;

/**
 * Created by weizhouh on 5/28/2015.
 */
public class MyBigNumberValueChangeListener implements MyBigNumberPicker.OnBigNumberValueChangeListener {

    private int mPosition;
    private int[] mMaxDigits;
    private int[] mMinDigits;
    private int mDigitNumber;
    private MyBigNumberPicker[] mNumberPickers;
    private int[] mRangeMaxs;
    private int[] mRangeMins;
    private MyBigNumberPicker.OnBigNumberValueChangeListener mOnBigNumberValueChangeListener;

    public MyBigNumberValueChangeListener(int mPosition, int[] mMaxDigits, int[] mMinDigits, int[] mRangeMaxs, int mRangeMins[], int mDigitNumber, MyBigNumberPicker[] mNumberPickers) {
        this.mPosition = mPosition;
        this.mMaxDigits = mMaxDigits;
        this.mMinDigits = mMinDigits;
        this.mDigitNumber = mDigitNumber;
        this.mNumberPickers = mNumberPickers;
        this.mRangeMaxs = mRangeMaxs;
        this.mRangeMins = mRangeMins;
    }

    @Override
    public void onValueChange(int newVal) {
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
            System.out.println("4---------------------");
            for (int ii = mPosition + 1; ii < mDigitNumber; ii++) {
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

