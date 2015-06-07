package com.vgomc.mchelper.base;

import android.content.Context;
import android.view.View;

import com.vgomc.mchelper.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.FrameLayout;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/20/2015.
 */
public class BaseCollapsibleView extends LinearLayout {

    protected Context mContext;
    private LinearLayout mTitleCustomContainerLayout;
    private TextView mTitleTextView;
    private FrameLayout mContainerFrameLayout;
    private BaseCollapsibleContentView mContentView;

    private boolean isContentShow = false;

    public BaseCollapsibleView(Context context) {
        super(context);
        this.mContext = context;
        setLayoutParams(new ListView.LayoutParams(
                ListView.LayoutParams.MATCH_PARENT,
                ListView.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_base_collapsible, this);
        initView();
    }

    private void initView() {
        mTitleCustomContainerLayout = (LinearLayout) findViewById(R.id.ll_base_collapsible_title_container);
        mTitleTextView = (TextView) findViewById(R.id.tv_base_collapsible_title);
        mTitleCustomContainerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleClick();
            }
        });
        mContainerFrameLayout = (FrameLayout) findViewById(R.id.fl_base_collapsible_container);
    }

    public void addTitleView(View view) {
        mTitleCustomContainerLayout.addView(view);
    }

    public void setContentView(BaseCollapsibleContentView contentView) {
        this.mContentView = contentView;
        //this.mContentView.updateData();
        mContainerFrameLayout.addView(mContentView);
        //hideContent();
    }

    protected void onTitleClick() {
        if (isContentShow) {
            hideContent();
        } else {
            showContent();
        }
    }

    public void setTitle(int resId) {
        mTitleTextView.setText(resId);
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void showContent() {
        mContainerFrameLayout.setVisibility(View.VISIBLE);
        isContentShow = true;
    }

    public void hideContent() {
        mContainerFrameLayout.setVisibility(View.GONE);
        isContentShow = false;
    }

    public void updateData() {
        mContentView.updateData();
    }

}
