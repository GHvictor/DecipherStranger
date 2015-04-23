package com.android.decipherstranger.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.entity.User;

import java.util.ArrayList;

/**
 *      へ　　　　　／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　 /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／      去吧！
 * 　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 *
 * @author penghaitao
 * @version V1.0
 * @Date 2015/4/23 21:31
 * @e-mail 785351408@qq.com
 */
public class ChatRecord {
    
    private SQLiteDatabase db = null;
    
    public ChatRecord(SQLiteDatabase db) {
        this.db = db;
    }
    
    /*
     * 添加聊天记录
     * @param String account(好友账号),int who(0/1 0为用户发给好友，1为好友发给用户) Sting message
     */
    public void insert(String account, int who, String message){
        String insert = "INSERT INTO chatrecord VALUES(?,?,?,datetime())";
        Object args[] = new Object[]{account,who,message};
        this.db.execSQL(insert,args);
        this.db.close();
    }
    
    /*
     * 获取聊天记录
     * @param String account
     * @return who,message,time
     */
    public ArrayList<Contacts> getInfo(String account) {
        String select = "SELECT ismine,message FROM chatrecord WHERE contacts=? ORDER BY chat_time";
        String args[] = new String[]{account};
        Cursor result = this.db.rawQuery(select, args);
        ArrayList<Contacts> all = new ArrayList<Contacts>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Contacts contacts = new Contacts();
            contacts.setWho(result.getInt(0));
            contacts.setMessage(result.getString(0));
            contacts.setDatetime(result.getString(0));
            all.add(contacts);
        } this.db.close();
        return all;
    }
}
