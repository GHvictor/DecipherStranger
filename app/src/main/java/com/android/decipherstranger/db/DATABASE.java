package com.android.decipherstranger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PengHaitao on 2015/2/24.
 */
public class DATABASE extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ds_user.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "user_tab";

    public DATABASE(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                "account    VARCHAR(50)     PRIMARY KEY," +
                "password   VARCHAR(50)     NOT NULL)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        DATABASE.this.onCreate(db);
    }
}