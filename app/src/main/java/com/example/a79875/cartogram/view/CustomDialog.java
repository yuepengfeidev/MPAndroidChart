package com.example.a79875.cartogram.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a79875.cartogram.R;

import java.util.ArrayList;


// 自定义数据对话框
public class CustomDialog extends Dialog {

    private View.OnClickListener onPositiveClickListener;

    private View.OnClickListener onNegativeClickListener;

    /* 奥迪编辑框*/
    private ArrayList<EditText> audiEditTexts;
    /* 奔驰编辑框*/
    private ArrayList<EditText> benzEditTexts;


    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_set_data);

        init();
    }

    /* 初始化*/
    private void init() {
        TextView positiveButton = findViewById(R.id.dialog_positive);
        TextView negativeButton = findViewById(R.id.dialog_negative);
        if (onPositiveClickListener != null){
            positiveButton.setOnClickListener(onPositiveClickListener);
        }
        if (onNegativeClickListener != null){
            negativeButton.setOnClickListener(onNegativeClickListener);
        }

        audiEditTexts = new ArrayList<>();
        benzEditTexts = new ArrayList<>();

        EditText mEditText09Audi = findViewById(R.id.et_09_audi);
        audiEditTexts.add(mEditText09Audi);
        EditText mEditText09Benz = findViewById(R.id.et_09_benz);
        benzEditTexts.add(mEditText09Benz);
        EditText mEditText10Audi = findViewById(R.id.et_10_audi);
        audiEditTexts.add(mEditText10Audi);
        EditText mEditText10Benz = findViewById(R.id.et_10_benz);
        benzEditTexts.add(mEditText10Benz);
        EditText mEditText11Audi = findViewById(R.id.et_11_audi);
        audiEditTexts.add(mEditText11Audi);
        EditText mEditText11Benz = findViewById(R.id.et_11_benz);
        benzEditTexts.add(mEditText11Benz);
        EditText mEditText12Audi = findViewById(R.id.et_12_audi);
        audiEditTexts.add(mEditText12Audi);
        EditText mEditText12Benz = findViewById(R.id.et_12_benz);
        benzEditTexts.add(mEditText12Benz);
        EditText mEditText13Audi = findViewById(R.id.et_13_audi);
        audiEditTexts.add(mEditText13Audi);
        EditText mEditText13Benz = findViewById(R.id.et_13_benz);
        benzEditTexts.add(mEditText13Benz);
        EditText mEditText14Audi = findViewById(R.id.et_14_audi);
        audiEditTexts.add(mEditText14Audi);
        EditText mEditText14Benz = findViewById(R.id.et_14_benz);
        benzEditTexts.add(mEditText14Benz);
        EditText mEditText15Audi = findViewById(R.id.et_15_audi);
        audiEditTexts.add(mEditText15Audi);
        EditText mEditText15Benz = findViewById(R.id.et_15_benz);
        benzEditTexts.add(mEditText15Benz);
        EditText mEditText16Audi = findViewById(R.id.et_16_audi);
        audiEditTexts.add(mEditText16Audi);
        EditText mEditText16Benz = findViewById(R.id.et_16_benz);
        benzEditTexts.add(mEditText16Benz);
        EditText mEditText17Audi = findViewById(R.id.et_17_audi);
        audiEditTexts.add(mEditText17Audi);
        EditText mEditText17Benz = findViewById(R.id.et_17_benz);
        benzEditTexts.add(mEditText17Benz);
        EditText mEditText18Audi = findViewById(R.id.et_18_audi);
        audiEditTexts.add(mEditText18Audi);
        EditText mEditText18Benz = findViewById(R.id.et_18_benz);
        benzEditTexts.add(mEditText18Benz);
    }

    /* 点击确定按钮*/

    public CustomDialog setOnPositiveClickListener(View.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
        return this;
    }

    /* 点击取消按钮*/

    public CustomDialog setOnNegativeClickListener(View.OnClickListener onNegativeClickListener) {
        this.onNegativeClickListener = onNegativeClickListener;
        return this;
    }

    /* 获取到输入奥迪的数据*/
    public ArrayList<Integer> getAudiData(){
        ArrayList<Integer> data = new ArrayList<>();
        for (EditText editText : audiEditTexts){
            if (!editText.getText().toString().equals("")){
                data.add(Integer.valueOf(editText.getText().toString()));
            }
        }
        return data;
    }

    /* 获取到输入奔驰的数据*/
    public ArrayList<Integer> getBenzData(){
        ArrayList<Integer> data = new ArrayList<>();
        for (EditText editText : benzEditTexts){
            if (!editText.getText().toString().equals("")){
                data.add(Integer.valueOf(editText.getText().toString()));
            }
        }
        return data;
    }
}
