package com.android.decipherstranger.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

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
 * @Date 2015/4/13 13:33
 * @e-mail 785351408@qq.com
 */
public class SharedPreferencesUtils {

    private Context context = null;
    private String FILENAME = null;

    public SharedPreferencesUtils(Context context, String FILENAME) {
        this.context = context;
        this.FILENAME = FILENAME;
    }

    //  新增或更新数据
    public void set(String KEY, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_APPEND);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(KEY, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(KEY, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(KEY, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(KEY, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(KEY, (Long) object);
        } else {
            editor.putString(KEY, object.toString());
        }
        editor.commit();
    }

    //  查询并获取数据
    public Object get(String KEY, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(KEY, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(KEY, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(KEY, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(KEY, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(KEY, (Long) defaultObject);
        }
        return null;
    }

    // 删除指定KEY的数据
    public void delete(String KEY) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(KEY);
        editor.commit();
    }

    //  清空所有数据
    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    //  判断KEY是否存在
    public boolean isContain(String key) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    //  取出所有数据
    public Map<String, ?> getAll() {
        SharedPreferences sp = context.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return sp.getAll();
    }
}