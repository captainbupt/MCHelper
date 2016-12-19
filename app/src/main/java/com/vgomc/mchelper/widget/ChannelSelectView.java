package com.vgomc.mchelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.vgomc.mchelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dreamone on 2016/9/27.
 */
public class ChannelSelectView extends LinearLayout {

    private Context context;

    private Spinner chanelSpinner;

    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    public ChannelSelectView(Context context) {

        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_channel_address_select, this);
        initOptions();
        this.chanelSpinner = (Spinner)findViewById(R.id.spinnerChannel);

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this.context,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.chanelSpinner.setAdapter(adapter);

    }



    public  ChannelSelectView(Context context, String current) {
        this(context);
        setData(current);
    }

    public  String getData() {
        int selected = this.chanelSpinner.getSelectedItemPosition();
        return  this.list.get(selected);
    }


    private void setData(String value) {
        for(int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(value)) {
                this.chanelSpinner.setSelection(i);
                return;
            }
        }
    }

    private void initOptions() {
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");
        list.add("F");
        list.add("G");
        list.add("H");
        list.add("I");
        list.add("J");
        list.add("K");
        list.add("L");
        list.add("M");
        list.add("N");
        list.add("O");
        list.add("P");
        list.add("Q");
        list.add("R");
        list.add("S");
        list.add("T");
        list.add("U");
        list.add("V");
        list.add("W");
        list.add("X");
        list.add("Y");
        list.add("Z");
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("h");
        list.add("i");
        list.add("j");
        list.add("k");
        list.add("l");
        list.add("m");
        list.add("n");
        list.add("o");
        list.add("p");
        list.add("q");
        list.add("r");
        list.add("s");
        list.add("t");
        list.add("u");
        list.add("v");
        list.add("w");
        list.add("x");
        list.add("y");
        list.add("z");
    }


    public  static AlertDialog getChannelSelectDialog(Context context, String current,final EditText editText, String title) {
        final ChannelSelectView channelSelectView = new ChannelSelectView(context, current);
        return new AlertDialog.Builder(context).setView(channelSelectView).setTitle(title)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editText.setText(channelSelectView.getData());
                    }
                }).create();
    }


}
