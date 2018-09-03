package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mqfcu7.jiangmeilan.emoticon.databinding.ActivitySearchBinding;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding mBinding;
    private Database mDatabase;

    private Drawable mCancelDrawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        Utils.setStatusBarLightMode(this, getWindow(), false);
        mDatabase = new Database(getApplicationContext());

        mCancelDrawable = getResources().getDrawable(R.drawable.cancel);
        mCancelDrawable.setBounds(0, 0, 50, 50);

        initSearchLayout();
        initHotSearchUI();
    }

    private void initSearchLayout() {
        mBinding.searchBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hiddenInputMothod();
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
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    onSearch(searchText.getText().toString());
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
                    onSearch(mBinding.searchText.getText().toString());
                }
            }
        });
    }

    private void initHotSearchUI() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);

        List<String> hotWords = mDatabase.getHotWords();
        for (final String word : hotWords) {
            LinearLayout layout = new LinearLayout(getApplicationContext());
            TextView view = new TextView(getApplicationContext());
            view.setLayoutParams(lp);
            view.setBackground(getResources().getDrawable(R.drawable.search_word_shape));
            view.setTextColor(Color.GRAY);
            view.setText(word);
            view.setTextSize(14);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSearch(word);
                }
            });
            layout.addView(view);
            mBinding.searchFlowLayout.addView(layout);
        }
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

    private void onSearch(final String word) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.delSearchEmoticons();
                try {
                    String url = "https://fabiaoqing.com/search/search/keyword/" + URLEncoder.encode(word, "utf-8");
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.select("div.searchbqppdiv").select("img");
                    for (int i = 0; i < elements.size(); ++ i) {
                        Emoticon e = new Emoticon();
                        e.id = i;
                        e.url = elements.get(i).attr("data-original");
                        e.type = Database.EmoticonType.SEARCH;
                        e.calcHash();
                        mDatabase.addEmoticon(e);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hiddenInputMothod();
                Intent intent = EmoticonListActivity.newIntent(getApplicationContext(), Database.EmoticonType.SEARCH, word);
                startActivity(intent);
            }
        }).start();
    }
}
