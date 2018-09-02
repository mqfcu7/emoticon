package com.mqfcu7.jiangmeilan.emoticon;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        Utils.setStatusBarLightMode(this, getWindow(), true);
    }
}
