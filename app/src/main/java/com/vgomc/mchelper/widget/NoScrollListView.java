package com.vgomc.mchelper.widget;

/**
 * Created by weizhouh on 5/19/2015.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.DimenUtil;

import org.holoeverywhere.widget.LinearLayout;

public class NoScrollListView extends LinearLayout {

    public NoScrollListView(Context context) {
        super(context);
        this.mContext = context;
        initAttr(context, null);
    }

    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAttr(context, attrs);
    }

    public void initAttr(Context context, AttributeSet attrs) {
        setOrientation(android.widget.LinearLayout.VERTICAL);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.NoScrollListView);
            dividerColor = typedArray.getColor(
                    R.styleable.NoScrollListView_dividerColor, Color.WHITE);
            dividerHeight = typedArray.getDimensionPixelOffset(
                    R.styleable.NoScrollListView_dividerHeight, 0);
            typedArray.recycle();
        }
    }

    private Context mContext;
    private Adapter mAdapter;
    private int dividerHeight = 0;
    private int dividerColor = 0x00000000;

    public void setAdapter(Adapter adapter) {
        this.mAdapter = mAdapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(new DataSetObserver() {

                @Override
                public void onChanged() {
                    // TODO Auto-generated method stub
                    super.onChanged();
                    notifyDataSetChanged();
                }
            });
        }
        notifyDataSetChanged();
    }

    public void setDividerColor(int color) {
        this.dividerColor = color;
        notifyDataSetChanged();
    }

    public void setDividerHeight(int dpSize) {
        this.dividerHeight = DimenUtil.dip2px(mContext, dpSize);
        notifyDataSetChanged();
    }

    private OnNoScrollItemClickListener mOnItemClickListener;
    private OnNoScrollItemLongClcikListener mOnItemLongClickListener;

    public void setOnItemClickListener(
            OnNoScrollItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        notifyDataSetChanged();
    }

    public void setOnItemLongClickListener(
            OnNoScrollItemLongClcikListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        removeAllViews();
        if (mAdapter != null) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View v = mAdapter.getView(i, findViewWithTag(i), this);
                v.setTag(i);
                if (mOnItemClickListener != null) {
                    v.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            // TODO Auto-generated method stub
                            int index = (Integer) arg0.getTag();
                            mOnItemClickListener.onItemClick(arg0, index,
                                    mAdapter.getItemId(index));
                        }
                    });
                }
                if (mOnItemLongClickListener != null) {
                    v.setOnLongClickListener(new OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View arg0) {
                            // TODO Auto-generated method stub
                            int index = (Integer) arg0.getTag();
                            mOnItemLongClickListener.onItemClick(arg0, index,
                                    mAdapter.getItemId(index));
                            return true;
                        }
                    });
                }
                addView(v);
                if (dividerHeight != 0 && i != mAdapter.getCount() - 1) {
                    View view = new View(mContext);
                    view.setLayoutParams(new LayoutParams(
                            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                            dividerHeight));
                    view.setBackgroundColor(dividerColor);
                    addView(view);
                }
            }
        }
        System.gc();
    }

    public interface OnNoScrollItemClickListener {
        public void onItemClick(View v, int position, long id);
    }

    public interface OnNoScrollItemLongClcikListener {
        public void onItemClick(View v, int position, long id);
    }
}