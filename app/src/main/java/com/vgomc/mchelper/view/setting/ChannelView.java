package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.Entity.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.ChannelMultipleVariableAdapter;
import com.vgomc.mchelper.adapter.setting.ChannelSingleVariableAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;
import com.vgomc.mchelper.widget.VariableEditView;

import org.holoeverywhere.app.AlertDialog;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class ChannelView extends BaseCollapsibleView {
    public ChannelView(Context context) {
        super(context);
        setTitle(R.string.setting_channel);
        setContentView(new ChannelContentView(context));
    }

    private class ChannelContentView extends BaseCollapsibleContentView {

        private NoScrollListView mSingleListView;
        private NoScrollListView mMultipleListView;
        private ChannelSingleVariableAdapter mSingleChannelAdapter;
        private ChannelMultipleVariableAdapter mMultipleChannelAdapter;

        public ChannelContentView(Context context) {
            super(context);
            initView();
            updateData();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_channel;
        }

        @Override
        protected void updateData() {
            mSingleChannelAdapter.updateList();
            mMultipleChannelAdapter.updateList();
        }

        private void initView() {
            mSingleListView = (NoScrollListView) findViewById(R.id.nslv_setting_channel_single);
            mMultipleListView = (NoScrollListView) findViewById(R.id.nslv_setting_channel_multiple);
            mSingleChannelAdapter = new ChannelSingleVariableAdapter(mContext);
            mMultipleChannelAdapter = new ChannelMultipleVariableAdapter(mContext);
            mSingleListView.setAdapter(mSingleChannelAdapter);
            mSingleListView.setOnItemClickListener(new OnSingleListItemClickListener());
            mMultipleListView.setAdapter(mMultipleChannelAdapter); // Add on click listener in adapter
        }
    }

    class OnSingleListItemClickListener implements NoScrollListView.OnNoScrollItemClickListener {

        @Override
        public void onItemClick(final View v, Object item, int position, long id) {
            final Channel channel = (Channel) item;
            final VariableEditView editView = new VariableEditView(mContext);
            editView.initData(channel.type, channel.subject + "通道", channel.variables.get(0));
            new AlertDialog.Builder(mContext).setView(editView).setPositiveButton(R.string.dialog_confirm, new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Variable variable = editView.getData();
                    channel.variables.set(0, variable);
                    Configuration.getInstance().channelMap.put(channel.subject, channel);
                    ChannelView.this.updateData();
                }
            }).create().show();
        }
    }
}