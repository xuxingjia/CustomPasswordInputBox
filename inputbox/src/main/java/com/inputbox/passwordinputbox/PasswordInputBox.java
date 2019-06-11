package com.inputbox.passwordinputbox;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.inputbox.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 密码输入框
 */
public class PasswordInputBox extends LinearLayout implements TextWatcher, View.OnKeyListener {

    private int amount;
    private int defaultBg;
    private int inputBg;
    private int boxHeight;
    private int boxWidth;
    private int margin;
    private int inputType;

    private List<EditText> mEditTexts = new ArrayList<>();

    private int position = 0;
    private int textSize;

    public PasswordInputBox(Context context) {
        this(context, null);
    }

    public PasswordInputBox(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordInputBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCustomAttrs(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        setFocus();
        return true;
    }

    /**
     * 获取自定义属性
     *
     * @param context 上下文
     * @param attrs   属性Set
     */
    private void initCustomAttrs(Context context, AttributeSet attrs) {
        @SuppressLint("Recycle")
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordInputBox);
        amount = typedArray.getInt(R.styleable.PasswordInputBox_amount, 6);
        defaultBg = typedArray.getResourceId(R.styleable.PasswordInputBox_defaultBg, R.mipmap.ic_edit_text_bg_black);
        inputBg = typedArray.getResourceId(R.styleable.PasswordInputBox_inputBg, R.mipmap.ic_set_new_password_grean);
        boxHeight = typedArray.getDimensionPixelOffset(R.styleable.PasswordInputBox_boxHeight, 100);
        boxWidth = typedArray.getDimensionPixelOffset(R.styleable.PasswordInputBox_boxWidth, 100);
        margin = typedArray.getDimensionPixelOffset(R.styleable.PasswordInputBox_margin, 20);
        inputType = typedArray.getInt(R.styleable.PasswordInputBox_inputType, 1);
        textSize = typedArray.getDimensionPixelOffset(R.styleable.PasswordInputBox_textSize, 20);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = 0;
        int right;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            right = width + left;
            child.layout(left, t, right, b);
            left = right + margin;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < amount; i++) {
            EditText editText = new EditText(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(boxWidth, boxHeight);
            if (i != amount - 1) {
                layoutParams.rightMargin = margin;
            }
            editText.setLayoutParams(layoutParams);
            if (i == 0) {
                setBg(editText, true);
            } else {
                setBg(editText, false);
            }
            editText.setTextColor(Color.BLACK);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            editText.setTextSize(textSize);
            editText.setGravity(Gravity.CENTER);
            if (inputType == 3) {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (inputType == 1) {
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
            } else if (inputType == 2) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
            } else if (inputType == 4) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            addView(editText, i);
            editText.addTextChangedListener(this);
            editText.setOnKeyListener(this);
            mEditTexts.add(editText);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (start == 0 && count >= 1 && position != mEditTexts.size() - 1) {
            position++;
            mEditTexts.get(position).requestFocus();
            setBg(mEditTexts.get(position), true);
            setBg(mEditTexts.get(position - 1), false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() != 0) {
            setFocus();
            getString();
        }
    }

    /**
     * 获取输入的数字
     */
    public void getString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < amount; i++) {
            EditText child = (EditText) getChildAt(i);
            String content = child.getText().toString().trim();
            if (content.length() == 0) {
                break;
            } else {
                builder.append(content);
            }
        }
        if (commitListener != null) {
            if (builder.toString().length()==amount){
                commitListener.commitListener(builder.toString());
            }
        }
    }

    private CommitListener commitListener = null;

    public interface CommitListener {
        void commitListener(String content);
    }

    public void setCommitListener(CommitListener commitListener) {
        this.commitListener = commitListener;
    }

    /**
     * 设置焦点
     */
    private void setFocus() {
        for (int i = 0; i < getChildCount(); i++) {
            EditText child = (EditText) getChildAt(i);
            if (child.getText().length() < 1) {
                child.requestFocus();
                if (child.requestFocus()) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(child, InputMethodManager.SHOW_IMPLICIT);
                }
                return;
            }
        }
    }


    /**
     * 设置背景
     *
     * @param editText 设置背景的图片
     * @param isFocus  是否当前可选
     */
    private void setBg(EditText editText, boolean isFocus) {
        if (editText == null) {
            return;
        }
        if (isFocus) {
            editText.setBackgroundResource(inputBg);
        } else {
            editText.setBackgroundResource(defaultBg);
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        EditText editText = (EditText) v;
        if (keyCode == KeyEvent.KEYCODE_DEL && editText.getText().length() == 0) {
            int action = event.getAction();
            if (position != 0 && action == KeyEvent.ACTION_DOWN) {
                position--;
                mEditTexts.get(position).requestFocus();
                setBg(mEditTexts.get(position), true);
                setBg(mEditTexts.get(position + 1), false);
                mEditTexts.get(position).setText("");
            }
        }
        return false;
    }
}
