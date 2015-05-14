package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.db.ConversationList;
import com.android.decipherstranger.util.MyStatic;

import java.util.ArrayList;
import java.util.List;
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
 * @Date 2015/5/13 16:27
 * @e-mail 785351408@qq.com
 */
public class ConversationPageActivity extends BaseActivity {

    private ListView dataList = null;
    private ArrayList<Map<String, Object>> list = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_conversation);
        this.init();
        this.setData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("### ABCD onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("### ABCD onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("### ABCD onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("### ABCD onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        super.onDestroy();
        System.out.println("### ABCD onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("### ABCD onDestroy");
    }
    
    private void init() {
        this.list = new ArrayList<Map<String, Object>>();
        this.dataList = (ListView) super.findViewById(R.id.listView);
        this.dataList.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    private void setData() {
        SQLiteOpenHelper helper = new DATABASE(this);
        ConversationList conversationList = new ConversationList(helper.getReadableDatabase());
        this.list.addAll(conversationList.selectAll());

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                this.list,
                R.layout.data_conversation_list,
                new String[] {MyStatic.CONVERSATION_PORTRAIT, MyStatic.CONVERSATION_NAME, MyStatic.CONVERSATION_MESSAGE, MyStatic.CONVERSATION_TIME},
                new int[] {R.id.conversationPortrait, R.id.conversationName, R.id.conversationMessage, R.id.conversationTime}
        );
        /*实现ViewBinder()这个接口*/
        simpleAdapter.setViewBinder(new ViewBinderImpl());
        this.dataList.setAdapter(simpleAdapter);
        /*动态跟新ListView*/
        simpleAdapter.notifyDataSetChanged();
    }
    
    private class ViewBinderImpl implements SimpleAdapter.ViewBinder {
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            // TODO Auto-generated method stub
            if(view instanceof ImageView && data instanceof Bitmap){
                ImageView i = (ImageView)view;
                i.setImageBitmap((Bitmap) data);
                return true;
            }
            return false;
        }
    }
    
    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            
            String userAccount = (String) list.get(position).get(MyStatic.CONVERSATION_ACCOUNT);
            String userName = (String) list.get(position).get(MyStatic.CONVERSATION_NAME);
            Bitmap userPhoto = (Bitmap) list.get(position).get(MyStatic.CONVERSATION_PORTRAIT);

            Bundle bundle = new Bundle();
            bundle.putString("userName",userName);
            bundle.putString("userAccount", userAccount);
            bundle.putParcelable("userPhoto", userPhoto);
            
            Intent intent = new Intent(ConversationPageActivity.this, ChatMsgActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    
}
