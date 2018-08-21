package com.mqfcu7.jiangmeilan.emoticon;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private EmoticonSuiteLayout mDailyEmoticonLayout;

    private EmoticonSuiteGenerator mEmoticonGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Utils.setStatusBarLightMode(this, getWindow(), true);

        Glide.get(getApplicationContext()).clearMemory();
        mEmoticonGenerator = new EmoticonSuiteGenerator(getApplicationContext());

        initCategoryNavigateLayout();
        initDailyAvatar();
    }

    private void initCategoryNavigateLayout() {

    }

    private void initDailyAvatar() {
        mDailyEmoticonLayout = mBinding.dailyEmoticonInclude.mainEmoticonSuiteLayout;
        mDailyEmoticonLayout.setEmoticonSuite(mEmoticonGenerator.randomEmoticonSuite());
        mDailyEmoticonLayout.setPaddingVertical(5);
    }
}
