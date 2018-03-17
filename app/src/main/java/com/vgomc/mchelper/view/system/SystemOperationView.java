package com.vgomc.mchelper.view.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by weizhouh on 6/4/2015.
 */
public class SystemOperationView extends BaseCollapsibleView {
    public SystemOperationView(Context context) {
        super(context);
        setTitle(R.string.system_operation);
        setContentView(new SystemOperationContentView(mContext));
    }

    class SystemOperationContentView extends BaseCollapsibleContentView {


        public SystemOperationContentView(Context context) {
            super(context);
            findViewById(R.id.tv_view_system_operation_other).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = new TextView(mContext);
                    LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_large));
                    textView.setLayoutParams(layoutParams);
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                    textView.setLineSpacing(0, 1.4f);
                    int padding = mContext.getResources().getDimensionPixelOffset(R.dimen.offset_less);
                    textView.setPadding(0, padding, 0, padding);
                    String url = "http://www.vgomc.com/";
                    String tel = "010-56330522";
                    String content = "北京微果草通信技术有限公司\n版本号：" + getVersion() + "\n" + url + "\n" + tel;
                    SpannableString ss = new SpannableString(content);
                    int urlIndex = content.indexOf(url);
                    int telIndex = content.indexOf(tel);
                    ss.setSpan(new URLSpan("http://www.vgomc.com"), urlIndex, urlIndex + url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new URLSpan("tel:01056330522"), telIndex, telIndex + tel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //textView.setText("版本号：" + getVersion());
                    textView.setText(ss);
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    new AlertDialog.Builder(mContext).setView(textView).setPositiveButton(R.string.dialog_confirm, null).show();
                }
            });
        }

        public String getVersion() {
            try {
                PackageManager manager = mContext.getPackageManager();
                PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
                String version = info.versionName;
                return version;

            } catch (Exception e) {
                e.printStackTrace();
                return "1.0";

            }
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_system_operation;
        }

        @Override
        protected void updateData() {

        }
    }
}
