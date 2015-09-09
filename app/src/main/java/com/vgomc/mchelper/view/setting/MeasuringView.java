package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.Measuring;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.MeasuringChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;
import com.vgomc.mchelper.widget.MeasuringEditView;

import org.holoeverywhere.app.AlertDialog;

/**
 * Created by weizhouh on 5/21/2015.
 */
public class MeasuringView extends BaseCollapsibleView {
    public MeasuringView(Context context) {
        super(context);
        setTitle(R.string.setting_measuring);
        setContentView(new MeasuringContentView(context));
    }

    private class MeasuringContentView extends BaseCollapsibleContentView {

        private NoScrollListView mChannelListView;
        private MeasuringChannelAdapter mMeasuringChannelAdapter;

        public MeasuringContentView(Context context) {
            super(context);
            initView();
            initListener();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_measuring;
        }

        @Override
        protected void updateData() {
            mMeasuringChannelAdapter.setList(Configuration.getInstance().measuringList);
            mMeasuringChannelAdapter.notifyDataSetChanged();
        }

        private void initView() {
            mChannelListView = (NoScrollListView) findViewById(R.id.nslv_setting_measuring);
            mMeasuringChannelAdapter = new MeasuringChannelAdapter(mContext);
            mChannelListView.setAdapter(mMeasuringChannelAdapter);
            mMeasuringChannelAdapter.setList(Configuration.getInstance().measuringList);
        }

        private void initListener() {
            mChannelListView.setOnItemClickListener(new NoScrollListView.OnNoScrollItemClickListener() {
                @Override
                public void onItemClick(View v, Object item, final int position, long id) {
                    final MeasuringEditView editView = new MeasuringEditView(mContext, position);
                    new AlertDialog.Builder(mContext).setView(editView).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Measuring measuring = editView.getMeasuring();
                            Configuration.getInstance().measuringList.set(position, measuring);
                            mMeasuringChannelAdapter.setList(Configuration.getInstance().measuringList);
                        }
                    }).create().show();
                }
            });
        }
    }
}
