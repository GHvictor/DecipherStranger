package com.android.decipherstranger.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
 * @Date 2015/4/17 22:19
 * @e-mail 785351408@qq.com
 */
public class RecentContacts {

    private SQLiteDatabase db = null;
    private static final String TABLE_NAME = "recent_contacts";

    public RecentContacts(SQLiteDatabase db){
        this.db = db;
    }

    //  更新数据
    public ArrayList<Contacts> update(String account, String name, String photo, String message) {
        try {
            String insert = "insert into recent_contacts VALUES(?,?,?,?,datetime())";
            Object args[] = new Object[]{account,name,photo,message};
            this.db.execSQL(insert,args);
        }catch (Exception e) {
            String sql = "UPDATE recent_contacts SET username=?, userphoto=?, newest=?, contacts_time=datetime() WHERE account=?";
            Object args[] = new Object[]{name,photo,message,account};
            this.db.execSQL(sql,args);
        }
        String select = "select * from recent_contacts order by contacts_time";
        Cursor result = this.db.rawQuery(select, null);
        ArrayList<Contacts> all = new ArrayList<Contacts>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Contacts contacts = new Contacts();
            contacts.setAccount(result.getString(0));
            contacts.setUsername(result.getString(1));
            contacts.setPortrait(result.getString(2));
            contacts.setMessage(result.getString(3));
            contacts.setDatetime(result.getString(4));
            all.add(contacts);
        } this.db.close();
        return all;
    }

    //  列表刷新
    public ArrayList<Contacts> refresh() {        
        String sql = "select * from recent_contacts order by contacts_time";
        Cursor result = this.db.rawQuery(sql, null);
        ArrayList<Contacts> all = new ArrayList<Contacts>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Contacts contacts = new Contacts();
            contacts.setAccount(result.getString(0));
            contacts.setUsername(result.getString(1));
            contacts.setPortrait(result.getString(2));
            contacts.setMessage(result.getString(3));
            contacts.setDatetime(result.getString(4));
            all.add(contacts);
        } this.db.close();
        return all;
    }
}
