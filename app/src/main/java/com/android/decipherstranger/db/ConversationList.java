package com.android.decipherstranger.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.MyStatic;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
public class ConversationList {

    private SQLiteDatabase db = null;

    public ConversationList(SQLiteDatabase db){
        this.db = db;
    }

    //  更新数据    在每次 获取了新的好友时 和 产生聊天框时  调用
    public void create(String account, String name, Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        try {
            String sql = "insert into recent_contacts VALUES(?,?,?,null,null)";
            Object args[] = new Object[]{account,name,os.toByteArray()};
            this.db.execSQL(sql,args);
        }catch (Exception e) {
            String sql = "UPDATE recent_contacts SET username=?, userphoto=? WHERE account=?";
            Object args[] = new Object[]{name,os.toByteArray(),account};
            this.db.execSQL(sql,args);
        }
        this.db.close();
    }

    //  刷新最新会话  在发送或接收到新的信息时调用 若信息为语音 则 message=“” 若信息为图片 则 message=“【图片】”
    public void setMessage(String account, String message) {
        String sql = "UPDATE recent_contacts SET newest=?, contacts_time=datetime() WHERE account=?";
        Object args[] = new Object[]{message,account};
        this.db.execSQL(sql, args);
        this.db.close();
    }

    public ArrayList<Map<String, Object>> selectAll (){
        ArrayList<Map<String, Object>> all = new ArrayList<Map<String, Object>>();
        String sql = "SELECT * FROM 'recent_contacts' WHERE contacts_time is not null order BY contacts_time DESC";
        Cursor result = this.db.rawQuery(sql, null);
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(MyStatic.CONVERSATION_ACCOUNT,  result.getString(0));
            map.put(MyStatic.CONVERSATION_NAME, result.getString(1));
            map.put(MyStatic.CONVERSATION_PORTRAIT, R.drawable.mypic);
            map.put(MyStatic.CONVERSATION_MESSAGE, result.getString(3));
            map.put(MyStatic.CONVERSATION_TIME, result.getString(4));
            all.add(map);
        }
        this.db.close();
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
