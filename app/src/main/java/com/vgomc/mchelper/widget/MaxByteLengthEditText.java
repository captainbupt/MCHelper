package com.vgomc.mchelper.widget;

import android.content.Context;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.AttributeSet;

import org.holoeverywhere.widget.EditText;

import java.io.UnsupportedEncodingException;

/**
 * Created by weizhouh on 6/24/2015.
 */
public class MaxByteLengthEditText extends EditText {
    private int maxByteLength = 100;
    private String encoding = "GBK";

    public MaxByteLengthEditText(Context context) {
        super(context);
        init();
    }

    public MaxByteLengthEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setFilters(new InputFilter[]{inputFilter});
    }

    public int getMaxByteLength() {
        return maxByteLength;
    }

    public void setMaxByteLength(int maxByteLength) {
        this.maxByteLength = maxByteLength;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * inputÊäÈë¹ýÂË
     */
    private InputFilter inputFilter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int len = 0;
                boolean more = false;
                do {
                    SpannableStringBuilder builder = new SpannableStringBuilder(dest).replace(dstart, dend, source.subSequence(start, end));
                    len = builder.toString().getBytes(encoding).length;
                    more = len > maxByteLength;
                    if (more) {
                        end--;
                        source = source.subSequence(start, end);
                    }
                } while (more);
                return source;
            } catch (UnsupportedEncodingException e) {
                return "Exception";
            }
        }
    };
}