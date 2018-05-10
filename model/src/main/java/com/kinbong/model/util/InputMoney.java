package com.kinbong.model.util;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 限制只输入输入金额
 * Created by Administrator on 2017/10/10.
 */

public class InputMoney implements InputFilter {
    private EditText mEditText;

    public InputMoney(EditText editText) {
        this.mEditText = editText;
//        mEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        if (source.toString().equals(".") && dstart == 0 && dend == 0) {//判断小数点是否在第一位
            mEditText.setText(0 + "" + source + dest);//给小数点前面加0
            mEditText.setSelection(2);//设置光标
        }

        if (source.toString().equals("0") && dstart == 0 && dend == 0) {//判断小数点是否在第一位
            mEditText.setText(source + "." + dest);//给0后面加.
            mEditText.setSelection(2);//设置光标
        }

        if (dest.toString().indexOf(".") != -1 && (dest.length() - dest.toString().indexOf(".")) > 2) {//判断小数点是否存在并且小数点后面是否已有两个字符
            if ((dest.length() - dstart) < 3) {//判断现在输入的字符是不是在小数点后面
                return "";//过滤当前输入的字符
            }
        }

        return null;
    }

}
