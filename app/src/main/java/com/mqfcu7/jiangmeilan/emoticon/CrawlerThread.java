package com.mqfcu7.jiangmeilan.emoticon;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrawlerThread extends Thread {
    private static final int MAX_EMOTICON_NUM = 200;
    private static CrawlerThread sCrawlerThread = new CrawlerThread();

    public static String sUA = "";
    private Database mDatabase;

    private Random mRandom = new Random();
    private int mHotEmoticonPageNum = 1;

    private CrawlerThread() {

    }

    public static CrawlerThread getInstance() {
        return sCrawlerThread;
    }

    public void setDatabase(Database db) {
        mDatabase = db;
    }

    public static void setUA(String ua) {
        sUA = ua;
    }

    @Override
    public void run() {
        while (true) {
                crawlHotEmoticonSuites();
            try {
                sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void crawlHotEmoticonSuites() {
        if (mDatabase.getNewEmoticonSuiteNum() > 100) {
            return;
        }

        List<EmoticonSuite> suites = getHotPageEmoticon(getHotPageUrl());
        for (EmoticonSuite emoticon : suites) {
            mDatabase.addEmoticonSuite(emoticon);
        }

        mHotEmoticonPageNum ++;
    }

    private String getHotPageUrl() {
        if (mHotEmoticonPageNum == 1) {
            return "https://fabiaoqing.com/bqb/lists/type/hot.html";
        } else {
            return "https://fabiaoqing.com/bqb/lists/type/hot/page/" + mHotEmoticonPageNum + ".html";
        }
    }

    private List<EmoticonSuite> getHotPageEmoticon(String pageUrl) {
        List<EmoticonSuite> suites = new ArrayList<>();
        try {
            Document rootDoc = Jsoup.connect(pageUrl).get();
            Elements rootElements = rootDoc.select("a.bqba");
            for (int i = 0; i < rootElements.size(); ++ i) {
                EmoticonSuite suite = new EmoticonSuite();
                suite.title = rootElements.get(i).select("h1").text();
                String url = rootElements.get(i).attr("href");
                Document doc = Jsoup.connect("https://fabiaoqing.com" + url).get();
                Elements elements = doc.select("div.bqppdiv1");
                for (int j = 0; j < elements.size(); ++ j) {
                    String imageUrl = elements.get(j).select("img").attr("data-original");
                    suite.images_url.add(imageUrl);
                }
                suite.calcHash();
                suites.add(suite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return suites;
    }
}
