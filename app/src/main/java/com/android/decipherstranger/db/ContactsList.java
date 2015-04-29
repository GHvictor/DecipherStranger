package com.android.decipherstranger.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.entity.User;

import java.util.ArrayList;

/**
 * へ　　　　　／|
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
 * @Date 2015/4/25 17:54
 * @e-mail 785351408@qq.com
 */
public class ContactsList {
    
    private SQLiteDatabase db = null;
    
    public ContactsList(SQLiteDatabase db) {
        this.db = db;
    }

    /*
     * 添加联系人
     * @param User user
     */
    public void insert(User user){
        String insert = "INSERT INTO contacts_list VALUES(?,?,?,?,?,?,?,?)";
        Object args[] = new Object[]{user.getAccount(),user.getUsername(),user.getRemark(),user.getPortrait(),
                user.getUserSex(),user.getBirth(),user.getEmail(),user.getPhone()};
        this.db.execSQL(insert,args);
        this.db.close();
    }
    
    /*
     * 获取联系人列表
     * @param String account
     */
    public ArrayList<User> getInfo(){
        String select = "SELECT * FROM contacts_list";
        Cursor result = this.db.rawQuery(select, null);
        ArrayList<User> all = new ArrayList<User>();
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            User contacts = new User();
            contacts.setAccount(result.getString(0));
            contacts.setUsername(result.getString(1));
            contacts.setRemark(result.getString(2));
            byte[] in = result.getBlob(3);
            contacts.setPortrait(BitmapFactory.decodeByteArray(in, 0, in.length));
            contacts.setUserSex(result.getString(4));
            contacts.setEmail(result.getString(5));
            contacts.setBirth(result.getString(6));
            contacts.setPhone(result.getString(7));
            all.add(contacts);
        } this.db.close();
        return all;
    }
    
    /*
     * 删除指定联系人
     */
    public void delete(String account) {
        String delete = "delete from contacts_list where account=?";
        String args[] = new String[]{account};
        this.db.execSQL(delete,args);
        this.db.close();
    }
    
    /*
     * 清空所有联系人
     */
    public void clear() {
        String clear = "DELETE FROM contacts_list";
        this.db.execSQL(clear);
        this.db.close();
    }
    
}
