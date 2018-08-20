package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class EmoticonDetailActivity extends AppCompatActivity {
    private static final String EXTRA_EMOTICON_URL =
            "com.mqfcu7.jiangmeilan.emoticon.image_url";

    public static Intent newIntent(Context context, String imageUrl) {
        Intent intent = new Intent(context, EmoticonDetailActivity.class);
        intent.putExtra(EXTRA_EMOTICON_URL, imageUrl);
        return intent;
    }


}
