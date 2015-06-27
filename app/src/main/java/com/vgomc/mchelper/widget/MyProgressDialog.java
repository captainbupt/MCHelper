package com.vgomc.mchelper.widget;

import android.content.Context;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.ProgressDialog;

/**
 * Created by weizhouh on 6/26/2015.
 */
public class MyProgressDialog extends ProgressDialog {
    private Context mContext;

    public MyProgressDialog(Context context) {
        super(context);
        mContext = context;
    }

    public MyProgressDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    public void show() {
        if (!((Activity) mContext).isFinishing())
            super.show();
    }

    @Override
    public void dismiss() {
        if (!((Activity) mContext).isFinishing())
            super.dismiss();
    }
}
