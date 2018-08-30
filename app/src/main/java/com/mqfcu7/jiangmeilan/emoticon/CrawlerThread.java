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
    private static final int CYCLE_NUM = 5;
    private static CrawlerThread sCrawlerThread = new CrawlerThread();

    public static String sUA = "";
    private Database mDatabase;

    private Random mRandom = new Random();
    private int mHotEmoticonPageNum = 1;

    private boolean mIsStopStarCrawler = false;
    private int mStarStartId = 0;
    private boolean mIsStopShowCrawler = false;
    private int mShowStartId = 0;
    private boolean mIsStopAcgnCrawler = false;
    private int mAcgnStartId = 0;
    private boolean mIsStopFestivalCrawler = false;
    private int mFestivalStartId = 0;
    private boolean mIsStopCuteCrawler = false;
    private int mCuteStartId = 0;
    private int mQunEmoticonPageNum = 1;
    private boolean mIsStopQunCrawler = false;
    private boolean mIsStopComicCrawler = false;
    private int mComicStartId = 0;
    private boolean mIsStopWishCrawler = false;

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

                crawlStarEmoticons();
                crawlShowEmoticons();
                crawlAcgnEmoticons();
                crawlFestivalEmoticons();
                crawlCuteEmoticons();
                crawlQunEmoticons();
                crawlComicEmoticons();
                crawlWishEmoticons();
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

    private void crawlStarEmoticons() {
        if (mIsStopStarCrawler) {
            return;
        }

        mIsStopStarCrawler = crawlSogouEmoticons(
                "http://pic.sogou.com/pic/emo/classify.jsp?id=183&from=emoclassify_tab",
                Database.EmoticonType.STAR, mStarStartId);
        mStarStartId += CYCLE_NUM;
    }

    private void crawlShowEmoticons() {
        if (mIsStopShowCrawler) {
            return;
        }

        mIsStopShowCrawler = crawlSogouEmoticons(
                "http://pic.sogou.com/pic/emo/classify.jsp?id=188&from=emoclassify_tab",
                Database.EmoticonType.SHOW, mShowStartId);
        mShowStartId += CYCLE_NUM;
    }

    private void crawlAcgnEmoticons() {
        if (mIsStopAcgnCrawler) {
            return;
        }

        mIsStopAcgnCrawler = crawlSogouEmoticons(
                "http://pic.sogou.com/pic/emo/classify.jsp?id=170&from=emoclassify_tab",
                Database.EmoticonType.ACGN, mAcgnStartId);
        mAcgnStartId += CYCLE_NUM;
    }

    private void crawlFestivalEmoticons() {
        if (mIsStopFestivalCrawler) {
            return;
        }

        mIsStopFestivalCrawler = crawlSogouEmoticons(
                "http://pic.sogou.com/pic/emo/classify.jsp?id=177&from=emoclassify_tab",
                Database.EmoticonType.FESTIVAL, mFestivalStartId);
        mFestivalStartId += CYCLE_NUM;
    }

    private void crawlCuteEmoticons() {
        if (mIsStopCuteCrawler) {
            return;
        }

        mIsStopCuteCrawler = crawlSogouEmoticons(
                "http://pic.sogou.com/pic/emo/classify.jsp?id=150&from=emoclassify_tab",
                Database.EmoticonType.CUTE, mCuteStartId);
        mCuteStartId += CYCLE_NUM;
    }

    private void crawlQunEmoticons() {
        if (mIsStopQunCrawler) {
            return;
        }

        boolean isRepeat = false;
        List<Emoticon> emoticons = getPageEmoticon(getQunPageUrl(), Database.EmoticonType.QUN);
        for (Emoticon e : emoticons) {
            isRepeat |= mDatabase.addEmoticon(e);
        }
        mIsStopQunCrawler = isRepeat && mDatabase.getEmoticonsNum(Database.EmoticonType.QUN) > MAX_EMOTICON_NUM;

        mQunEmoticonPageNum ++;
    }

    private String getQunPageUrl() {
        if (mQunEmoticonPageNum == 1) {
            return "https://fabiaoqing.com/bqb/lists/type/qunliao.html";
        } else {
            return "https://fabiaoqing.com/bqb/lists/type/qunliao/page/" + mQunEmoticonPageNum + ".html";
        }
    }

    private List<Emoticon> getPageEmoticon(String pageUrl, int type) {
        List<Emoticon> emoticons = new ArrayList<>();
        try {
            Document rootDoc = Jsoup.connect(pageUrl).get();
            Elements rootElements = rootDoc.select("a.bqba");
            for (int i = 0; i < rootElements.size(); ++ i) {
                String url = rootElements.get(i).attr("href");
                Document doc = Jsoup.connect("https://fabiaoqing.com" + url).get();
                Elements elements = doc.select("div.bqppdiv1");
                for (int j = 0; j < elements.size(); ++ j) {
                    Emoticon emoticon = new Emoticon();
                    emoticon.id = i;
                    emoticon.type = type;
                    emoticon.url = elements.get(j).select("img").attr("data-original");
                    emoticon.calcHash();
                    emoticons.add(emoticon);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emoticons;
    }

    private void crawlComicEmoticons() {
        if (mIsStopComicCrawler) {
            return;
        }

        mIsStopComicCrawler = crawlSogouEmoticons(
                "http://pic.sogou.com/pic/emo/classify.jsp?id=175&from=emoclassify_tab",
                Database.EmoticonType.COMIC, mComicStartId);
        mComicStartId += CYCLE_NUM;
    }

    private void crawlWishEmoticons() {
        if (mIsStopWishCrawler) {
            return;
        }

        List<Emoticon> emoticons = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://qq.yh31.com/zf/").get();
            Elements elements = doc.select("div.pe_u_thumb");
            for (int i = 0; i < elements.size(); ++ i) {
                Emoticon e = new Emoticon();
                String imageUrl = elements.get(i).select("img").attr("src");
                e.id = i;
                e.type = Database.EmoticonType.WISH;
                e.url = "http://qq.yh31.com" + imageUrl;
                e.calcHash();
                emoticons.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Emoticon e : emoticons) {
            mDatabase.addEmoticon(e);
        }

        mIsStopWishCrawler = true;
    }

    private boolean crawlSogouEmoticons(String url, int type, int startId) {
        boolean isRepeat = false;
        List<Emoticon> emoticons = getSogouEmoticons(url, type, startId, CYCLE_NUM);
        for (Emoticon e : emoticons) {
            isRepeat |= mDatabase.addEmoticon(e);
        }
        return isRepeat && mDatabase.getEmoticonsNum(type) > MAX_EMOTICON_NUM;
    }

    private List<Emoticon> getSogouEmoticons(String pageUrl, int type, int startId, int num) {
        List<Emoticon> emoticons = new ArrayList<>();
        try {
            Document rootDoc = Jsoup.connect(pageUrl).get();
            Elements rootElements = rootDoc.select("section.recall-module");
            for (int i = startId; i < rootElements.size() && i < startId + num; ++ i) {
                String url = rootElements.get(i).select("div.module-tit-recall").select("a.emo-tit-recall").attr("href");
                int id = Integer.parseInt(url.substring(url.indexOf("id=") + 3, url.indexOf("&", url.indexOf("id="))));
                Document doc = Jsoup.connect("http://pic.sogou.com" + url).get();
                Elements elements = doc.select("section.stkrelate").select("img");
                for (int j = 0; j < elements.size(); ++ j) {
                    Emoticon e = new Emoticon();
                    e.id = id;
                    e.type = type;
                    e.url = elements.get(j).attr("rsrc");
                    e.calcHash();
                    emoticons.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emoticons;
    }
}
