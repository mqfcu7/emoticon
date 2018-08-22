package com.mqfcu7.jiangmeilan.emoticon;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private RecyclerView mHotAvatarRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private NestedScrollView mNestedScrollView;
    private Database mDatabase;
    private EmoticonSuiteLayout mDailyEmoticonLayout;

    private EmoticonSuiteGenerator mEmoticonGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Utils.setStatusBarLightMode(this, getWindow(), true);
        mDatabase = new Database(getApplicationContext());

        Glide.get(getApplicationContext()).clearMemory();
        mEmoticonGenerator = new EmoticonSuiteGenerator(getApplicationContext());

        initCategoryNavigateLayout();
        initDailyJoke();
        initDailyEmoticon();
        initHotEmoticon();
    }

    private void initCategoryNavigateLayout() {

    }

    private void initDailyJoke() {
        final JokeSuite joke = mDatabase.getJokeSuite();
        Glide.with(getApplicationContext())
                .load(joke.userAvatar)
                .apply(new RequestOptions().circleCrop())
                .into(mBinding.dailyJokeInclude.mainDailyJokeImageView);
        mBinding.dailyJokeInclude.mainDailyJokeNameText.setText(joke.userName);
        mBinding.dailyJokeInclude.mainDailyJokeTitleText.setText(joke.content);
        mBinding.dailyJokeInclude.mainDailyJokeGoodText.setText(String.valueOf(joke.good));
        mBinding.dailyJokeInclude.mainDailyJokeBadText.setText(String.valueOf(joke.bad));
    }

    private void initDailyEmoticon() {
        mDailyEmoticonLayout = mBinding.dailyEmoticonInclude.mainEmoticonSuiteLayout;
        mDailyEmoticonLayout.setEmoticonSuite(mEmoticonGenerator.randomEmoticonSuite());
        mDailyEmoticonLayout.setPaddingVertical(5);
    }

    private void initHotEmoticon() {
        mSwipeLayout = mBinding.mainSwipeRefreshLayout;
        mSwipeLayout.setDistanceToTriggerSync(300);
        mSwipeLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED);
        mSwipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeLayout.setSize(SwipeRefreshLayout.LARGE);
    }
}
