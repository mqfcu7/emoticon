package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivityEmoticonDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class EmoticonDetailActivity extends FragmentActivity {
    private static final String EXTRA_EMOTICON_TITLE =
            "com.mqfcu7.jiangmeilan.emoticon.title";
    private static final String EXTRA_EMOTICON_URL =
            "com.mqfcu7.jiangmeilan.emoticon.image_urls";
    private static final String EXTRA_EMOTICON_POS =
            "com.mqfcu7.jiangmeilan.emoticon.image_pos";

    private ActivityEmoticonDetailBinding mBinding;
    private String mTitle;
    private List<String> mImageUrls = new ArrayList<>();
    private int mImagePos;

    public static Intent newIntent(Context context, String title, List<String> imageUrls, int curPos) {
        Intent intent = new Intent(context, EmoticonDetailActivity.class);
        intent.putExtra(EXTRA_EMOTICON_TITLE, title);
        intent.putStringArrayListExtra(EXTRA_EMOTICON_URL, (ArrayList<String>) imageUrls);
        intent.putExtra(EXTRA_EMOTICON_POS, curPos);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_emoticon_detail);
        Utils.setStatusBarLightMode(this, getWindow(), true);

        Glide.get(getApplicationContext()).clearMemory();
        mTitle = getIntent().getStringExtra(EXTRA_EMOTICON_TITLE);
        mImageUrls = getIntent().getStringArrayListExtra(EXTRA_EMOTICON_URL);
        mImagePos = getIntent().getIntExtra(EXTRA_EMOTICON_POS, 0);

        initBackBanner();
        initViewPager();
        initShareLayour();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initBackBanner() {
        mBinding.emoticonDetailTitleText.setText(mTitle);
        mBinding.emoticonDetailBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViewPager() {
        ViewPager viewPager = mBinding.emoticonDetailViewPager;

        FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return EmoticonFragment.newInstance(mImageUrls.get(position));
            }

            @Override
            public int getCount() {
                return mImageUrls.size();
            }
        });
        viewPager.setCurrentItem(mImagePos);
    }

    private void initShareLayour() {
        mBinding.emoticonDetailQqImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mBinding.emoticonDetailViewPager.getCurrentItem();
                Utils.shareQQ(EmoticonDetailActivity.this, mImageUrls.get(pos));
            }
        });
        mBinding.emoticonDetailWeixinImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mBinding.emoticonDetailViewPager.getCurrentItem();
                Utils.shareWeiXin(EmoticonDetailActivity.this, mImageUrls.get(pos));
            }
        });
        mBinding.emoticonDetailDlImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mBinding.emoticonDetailViewPager.getCurrentItem();
                Utils.saveNetImage(EmoticonDetailActivity.this, mImageUrls.get(pos));
            }
        });
    }
}
