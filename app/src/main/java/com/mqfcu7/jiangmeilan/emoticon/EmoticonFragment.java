package com.mqfcu7.jiangmeilan.emoticon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class EmoticonFragment extends Fragment {
    private static final String ARG_IMAGE_URL = "image_url";

    private String mImageUrl;

    public static EmoticonFragment newInstance(String imgUrl) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE_URL, imgUrl);

        EmoticonFragment fragment = new EmoticonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageUrl = getArguments().getString(ARG_IMAGE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_emoticon, container, false);

        ImageView imageView = v.findViewById(R.id.detail_image_view);
        Glide.with(getContext())
                .load(mImageUrl)
                .into(imageView);

        return v;
    }
}
