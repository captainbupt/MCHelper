package com.vgomc.mchelper.view.data;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.data.CurrentDataAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;

/**
 * Created by weizhouh on 6/5/2015.
 */
public class CurrentDataView extends BaseCollapsibleView {
    public CurrentDataView(Context context) {
        super(context);
        setTitle(R.string.data_current);
        setContentView(new CurrentDataContentView(context));
    }

    class CurrentDataContentView extends BaseCollapsibleContentView {

        private NoScrollListView mDataListView;
        private CurrentDataAdapter mDataAdapter;

        public CurrentDataContentView(Context context) {
            super(context);
            mDataListView = (NoScrollListView) findViewById(R.id.nslv_data_current);
            mDataAdapter = new CurrentDataAdapter(mContext);
            mDataListView.setAdapter(mDataAdapter);
            updateData();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_current;
        }

        @Override
        protected void updateData() {

        }
    }
}
