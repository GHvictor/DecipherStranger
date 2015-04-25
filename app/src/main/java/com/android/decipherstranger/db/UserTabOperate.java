package com.android.decipherstranger.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.decipherstranger.entity.User;

import java.util.ArrayList;

/**
 * Created by PengHaitao on 2015/2/24.
 */
public class UserTabOperate {

    private SQLiteDatabase db = null;
    private static final String TABLE_NAME = "user_tab";

    public UserTabOperate(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(String account, String password){
        String sql = "INSERT INTO " + TABLE_NAME + " (account,password) VALUES(?,?)";
        Object args[] = new Object[]{account,password};
        this.db.execSQL(sql,args);
        this.db.close();
    }

    public void update(String account, String password){
        String sql = "UPDATE " + TABLE_NAME + " SET password=? WHERE account=?";
        Object args[] = new Object[]{password,account};
        this.db.execSQL(sql,args);
        this.db.close();
    }

    public User userTabInfo (String account){

        String U_account = "";
        String U_password = "";

        User user = new User();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE account = ?";
        String args[] = new String[]{account};
        Cursor result = this.db.rawQuery(sql, args);

        if (result.moveToNext()){
            U_account = result.getString(0);
            U_password = result.getString(1);
        }
        user.setAccount(U_account);
        user.setPassword(U_password);
        this.db.close();
        return user;
    }

    public ArrayList<String> accountInfo (){

        ArrayList<String> all = new ArrayList<String>();
        
        String sql = "SELECT account FROM " + TABLE_NAME;
        Cursor result = this.db.rawQuery(sql, null);

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            all.add(result.getString(0));
        }
        this.db.close();
        return all;
    }
    
    /*
     * 删除指定联系人记录
     */    
    public void delete(String account) {
        String delete = "delete from user_tab where contacts=?";
        String args[] = new String[]{account};
        this.db.execSQL(delete,args);
        this.db.close();
    }

    /*
     * 清空所有记录
     */
    public void clear() {
        String clear = "DELETE FROM user_tab";
        this.db.execSQL(clear);
        this.db.close();
    }
    
}