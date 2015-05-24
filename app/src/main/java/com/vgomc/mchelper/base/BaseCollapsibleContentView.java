package com.vgomc.mchelper.base;

import android.content.Context;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.LinearLayout;

/**
 * Created by weizhouh on 5/20/2015.
 */
public abstract class BaseCollapsibleContentView extends LinearLayout {

    protected Context mContext;

    public BaseCollapsibleContentView(Context context) {
        super(context);
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutResId(), this);
    }

    protected abstract int getLayoutResId();

    protected abstract void updateData();
}
