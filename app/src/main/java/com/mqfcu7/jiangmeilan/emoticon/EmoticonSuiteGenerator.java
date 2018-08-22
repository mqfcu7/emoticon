package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

public class EmoticonSuiteGenerator extends Object {
    private Database mDatabase;

    public EmoticonSuiteGenerator(Context context) {
        mDatabase = new Database(context);
    }

    public EmoticonSuite randomEmoticonSuite() {
        List<EmoticonSuite> suites = mDatabase.getBatchEmoticonSuites(1);
        if (!suites.isEmpty()) {
            return suites.get(0);
        }

        EmoticonSuite s = new EmoticonSuite();
        s.title = "你道歉 VS 你女朋友道歉";
        s.images_url = new LinkedList<>();
        s.images_url.add("http://wx3.sinaimg.cn/bmiddle/005A0PMely1ftpg4dssvjg306o06ok3b.gif");
        s.images_url.add("http://wx1.sinaimg.cn/bmiddle/005A0PMely1ftpg4ef3wrg306o06ok7d.gif");
        s.images_url.add("http://wx2.sinaimg.cn/bmiddle/005A0PMely1ftpg4ev9glg306o06on9f.gif");
        s.images_url.add("http://wx4.sinaimg.cn/bmiddle/005A0PMely1ftpg4f5ytvg306o06on9o.gif");
        return s;
    }


}
