package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.view.View;

import com.vgomc.mchelper.entity.data.VariableData;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.utility.TimeUtil;

import android.widget.Button;
import android.widget.TextView;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class DateDetailTableView extends BaseCollapsibleView {

    private VariableData mVariableData;
    private View.OnClickListener setAccumulateListener;

    public DateDetailTableView(Context context, int index, VariableData variableData, View.OnClickListener setAccumulateListener) {
        super(context);
        this.mVariableData = variableData;
        setTitle(getResources().getString(R.string.data_current_detail_table) + index);
        this.setAccumulateListener = setAccumulateListener;
        setContentView(new DataDetailTableContentView(context));
    }

    class DataDetailTableContentView extends BaseCollapsibleContentView {

        private TextView mCurrentTextView;
        private TextView mAverageTextView;
        private TextView mMaxTextView;
        private TextView mMaxTimeTextView;
        private TextView mMinTextView;
        private TextView mMinTimeTextView;
        private TextView mPeriodTextView;
        private TextView mEverTextView;
        private Button mResetButton;

        public DataDetailTableContentView(Context context) {
            super(context);
            initView();
            initData();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_detail_table;
        }

        private void initView() {
            mCurrentTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_current);
            mAverageTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_average);
            mMaxTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_max);
            mMaxTimeTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_max_time);
            mMinTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_min);
            mMinTimeTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_min_time);
            mPeriodTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_period);
            mEverTextView = (TextView) findViewById(R.id.tv_view_data_detail_table_ever);
            mResetButton = (Button) findViewById(R.id.btn_view_data_detail_table_reset);
        }

        private void initData() {
            mCurrentTextView.setText(mVariableData.currentValue + "");
            mAverageTextView.setText(mVariableData.averageValue + "");
            mMaxTextView.setText(mVariableData.maxValue + "");
            mMaxTimeTextView.setText(TimeUtil.long2HMString(mVariableData.maxTime));
            mMinTextView.setText(mVariableData.minValue + "");
            mMinTimeTextView.setText(TimeUtil.long2HMString(mVariableData.minTime));
            mPeriodTextView.setText(mVariableData.periodTotal + "");
            mEverTextView.setText(mVariableData.everTotal + "");
            mResetButton.setOnClickListener(setAccumulateListener);
        }

        @Override
        protected void updateData() {

        }
    }
}
