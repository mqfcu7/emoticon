package com.mqfcu7.jiangmeilan.emoticon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

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
    }

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createHotEmoticonTable(db);
    }

    private void createHotEmoticonTable(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_EMOTICON_SUITE + " ("
                + EmoticonSuiteColumns._ID + " integer primary key,"
                + EmoticonSuiteColumns.HASH + " integer,"
                + EmoticonSuiteColumns.TITLE + " text,"
                + EmoticonSuiteColumns.DESCRIBE + " text,"
                + EmoticonSuiteColumns.IMAGE_NUM + " integer,"
                + EmoticonSuiteColumns.IMAGES_URL + " text"
                + ");");

        db.execSQL("insert into " + TABLE_EMOTICON_SUITE + " values(0,0,'你道歉 VS 你女朋友道歉','',3,'http://wx1.sinaimg.cn/bmiddle/006ewLaAly1fp7t5x3w3bj308c0acdg5.jpg,http://wx2.sinaimg.cn/bmiddle/006ewLaAly1fp7t5wrjmhj307b0a3t91.jpg,http://wx1.sinaimg.cn/bmiddle/006ewLaAly1fp7t6jsnutg30at0atdtw.gif')");
        db.execSQL("insert into " + TABLE_EMOTICON_SUITE + " values(1,0,'权律二撩妹撩汉表情包','',9,'http://wx3.sinaimg.cn/bmiddle/006B12oSgy1fu4yk6mctwj30hs0hf766.jpg,http://wx2.sinaimg.cn/bmiddle/006B12oSgy1fu4yk8b2wkj30hs0h8jt4.jpg,http://wx2.sinaimg.cn/bmiddle/006B12oSgy1fu4yk7l8g2j30hs0hmq62.jpg,http://wx4.sinaimg.cn/bmiddle/006B12oSgy1fu4yk96e1xj30hs0h376d.jpg,http://wx2.sinaimg.cn/bmiddle/006B12oSgy1fu4yka1j5jj30hs0hsgnr.jpg,http://wx1.sinaimg.cn/bmiddle/006B12oSgy1fu4ykaolm5j30hs0hegna.jpg,http://wx1.sinaimg.cn/bmiddle/006B12oSgy1fu4ykd3723j30hs0h8ta5.jpg,http://wx1.sinaimg.cn/bmiddle/006B12oSgy1fu4ykcgrpbj30hs0hs77g.jpg,http://wx3.sinaimg.cn/bmiddle/006B12oSgy1fu4ykbdxlpj30hs0hg0uq.jpg')");
        db.execSQL("insert into " + TABLE_EMOTICON_SUITE + " values(2,0,'表情名','',4,'http://qq.yh31.com/tp/zjbq/201712091833198306.gif,http://qq.yh31.com/tp/zjbq/201711301425316225.gif,http://qq.yh31.com/tp/zjbq/201806272307043903.gif,http://qq.yh31.com/tp/zjbq/201805222259592777.gif')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
