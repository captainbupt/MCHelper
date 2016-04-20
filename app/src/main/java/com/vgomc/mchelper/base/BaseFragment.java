package com.vgomc.mchelper.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.vgomc.mchelper.utility.ToastUtil;

import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = activity;
    }

    protected void showToast(String content) {
        ToastUtil.showToast(mContext, content);
    }

    protected void showToast(int resId) {
        ToastUtil.showToast(mContext, resId);
    }
}
