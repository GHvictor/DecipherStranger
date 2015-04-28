package com.android.decipherstranger.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.entity.User;

import java.io.ByteArrayOutputStream;
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
    public ArrayList<Contacts> update(String account, String name, Bitmap bitmap, String message) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        try {
            String insert = "insert into recent_contacts VALUES(?,?,?,?,datetime())";
            Object args[] = new Object[]{account,name,os.toByteArray(),message};
            this.db.execSQL(insert,args);
        }catch (Exception e) {
            String sql = "UPDATE recent_contacts SET username=?, userphoto=?, newest=?, contacts_time=datetime() WHERE account=?";
            Object args[] = new Object[]{name,os.toByteArray(),message,account};
            this.db.execSQL(sql,args);
        }
        String select = "select * from recent_contacts order by contacts_time";
        Cursor result = this.db.rawQuery(select, null);
        ArrayList<Contacts> all = new ArrayList<Contacts>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Contacts user = new Contacts();
            user.setAccount(result.getString(0));
            user.setUsername(result.getString(1));
            byte[] in = result.getBlob(2);
            user.setPortrait(BitmapFactory.decodeByteArray(in, 0, in.length));
            user.setMessage(result.getString(3));
            user.setDatetime(result.getString(4));
            all.add(user);
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
            byte[] in = result.getBlob(2);
            contacts.setPortrait(BitmapFactory.decodeByteArray(in, 0, in.length));
            contacts.setMessage(result.getString(3));
            contacts.setDatetime(result.getString(4));
            all.add(contacts);
        } this.db.close();
        return all;
    }

    /*
     * 删除指定联系人记录
     * @param String account
     */
    public void delete(String account) {
        String delete = "delete from recent_contacts where account=?";
        String args[] = new String[]{account};
        this.db.execSQL(delete,args);
        this.db.close();
    }

    /*
     * 清空最近联系人列表
     */
    public void clear() {
        String clear = "DELETE FROM recent_contacts";
        this.db.execSQL(clear);
        this.db.close();
    }
}
