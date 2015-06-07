package com.vgomc.mchelper.view.data;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class DataDetailTitleView extends BaseCollapsibleView {
    public DataDetailTitleView(Context context, boolean isFirst, boolean isLast, String title) {
        super(context);
        setTitle(R.string.data_current_detail);
        setContentView(new DataDetailTileContentView(context, isFirst, isLast, title));
    }

    class DataDetailTileContentView extends BaseCollapsibleContentView {

        private TextView mTitleTextView;

        public DataDetailTileContentView(Context context, boolean isFirst, boolean isLast, String title) {
            super(context);
            initView(isFirst, isLast, title);
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_detail_title;
        }

        private void initView(boolean isFirst, boolean isLast, String title) {
            findViewById(R.id.tv_view_data_detail_title_left).setVisibility(isFirst ? GONE : VISIBLE);
            findViewById(R.id.tv_view_data_detail_title_right).setVisibility(isLast ? GONE : VISIBLE);
            mTitleTextView = (TextView) findViewById(R.id.tv_view_data_detail_title_center);
            mTitleTextView.setText(title);
        }

        @Override
        protected void updateData() {

        }
    }
}
