package com.vgomc.mchelper.widget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

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
