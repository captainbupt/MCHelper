package com.vgomc.mchelper.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.vgomc.mchelper.Entity.setting.Variable;
import com.vgomc.mchelper.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/23/2015.
 */
public class DefaultFormulaEditView extends LinearLayout {

    private Context mContext;
    private Switch mFormulaSwitch;
    private EditText[] mFactorEditTexts;
    private TextView mX2TextView;
    private TextView mX3TextView;

    public DefaultFormulaEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_edit_formula, this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mFormulaSwitch = (Switch) findViewById(R.id.sw_default_formula);
        mFactorEditTexts = new EditText[Variable.COUNT_FACTORS];
        mFactorEditTexts[0] = (EditText) findViewById(R.id.et_default_formula_k0);
        mFactorEditTexts[1] = (EditText) findViewById(R.id.et_default_formula_k1);
        mFactorEditTexts[2] = (EditText) findViewById(R.id.et_default_formula_k2);
        mFactorEditTexts[3] = (EditText) findViewById(R.id.et_default_formula_k3);

        mX2TextView = (TextView) findViewById(R.id.tv_default_formula_x2);
        mX3TextView = (TextView) findViewById(R.id.tv_default_formula_x3);
    }

    private void initData() {
        int exponentialSize = getResources().getDimensionPixelSize(R.dimen.text_size_small);
        SpannableString x2SpannableString = new SpannableString("*x2 + ");
        x2SpannableString.setSpan(new SuperscriptSpan(), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        x2SpannableString.setSpan(new AbsoluteSizeSpan(exponentialSize), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        SpannableString x3SpannableString = new SpannableString("*x3");
        x3SpannableString.setSpan(new SuperscriptSpan(), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        x3SpannableString.setSpan(new AbsoluteSizeSpan(exponentialSize), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mX2TextView.setText(x2SpannableString);
        mX3TextView.setText(x3SpannableString);
    }

    private void initListener() {
        mFormulaSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    disableFactors();
                } else {
                    enableFactors();
                }
            }
        });
    }

    private void enableFactors() {
        for (int ii = 0; ii < Variable.COUNT_FACTORS; ii++) {
            mFactorEditTexts[ii].setEnabled(true);
        }
    }

    private void disableFactors() {
        mFactorEditTexts[0].setText(0.0f + "");
        mFactorEditTexts[1].setText(1.0f + "");
        mFactorEditTexts[2].setText(0.0f + "");
        mFactorEditTexts[3].setText(0.0f + "");
        for (int ii = 0; ii < Variable.COUNT_FACTORS; ii++) {
            mFactorEditTexts[ii].setEnabled(false);
        }
    }

    public void setFactors(boolean isOn, float[] factors) {
        if (isOn) {
            mFormulaSwitch.setChecked(true);
            for (int ii = 0; ii < Variable.COUNT_FACTORS; ii++) {
                mFactorEditTexts[ii].setText(factors[ii] + "");
            }
            enableFactors();
        } else {
            mFormulaSwitch.setChecked(false);
            disableFactors();
        }
    }

    public boolean isOn() {
        return mFormulaSwitch.isChecked();
    }

    public float[] getFactors() {
        float[] factors = new float[Variable.COUNT_FACTORS];
        for (int ii = 0; ii < Variable.COUNT_FACTORS; ii++) {
            try {
                factors[ii] = Float.parseFloat(mFactorEditTexts[ii].getText().toString());
            } catch (Exception e) {
                return null;
            }
        }
        return factors;
    }

}
