package com.vgomc.mchelper.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;

import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class DefaultFromulaView extends TextView {

    public static final int FACTOR_COUNT = 4;

    public DefaultFromulaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(float[] factors) {
        String text = String.format(" y = %.4f + ( %.4f * x ) + ( %.4f * x2 ) + ( %.4f * x3 )", factors[0], factors[1], factors[2], factors[3]);
        SpannableString spannableString = new SpannableString(text);
        int x2Position = text.indexOf("x2");
        int x3Position = text.indexOf("x3");
        spannableString.setSpan(new SuperscriptSpan(), x2Position+1, x2Position+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new SuperscriptSpan(), x3Position+1, x3Position+2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setText(spannableString);
    }

}
