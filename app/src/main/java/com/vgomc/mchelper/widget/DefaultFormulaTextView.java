package com.vgomc.mchelper.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;

import com.vgomc.mchelper.R;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class DefaultFormulaTextView extends TextView {

    public DefaultFormulaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(float[] factors) {
        StringBuilder builder = new StringBuilder("y = ");
        for (int ii = 0; ii < factors.length; ii++) {
            if (factors[ii] == 0)
                continue;
            builder.append(factors[ii] > 0 ? " + " : " - ");
            builder.append(String.format("%.4f", Math.abs(factors[ii])));
            if (ii == 1) {
                builder.append(" * x");
            } else if (ii != 0) {
                builder.append(" * x" + ii);
            }
        }
        if (builder.charAt(5) == '+') {
            builder.deleteCharAt(5);
            builder.deleteCharAt(5);
        }
        String text = builder.toString();
        SpannableString spannableString = new SpannableString(text);
        int x2Position = text.indexOf("x2");
        int x3Position = text.indexOf("x3");
        int exponentialSize = getResources().getDimensionPixelSize(R.dimen.text_size_small);
        if (x2Position >= 0) {
            spannableString.setSpan(new SuperscriptSpan(), x2Position + 1, x2Position + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new AbsoluteSizeSpan(exponentialSize), x2Position + 1, x2Position + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (x3Position >= 0) {
            spannableString.setSpan(new SuperscriptSpan(), x3Position + 1, x3Position + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new AbsoluteSizeSpan(exponentialSize), x3Position + 1, x3Position + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
    }

}
