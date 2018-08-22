package com.mqfcu7.jiangmeilan.emoticon;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class EmoticonSuite extends Object {
    public int id;
    public int hash;
    public String url;
    public String title;
    public String describe;
    public List<String> images_url;

    public EmoticonSuite() {
        images_url = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "id:" + id
                + ", hash:" + hash
                + ", url:" + url
                + ", title:" + title
                + ", describe:" + describe
                + ", images_url:" + StringUtil.join(images_url, " "); }

    public void calcHash() {
        hash = StringUtil.join(images_url, ",").hashCode();
    }
}
