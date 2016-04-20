package com.vgomc.mchelper.base;

import android.content.Context;

import android.view.LayoutInflater;
import android.widget.LinearLayout;

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
