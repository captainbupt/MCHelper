package com.vgomc.mchelper.base;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

import com.vgomc.mchelper.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/20/2015.
 */
public class BaseCollapsibleView extends org.holoeverywhere.widget.LinearLayout {

    private Context mContext;
    private TextView mTitleTextView;
    private BaseCollapsibleContentView mContentView;

    private boolean isContentShow = false;

    public BaseCollapsibleView(Context context) {
        super(context);
        this.mContext = context;
        setLayoutParams(new AbsListView.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_base_collapsible, this);
    }

    public void initContentView(BaseCollapsibleContentView contentView) {
        this.mContentView = contentView;
        addView(mContentView);
        hideContent();

        mTitleTextView = (TextView) findViewById(R.id.tv_base_collapsible_title);
        mTitleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTitleClick();
            }
        });
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

    public void setContent(View contentView) {
        mContentView.removeAllViews();
        mContentView.addView(contentView);
    }


    public void showContent() {
        mContentView.setVisibility(View.VISIBLE);
        isContentShow = true;
    }

    public void hideContent() {
        mContentView.setVisibility(View.GONE);
        isContentShow = false;
    }

}
