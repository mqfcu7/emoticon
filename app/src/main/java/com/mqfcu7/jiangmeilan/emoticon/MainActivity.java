package com.mqfcu7.jiangmeilan.emoticon;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Utils.setStatusBarLightMode(this, getWindow(), true);

        Glide.get(getApplicationContext()).clearMemory();

        initDailyAvatar();
    }

    private void initDailyAvatar() {
        mAvatarSuiteLayout = mBinding.dailyAvatarInclude.mainAvatarSuiteLayout;
        mAvatarSuiteLayout.setAvatarSuite(mAvatarSuiteGenerator.randomAvatarSuite());
        mAvatarSuiteLayout.setPaddingVertical(5);
    }
}
