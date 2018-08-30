package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivityEmoticonListBinding;

import java.util.LinkedList;
import java.util.List;

public class EmoticonListActivity extends AppCompatActivity {
    private static final String EXTRA_EMOTICON_TYPE =
            "com.mqfcu7.jiangmeilan.emoticon.emoticon_type";
    private static final String EXTRA_EMOTICON_TITLE =
            "com.mqfcu7.jiangmeilan.emoticon.emoticon_title";

    private Database mDatabase;
    private Handler mHandler = new Handler();

    private ActivityEmoticonListBinding mBinding;
    private PtrClassicFrameLayout mFrameLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterWithHF mAdapter;

    private int mEmoticonType;
    private String mEmoticonTitle;
    private List<String[]> mEmoticonList = new LinkedList<>();
    private int mMaxEmoticonId;

    public static Intent newIntent(Context context, int emoticon_type, String emoticon_title) {
        Intent intent = new Intent(context, EmoticonListActivity.class);
        intent.putExtra(EXTRA_EMOTICON_TYPE, emoticon_type);
        intent.putExtra(EXTRA_EMOTICON_TITLE, emoticon_title);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_emoticon_list);
        Utils.setStatusBarLightMode(this, getWindow(), true);
        mDatabase = new Database(getApplicationContext());

        Glide.get(getApplicationContext()).clearMemory();
        mEmoticonType = (int)getIntent().getSerializableExtra(EXTRA_EMOTICON_TYPE);
        mEmoticonTitle = (String)getIntent().getSerializableExtra(EXTRA_EMOTICON_TITLE);
        mBinding.emoticonListTitleText.setText(mEmoticonTitle);

        initBackBanner();
        createEmoticonList();
        createFrameLayout();
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
        mBinding.emoticonListBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createEmoticonList() {
        mRecyclerView = mBinding.emoticonListRecycleView;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        EmoticonAdapter adapter = new EmoticonAdapter(mEmoticonList);
        mAdapter = new RecyclerAdapterWithHF(adapter);
        mRecyclerView.setAdapter(mAdapter);
        updateEmoticonList(1, 30);
    }

    private void createFrameLayout() {
        mFrameLayout = mBinding.emoticonListFrameLayout;
        mFrameLayout.setLoadMoreEnable(true);
        mFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                final int num = updateEmoticonList(1, 9);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyItemRangeChanged(0, num);
                        mFrameLayout.refreshComplete();
                    }
                }, 1000);
            }
        });
        mFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int num = updateEmoticonList(2, 15);
                        mAdapter.notifyDataSetChanged();
                        mFrameLayout.loadMoreComplete(true);
                    }
                }, 1000);
            }
        });
    }

    private int updateEmoticonList(int aspect, int num) {
        final int imageNum = EmoticonsLayout.IMAGE_NUM;
        List<Emoticon> newEmoticon = mDatabase.getBatchEmoticons(mMaxEmoticonId, mEmoticonType, num);
        String[] ss = null;
        for (int i = 0; i < newEmoticon.size() / imageNum * imageNum; ++ i) {
            mMaxEmoticonId = Math.max(mMaxEmoticonId, newEmoticon.get(i).id);
            if (i % imageNum == 0) {
                ss = new String[imageNum];
            }
            ss[i % imageNum] = newEmoticon.get(i).url;
            if ((i + 1) % imageNum == 0) {
                if (aspect == 1) {
                    ((LinkedList) mEmoticonList).addFirst(ss);
                } else {
                    ((LinkedList) mEmoticonList).addLast(ss);
                }
            }
        }

        return newEmoticon.size() / imageNum * imageNum;
    }

    private class EmoticonHolder extends RecyclerView.ViewHolder {
        private EmoticonsLayout mEmoticonsLayout;

        public EmoticonHolder(View v) {
            super(v);

            mEmoticonsLayout = (EmoticonsLayout) v.findViewById(R.id.list_item_emoticons_layout);
        }

        public void bindEmoticon(String[] emoticons) {
            mEmoticonsLayout.setEmoticon(emoticons);
        }
    }

    private class EmoticonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<String[]> mEmoticonList;

        public EmoticonAdapter(List<String[]> emoticonList) {
            mEmoticonList = emoticonList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(EmoticonListActivity.this);
            View v = inflater.inflate(R.layout.list_item_emoticon, parent, false);
            return new EmoticonHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String[] emoticon = mEmoticonList.get(position);
            ((EmoticonHolder)holder).bindEmoticon(emoticon);
        }

        @Override
        public int getItemCount() {
            return mEmoticonList.size();
        }
    }
}
