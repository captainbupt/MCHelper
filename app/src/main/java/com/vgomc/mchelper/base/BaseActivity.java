package com.vgomc.mchelper.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.vgomc.mchelper.utility.MyActivityManager;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.Activity;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class BaseActivity extends Activity {
    protected Context mContext;
    protected Activity mActivity;
    protected Intent mReceivedIntent;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mContext = this;
        mActivity = this;
        mReceivedIntent = getIntent();
        MyActivityManager.getAppManager().addActivity(this);
    }

    @Override
    public void finish() {
        //将当前Activity移除掉
        MyActivityManager.getAppManager().removeActivity(this);
        super.finish();
    }


    private void showToast(String content) {
        ToastUtil.showToast(mContext, content);
    }

    private void showToast(int resId) {
        ToastUtil.showToast(mContext, resId);
    }
}
