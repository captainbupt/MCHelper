package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.Network;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.utility.TimeUtil;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.NetworkEditView;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by weizhouh on 5/21/2015.
 */
public class NetworkView extends BaseCollapsibleView {
    public NetworkView(Context context) {
        super(context);
        setTitle(R.string.setting_network);
        setContentView(new BatterContentView(context));
    }

    private class BatterContentView extends BaseCollapsibleContentView {

        private TextView mModeTextView;
        private TextView mTimeTextView;
        private TextView mAddressTextView;
        private TextView mPortTextView;
        private LinearLayout mContentLayout;

        public BatterContentView(Context context) {
            super(context);
            initView();
            initListener();
            updateData();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_network;
        }

        @Override
        protected void updateData() {
            Network network = Configuration.getInstance().network;
            mModeTextView.setText(network.isAlwaysOn ? R.string.setting_network_mode_always : R.string.setting_network_mode_auto);
            mContentLayout.setVisibility(network.isAlwaysOn ? View.GONE : View.VISIBLE);
            int[] timeArray = TimeUtil.long2timeArray(network.time);
            mTimeTextView.setText(timeArray[0] + getResources().getString(R.string.setting_time_hour) + timeArray[1] + getResources().getString(R.string.setting_time_minute) + timeArray[2] + getResources().getString(R.string.setting_time_second));
            mAddressTextView.setText(network.address);
            mPortTextView.setText(network.port + "");
        }

        private void initView() {
            mModeTextView = (TextView) findViewById(R.id.tv_view_setting_network_mode);
            mTimeTextView = (TextView) findViewById(R.id.tv_view_setting_network_time);
            mAddressTextView = (TextView) findViewById(R.id.tv_view_setting_network_address);
            mPortTextView = (TextView) findViewById(R.id.tv_view_setting_network_port);
            mContentLayout = (LinearLayout) findViewById(R.id.ll_view_setting_network_content);
        }

        private void initListener() {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final NetworkEditView editView = new NetworkEditView(mContext);
                    new AlertDialog.Builder(mContext).setTitle(R.string.setting_network).setView(editView).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Network network = editView.getNetwork();
                            if (network != null) {
                                Configuration.getInstance().network = network;
                                NetworkView.this.updateData();
                            } else {
                                ToastUtil.showToast(mContext, R.string.tip_invalid_input);
                                try {
                                    Field field = dialog.getClass()
                                            .getSuperclass().getDeclaredField(
                                                    "mShowing");
                                    field.setAccessible(true);
                                    //   将mShowing变量设为false，表示对话框已关闭
                                    field.set(dialog, false);
                                    dialog.dismiss();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }).create().show();
                }
            });
        }
    }
}
