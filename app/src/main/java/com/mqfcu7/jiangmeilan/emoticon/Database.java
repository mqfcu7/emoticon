package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "com.mqfcu7.jianmeilan.emoticon";
    private static final String TABLE_EMOTICON_SUITE = "emoticon_suite";
    private static final String TABLE_AVATARS = "emoticon";

    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private Random mRandom = new Random();

    public abstract class EmoticonSuiteColumns implements BaseColumns {
        public static final String HASH = "hash";
        public static final String TITLE = "title";
        public static final String DESCRIBE = "describe";
        public static final String IMAGE_NUM = "image_num";
        public static final String IMAGES_URL = "images_url";
        public static final String VISITED = "visited";
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEmoticonSuiteTable(db);
    }

    private void createEmoticonSuiteTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EMOTICON_SUITE + " ("
                + EmoticonSuiteColumns._ID + " integer primary key,"
                + EmoticonSuiteColumns.HASH + " integer,"
                + EmoticonSuiteColumns.TITLE + " text,"
                + EmoticonSuiteColumns.DESCRIBE + " text,"
                + EmoticonSuiteColumns.IMAGE_NUM + " integer,"
                + EmoticonSuiteColumns.IMAGES_URL + " text,"
                + EmoticonSuiteColumns.VISITED + " integer"
                + ");");

        db.execSQL("insert into " + TABLE_EMOTICON_SUITE + " values(0,0,'猫和老鼠动图表情包','',4,'http://wx3.sinaimg.cn/bmiddle/005A0PMely1ftpg4dssvjg306o06ok3b.gif,http://wx1.sinaimg.cn/bmiddle/005A0PMely1ftpg4ef3wrg306o06ok7d.gif,http://wx2.sinaimg.cn/bmiddle/005A0PMely1ftpg4ev9glg306o06on9f.gif,http://wx4.sinaimg.cn/bmiddle/005A0PMely1ftpg4f5ytvg306o06on9o.gif',0);");
        db.execSQL("insert into " + TABLE_EMOTICON_SUITE + " values(1,0,'权律二撩妹撩汉表情包','',9,'http://wx3.sinaimg.cn/bmiddle/006B12oSgy1fu4yk6mctwj30hs0hf766.jpg,http://wx2.sinaimg.cn/bmiddle/006B12oSgy1fu4yk8b2wkj30hs0h8jt4.jpg,http://wx2.sinaimg.cn/bmiddle/006B12oSgy1fu4yk7l8g2j30hs0hmq62.jpg,http://wx4.sinaimg.cn/bmiddle/006B12oSgy1fu4yk96e1xj30hs0h376d.jpg,http://wx2.sinaimg.cn/bmiddle/006B12oSgy1fu4yka1j5jj30hs0hsgnr.jpg,http://wx1.sinaimg.cn/bmiddle/006B12oSgy1fu4ykaolm5j30hs0hegna.jpg,http://wx1.sinaimg.cn/bmiddle/006B12oSgy1fu4ykd3723j30hs0h8ta5.jpg,http://wx1.sinaimg.cn/bmiddle/006B12oSgy1fu4ykcgrpbj30hs0hs77g.jpg,http://wx3.sinaimg.cn/bmiddle/006B12oSgy1fu4ykbdxlpj30hs0hg0uq.jpg',0);");
        db.execSQL("insert into " + TABLE_EMOTICON_SUITE + " values(2,0,'表情名','',4,'http://qq.yh31.com/tp/zjbq/201712091833198306.gif,http://qq.yh31.com/tp/zjbq/201711301425316225.gif,http://qq.yh31.com/tp/zjbq/201806272307043903.gif,http://qq.yh31.com/tp/zjbq/201805222259592777.gif',0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<EmoticonSuite> getBatchEmoticonSuites(int num) {
        List<EmoticonSuite> result = new ArrayList<>();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_EMOTICON_SUITE);
        qb.appendWhere(EmoticonSuiteColumns.VISITED + "=0");

        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            c = qb.query(db, null, null, null, null, null, null);
            while (c.moveToNext() && num > 0) {
                result.add(buildEmoticonSuite(c));
                num --;
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }

        // updateVisitedAvatarSuites

        return result;
    }

    private EmoticonSuite buildEmoticonSuite(Cursor c) {
        EmoticonSuite es = new EmoticonSuite();
        es.id = c.getInt(c.getColumnIndex(EmoticonSuiteColumns._ID));
        es.hash = c.getInt(c.getColumnIndex(EmoticonSuiteColumns.HASH));
        es.title = c.getString(c.getColumnIndex(EmoticonSuiteColumns.TITLE));
        es.describe = c.getString(c.getColumnIndex(EmoticonSuiteColumns.DESCRIBE));
        es.images_url = parseImagesUrl(c.getString(c.getColumnIndex(EmoticonSuiteColumns.IMAGES_URL)));
        return es;
    }

    private List<String> parseImagesUrl(String images_url) {
        List<String> result = new ArrayList<>();

        String[] items = images_url.split(",");
        for (int i = 0; i < items.length; ++ i) {
            result.add(items[i]);
        }

        return result;
    }

    private String serializeImagesUrl(final List<String> images_url) {
        return StringUtil.join(images_url, ",");
    }
}
