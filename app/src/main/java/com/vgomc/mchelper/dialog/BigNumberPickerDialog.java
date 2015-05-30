package com.vgomc.mchelper.dialog;

import android.content.Context;
import android.content.DialogInterface;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.widget.MyBigNumberPicker;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;

/**
 * Created by weizhouh on 5/28/2015.
 */
public class BigNumberPickerDialog {

    public static AlertDialog getBigNumberPickerDialog(Context context, int digitNumber, int min, int max, int current, final EditText editText, String title) {
        final MyBigNumberPicker numberPicker = new MyBigNumberPicker(context, max, min, digitNumber, current);
        return new AlertDialog.Builder(context).setView(numberPicker).setTitle(title)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText.setText(numberPicker.getValue() + "");
                    }
                }).create();

    }
}
