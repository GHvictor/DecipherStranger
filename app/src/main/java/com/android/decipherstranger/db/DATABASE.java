package com.android.decipherstranger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.decipherstranger.util.MyStatic;

/**
 * Created by PengHaitao on 2015/2/24.
 */
public class DATABASE extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ds_user.db";
    private static final int DATABASE_VERSION = 1;
    
    public DATABASE(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db){
        this.CreateUserTab(db);
        this.CreateRecentContactsTab(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        this.DropUserTab(db);
        this.DropRecentContactsTab(db);
        DATABASE.this.onCreate(db);
    }
    
    private void CreateUserTab(SQLiteDatabase db) {
        String sql = "CREATE TABLE user_tab (" +
                "account    VARCHAR(50)     PRIMARY KEY," +
                "password   VARCHAR(50)     NOT NULL)";
        db.execSQL(sql);
    }
    
    private void CreateRecentContactsTab(SQLiteDatabase db) {
        String sql = "CREATE TABLE `recent_contacts` (" +
                "`account` VARCHAR(20) DEFAULT NULL," +
                "`username` VARCHAR(20) DEFAULT NULL," +
                "`userphoto` VARCHAR(20) DEFAULT NULL," +
                "`newest` VARCHAR(30) DEFAULT NULL," +
                "`contacts_time` DATETIME DEFAULT NULL)";
        db.execSQL(sql);
    }
    
    private void DropUserTab(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS user_tab";
        db.execSQL(sql);
    }
    
    private void DropRecentContactsTab(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS `recent_contacts`;";
        db.execSQL(sql);
    }
}