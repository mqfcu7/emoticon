package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivitySearchBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;

    private Drawable mCancelDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        Utils.setStatusBarLightMode(this, getWindow(), false);

        mCancelDrawable = getResources().getDrawable(R.drawable.cancel);
        mCancelDrawable.setBounds(0, 0, 50, 50);

        initSearchLayout();
    }

    private void initSearchLayout() {
        mBinding.searchBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final EditText searchText = mBinding.searchText;
        Drawable left = searchText.getCompoundDrawables()[0];
        if (left != null) {
            left.setBounds(0, 5, 60, 44);
            searchText.setCompoundDrawables(left, searchText.getCompoundDrawables()[1],
                    searchText.getCompoundDrawables()[2], searchText.getCompoundDrawables()[3]);
        }

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText text = mBinding.searchText;
                if (text.getText() != null && text.getText().length() != 0) {
                    mBinding.searchCancel.setText("搜索");
                    text.setCompoundDrawables(text.getCompoundDrawables()[0], text.getCompoundDrawables()[1],
                            mCancelDrawable, text.getCompoundDrawables()[3]);
                } else {
                    mBinding.searchCancel.setText("取消");
                    text.setCompoundDrawables(text.getCompoundDrawables()[0], text.getCompoundDrawables()[1],
                            null, text.getCompoundDrawables()[3]);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });
        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Drawable right = mBinding.searchText.getCompoundDrawables()[2];
                    if (right != null) {
                        int x = searchText.getPaddingRight() + right.getIntrinsicWidth();
                        if (searchText.getWidth() - x < event.getX() &&
                                searchText.getWidth() - searchText.getPaddingRight() > event.getX()) {
                            searchText.setText("");
                            mBinding.searchCancel.setText("取消");
                            searchText.setCompoundDrawables(searchText.getCompoundDrawables()[0], searchText.getCompoundDrawables()[1],
                                    null, searchText.getCompoundDrawables()[3]);
                        }
                    }
                }
                return false;
            }
        });
        setSearchUI();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                showInputMethod();
            }
        }, 500);

        mBinding.searchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.searchCancel.getText().equals("取消")) {
                    cancelInput();
                } else {
                    onSearch();
                }
            }
        });
    }

    private void setSearchUI() {
        ViewGroup.LayoutParams cancelLayout = mBinding.searchCancel.getLayoutParams();
        EditText searchText = mBinding.searchText;
        searchText.setFocusable(true);
        searchText.setFocusableInTouchMode(true);
        searchText.requestFocus();

        ViewGroup.LayoutParams searchLayout = searchText.getLayoutParams();
        searchLayout.width -= cancelLayout.width;
        searchText.setLayoutParams(searchLayout);

        mBinding.searchCancel.setVisibility(View.VISIBLE);
        showInputMethod();
    }

    private void cancelInput() {
        ViewGroup.LayoutParams cancelLayout = mBinding.searchCancel.getLayoutParams();

        EditText searchText = mBinding.searchText;
        searchText.setFocusable(false);
        searchText.setFocusableInTouchMode(false);
        ViewGroup.LayoutParams searchLayout = searchText.getLayoutParams();
        searchLayout.width += cancelLayout.width;
        searchText.setLayoutParams(searchLayout);
        mBinding.searchCancel.setVisibility(View.GONE);

        hiddenInputMothod();
    }

    private void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mBinding.searchText, InputMethodManager.SHOW_FORCED);
    }

    private void hiddenInputMothod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mBinding.searchText.getWindowToken(), 0);
    }

    private void onSearch() {
        Log.d("TAG", "search");
    }
}
