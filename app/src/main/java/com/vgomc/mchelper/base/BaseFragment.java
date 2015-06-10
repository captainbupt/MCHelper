package com.vgomc.mchelper.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Toast;

/**
 * Created by weizhouh on 5/19/2015.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = activity;
    }
    private void showToast(String content) {
        ToastUtil.showToast(mContext, content);
    }

    private void showToast(int resId) {
        ToastUtil.showToast(mContext, resId);
    }
}
