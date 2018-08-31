package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivityMainBinding;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MAX_HOT_EMOTICON_PAGE_NUM = 5;

    private ActivityMainBinding mBinding;
    private EmoticonSuiteAdapter mHotEmoticonAdapter;
    private RecyclerView mHotEmoticonRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private NestedScrollView mNestedScrollView;
    private Database mDatabase;
    private EmoticonSuiteLayout mDailyEmoticonLayout;

    private int mHotPageNum;
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
        mBinding.cateoryListInclude.mainCategoryStarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.STAR, "明星红人");
                startActivity(intent);
            }
        });
        mBinding.cateoryListInclude.mainCategoryShowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.SHOW, "撩妹示爱");
                startActivity(intent);
            }
        });
        mBinding.cateoryListInclude.mainCategoryAcgnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.ACGN, "二次元");
                startActivity(intent);
            }
        });
        mBinding.cateoryListInclude.mainCategoryFestivalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.FESTIVAL, "节日热点");
                startActivity(intent);
            }
        });
        mBinding.cateoryListInclude.mainCategoryCuteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.CUTE, "萌萌哒");
                startActivity(intent);
            }
        });
        mBinding.cateoryListInclude.mainCategoryQunLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.QUN, "欢乐群聊");
                startActivity(intent);
            }
        });
        mBinding.cateoryListInclude.mainCategoryComicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.COMIC, "卡通形象");
                startActivity(intent);
            }
        });
        mBinding.cateoryListInclude.mainCategoryWishLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.WISH, "祝福祝愿");
                startActivity(intent);
            }
        });
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
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mSwipeLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });

        mHotEmoticonRecyclerView = mBinding.hotEmoticonInclude.mainHotRecyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setSmoothScrollbarEnabled(false);
        mHotEmoticonRecyclerView.setLayoutManager(linearLayoutManager);
        mHotEmoticonAdapter = new EmoticonSuiteAdapter(mDatabase.getBatchEmoticonSuites(3));
        mHotEmoticonRecyclerView.setAdapter(mHotEmoticonAdapter);
        mHotEmoticonRecyclerView.setNestedScrollingEnabled(false);
        mHotEmoticonRecyclerView.setItemViewCacheSize(20);
        mHotEmoticonRecyclerView.setDrawingCacheEnabled(true);
        mHotEmoticonRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        mNestedScrollView = mBinding.mainNestedScrollView;
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    if (mHotPageNum > MAX_HOT_EMOTICON_PAGE_NUM) {
                        return;
                    }
                    int pos = mHotEmoticonAdapter.getItemCount();
                    mHotEmoticonAdapter.appendItems(mDatabase.getBatchEmoticonSuites(3));
                    mHotEmoticonAdapter.notifyItemRangeChanged(pos, 3);
                    mHotPageNum ++;
                }
            }
        });
    }

    private class EmoticonSuiteHolder extends RecyclerView.ViewHolder {
        public EmoticonSuiteLayout mEmoticonSuiteLayout;

        public EmoticonSuiteHolder(View itemView) {
            super(itemView);

            mEmoticonSuiteLayout = itemView.findViewById(R.id.list_item_emoticon_suite_layout);
        }

        public void bindEmoticonSuite(EmoticonSuite emoticonSuite) {
            mEmoticonSuiteLayout.setEmoticonSuite(emoticonSuite);
        }
    }

    private class EmoticonSuiteAdapter extends RecyclerView.Adapter<EmoticonSuiteHolder> {
        private List<EmoticonSuite> mEmoticonSuites;

        public EmoticonSuiteAdapter(List<EmoticonSuite> emoticonSuites) {
            mEmoticonSuites = new LinkedList<>();
            mEmoticonSuites.addAll(emoticonSuites);
        }

        @NonNull
        @Override
        public EmoticonSuiteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View v = inflater.inflate(R.layout.list_item_hot_emoticon, parent, false);
            return new EmoticonSuiteHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull EmoticonSuiteHolder holder, int position) {
            EmoticonSuite emoticonSuite = mEmoticonSuites.get(position);
            holder.bindEmoticonSuite(emoticonSuite);
        }

        @Override
        public int getItemCount() {
            return mEmoticonSuites.size();
        }

        public void pushItems(List<EmoticonSuite> emoticonSuites) {
            mEmoticonSuites.addAll(0, emoticonSuites);
        }

        public void appendItems(List<EmoticonSuite> emoticonSuites) {
            mEmoticonSuites.addAll(emoticonSuites);
        }
    }
}
