package com.vgomc.mchelper.base;

import android.content.Context;
import org.holoeverywhere.widget.LinearLayout;

/**
 * Created by weizhouh on 5/20/2015.
 */
public abstract class BaseCollapsibleContentView extends LinearLayout {

    public Context mContext;

    public BaseCollapsibleContentView(Context context) {
        super(context);
        this.mContext = context;
        setLayoutResource();
    }

    protected abstract void setLayoutResource();
}
