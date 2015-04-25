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
        this.CreateChatRecord(db);
        this.CreateContactsList(db);
        this.CreateRecentContactsTab(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){
        this.DropUserTab(db);
        this.DropChatRecord(db);
        this.DropContactsList(db);
        this.DropRecentContactsTab(db);
        DATABASE.this.onCreate(db);
    }
    
    //  登陆
    private void CreateUserTab(SQLiteDatabase db) {
        String sql = "CREATE TABLE user_tab (" +
                "account    VARCHAR(50)     PRIMARY KEY," +
                "password   VARCHAR(50)     NOT NULL)";
        db.execSQL(sql);
    }
    
    //  最近联系人
    private void CreateRecentContactsTab(SQLiteDatabase db) {
        String sql = "CREATE TABLE `recent_contacts` (" +
                "  `account` varchar(20) DEFAULT NULL," +
                "  `username` varchar(20) DEFAULT NULL," +
                "  `userphoto` mediumblob," +
                "  `newest` varchar(30) DEFAULT NULL," +
                "  `contacts_time` datetime DEFAULT NULL)" ;
        db.execSQL(sql);
    }
    
    //  聊天记录
    private void CreateChatRecord(SQLiteDatabase db){
        String sql = "CREATE TABLE `chatrecord` (" +
                "  `contacts` varchar(20) NOT NULL," +
                "  `ismine` tinyint(1) DEFAULT NULL," +
                "  `message` varchar(20) DEFAULT NULL," +
                "  `chat_time` datetime DEFAULT NULL," +
                "  `timeLen` varbinary(5) DEFAULT NULL," +
                "  PRIMARY KEY (`contacts`)" +
                ")";
        db.execSQL(sql);
    }
    
    private void CreateContactsList(SQLiteDatabase db) {
        String sql = "CREATE TABLE `contacts_list` (" +
                "  `account` varbinary(20) NOT NULL," +
                "  `username` varbinary(20) DEFAULT NULL," +
                "  `remark` varbinary(20) DEFAULT NULL," +
                "  `portrait` mediumblob," +
                "  `sex` varbinary(10) DEFAULT NULL," +
                "  `email` varbinary(20) DEFAULT NULL," +
                "  `birth` date DEFAULT NULL," +
                "  `phone` varbinary(15) DEFAULT NULL," +
                "  PRIMARY KEY (`account`)" +
                ") ";
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
    
    private void DropChatRecord(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS `chatrecord`";
        db.execSQL(sql);
    }
    
    private void DropContactsList(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS `contacts_list`";
        db.execSQL(sql);
    }
}