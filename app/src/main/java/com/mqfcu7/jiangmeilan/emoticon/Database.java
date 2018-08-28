package com.mqfcu7.jiangmeilan.emoticon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import org.jsoup.helper.StringUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "com.mqfcu7.jianmeilan.emoticon";
    private static final String TABLE_EMOTICON_SUITE = "emoticon_suite";
    private static final String TABLE_EMOTICON = "emoticon";
    private static final String TABLE_JOKE_SUITE = "joke_suite";

    private static final int DATABASE_VERSION = 1;

    private Context mContext;
    private Random mRandom = new Random();

    public static class EmoticonType {
        public static final int STAR = 1;
        public static final int SHOW = 2;
        public static final int ACGN = 3;
        public static final int FESTIVAL = 4;
        public static final int CUTE = 5;
        public static final int QUN = 6;
        public static final int COMIC = 7;
        public static final int WISH = 8;
    }

    public abstract class EmoticonSuiteColumns implements BaseColumns {
        public static final String HASH = "hash";
        public static final String TITLE = "title";
        public static final String DESCRIBE = "describe";
        public static final String IMAGE_NUM = "image_num";
        public static final String IMAGES_URL = "images_url";
        public static final String VISITED = "visited";
    }

    public abstract class EmoticonColumns implements BaseColumns {
        public static final String ID = "id";
        public static final String HASH = "hash";
        public static final String TYPE = "type";
        public static final String IMAGE_URL = "image_url";
        public static final String TIME = "time";
    }

    public abstract class JokeSuiteColumns implements BaseColumns {
        public static final String ID = "id";
        public static final String CONTENT = "content";
        public static final String USER_NAME = "user_name";
        public static final String USER_AVATAR = "user_avatar";
        public static final String GOOD = "good";
        public static final String BAD = "bad";
        public static final String COMMENT = "comment";
        public static final String TIME = "time";
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createEmoticonSuiteTable(db);
        createEmoticonsTable(db);
        createJokeSuiteTable(db);
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

    private void createEmoticonsTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EMOTICON + " ("
                + EmoticonColumns._ID + " integer primary key,"
                + EmoticonColumns.ID + " integer,"
                + EmoticonColumns.HASH + " integer,"
                + EmoticonColumns.TYPE + " integer,"
                + EmoticonColumns.IMAGE_URL + " text,"
                + EmoticonColumns.TIME + " integer"
                + ");");
    }

    private void createJokeSuiteTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_JOKE_SUITE + " ("
                + JokeSuiteColumns._ID + " integer primary key,"
                + JokeSuiteColumns.ID + " integer,"
                + JokeSuiteColumns.CONTENT + " text,"
                + JokeSuiteColumns.USER_NAME + " text,"
                + JokeSuiteColumns.USER_AVATAR + " text,"
                + JokeSuiteColumns.GOOD + " integer,"
                + JokeSuiteColumns.BAD + " integer,"
                + JokeSuiteColumns.COMMENT + " integer,"
                + JokeSuiteColumns.TIME + " text"
                + ");");
        db.execSQL("insert into " + TABLE_JOKE_SUITE + " values(0,28250836,'奉劝各位，玩手机时间不要太长，伤害很大。最好30分钟就能让眼睛休息下，把视线投向窗外，想一想为什么自己这么穷。','ka便利贴','http://wimg.spriteapp.cn/profile/large/2018/05/23/5b05050010600_mini.jpg',58,3,0,'2018-07-05 07:30:01');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long getNewEmoticonSuiteNum() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_EMOTICON_SUITE, EmoticonSuiteColumns.VISITED + "=0");
    }

    public long getEmoticonsNum(int type) {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, TABLE_EMOTICON, EmoticonColumns.TYPE + "=" + type);
    }

    public boolean isExistEmoticonSuite(final  EmoticonSuite emoticonSuite) {
        boolean result = false;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_EMOTICON_SUITE);
        qb.appendWhere(EmoticonSuiteColumns.HASH + "=" + emoticonSuite.hash);
        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            c = qb.query(db, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                result = true;
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    public boolean addEmoticonSuite(final EmoticonSuite emoticonSuite) {
        if (isExistEmoticonSuite(emoticonSuite)) return false;

        ContentValues values = new ContentValues();
        values.put(EmoticonSuiteColumns.HASH, emoticonSuite.hash);
        values.put(EmoticonSuiteColumns.TITLE, emoticonSuite.title);
        values.put(EmoticonSuiteColumns.DESCRIBE, emoticonSuite.describe);
        values.put(EmoticonSuiteColumns.IMAGE_NUM, emoticonSuite.images_url.size());
        values.put(EmoticonSuiteColumns.IMAGES_URL, serializeImagesUrl(emoticonSuite.images_url));
        values.put(EmoticonSuiteColumns.VISITED, 0);

        SQLiteDatabase db = getReadableDatabase();
        db.insert(TABLE_EMOTICON_SUITE, EmoticonSuiteColumns._ID, values);

        return true;
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

        updateVisitedEmoticonSuites(result);

        return result;
    }

    private void updateVisitedEmoticonSuites(final List<EmoticonSuite> emoticonSuites) {
        SQLiteDatabase db = getWritableDatabase();
        for (EmoticonSuite es : emoticonSuites) {
            ContentValues values = new ContentValues();
            values.put(EmoticonSuiteColumns.VISITED, 1);
            db.update(TABLE_EMOTICON_SUITE, values, EmoticonSuiteColumns._ID + "=" + es.id, null);
        }
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

    public JokeSuite getJokeSuite() {
        List<JokeSuite> list = getBatchJokeSuite();
        if (!list.isEmpty()) {
            return list.get(mRandom.nextInt(list.size()));
        }

        JokeSuite joke = new JokeSuite();
        joke.content = "奉劝各位，玩手机时间不要太长，伤害很大。最好30分钟就能让眼睛休息下，把视线投向窗外，想一想为什么自己这么穷。";
        joke.userName = "ka便利贴";
        joke.userAvatar = "http://wimg.spriteapp.cn/profile/large/2018/05/23/5b05050010600_mini.jpg";
        joke.good = 58;
        joke.bad = 3;
        joke.comment = 0;
        joke.time = "2018-07-05 07:30:01";
        return joke;
    }

    public List<JokeSuite> getBatchJokeSuite() {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_JOKE_SUITE);

        List<JokeSuite> result = new LinkedList<>();
        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            c = qb.query(db, null, null, null, null, null, null);
            while (c.moveToNext()) {
                result.add(parseJokeSuite(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    private JokeSuite parseJokeSuite(Cursor c) {
        JokeSuite joke = new JokeSuite();
        joke.id = c.getInt(c.getColumnIndex(JokeSuiteColumns.ID));
        joke.content = c.getString(c.getColumnIndex(JokeSuiteColumns.CONTENT));
        joke.userName = c.getString(c.getColumnIndex(JokeSuiteColumns.USER_NAME));
        joke.userAvatar = c.getString(c.getColumnIndex(JokeSuiteColumns.USER_AVATAR));
        joke.good = c.getInt(c.getColumnIndex(JokeSuiteColumns.GOOD));
        joke.bad = c.getInt(c.getColumnIndex(JokeSuiteColumns.BAD));
        joke.comment = c.getInt(c.getColumnIndex(JokeSuiteColumns.COMMENT));
        joke.time = c.getString(c.getColumnIndex(JokeSuiteColumns.TIME));
        return joke;
    }

    public boolean addEmoticon(final Emoticon emoticon) {
        if (isExistEmoticon(emoticon)) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(EmoticonColumns.ID, emoticon.id);
        values.put(EmoticonColumns.HASH, emoticon.hash);
        values.put(EmoticonColumns.TYPE, emoticon.type);
        values.put(EmoticonColumns.IMAGE_URL, emoticon.url);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EMOTICON, EmoticonColumns._ID, values);

        return true;
    }

    private boolean isExistEmoticon(final Emoticon emoticon) {
        boolean result = false;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_EMOTICON);
        qb.appendWhere(EmoticonColumns.HASH + "=" + emoticon.hash);
        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            c = qb.query(db, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                return true;
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    public List<Emoticon> getBatchEmoticons(int startID, int type, int max_num) {
        List<Emoticon> emoticons = new LinkedList<>();

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_EMOTICON);
        qb.appendWhere(EmoticonColumns._ID + ">" + startID
                + " and " + EmoticonColumns.TYPE + "=" + type);

        Cursor c = null;
        try {
            SQLiteDatabase db = getReadableDatabase();
            c = qb.query(db, null, null, null, null, null, null);
            while (c.moveToNext() && max_num > 0) {
                Emoticon emoticon = new Emoticon();
                emoticon.id = c.getInt(c.getColumnIndex(EmoticonColumns._ID));
                emoticon.hash = c.getInt(c.getColumnIndex(EmoticonColumns.HASH));
                emoticon.type = c.getInt(c.getColumnIndex(EmoticonColumns.TYPE));
                emoticon.url = c.getString(c.getColumnIndex(EmoticonColumns.IMAGE_URL));
                emoticons.add(emoticon);
                max_num --;
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return emoticons;
    }
}
