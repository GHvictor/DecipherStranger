package com.android.decipherstranger.activity.MainPageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @Date 2015/5/13 16:27
 * @e-mail 785351408@qq.com
 */
public class ConversationPageActivity extends BaseActivity {

    private ListView dataList = null;
    private SQLiteOpenHelper helper = null;
    private SimpleAdapter simpleAdapter = null;
    private ArrayList<Map<String, Object>> list = null;
    private ConversationBroadcastReceiver receiver= null;

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
        registerBroadcas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(receiver);
    }

    private void init() {
        this.list = new ArrayList<Map<String, Object>>();
        this.dataList = (ListView) super.findViewById(R.id.listView);
        this.dataList.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    private void setData() {
        this.helper = new DATABASE(this);
        ConversationList conversationList = new ConversationList(helper.getReadableDatabase());
        this.list.addAll(conversationList.selectAll());

        this.simpleAdapter = new SimpleAdapter(this,
                this.list,
                R.layout.data_conversation_list,
                new String[] {MyStatic.CONVERSATION_PORTRAIT, MyStatic.CONVERSATION_NAME, MyStatic.CONVERSATION_MESSAGE, MyStatic.CONVERSATION_TIME, MyStatic.CONVERSATION_COUNT, MyStatic.CONVERSATION_IMAGE},
                new int[] {R.id.conversationPortrait, R.id.conversationName, R.id.conversationMessage, R.id.conversationTime, R.id.count, R.id.countImage}
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

    private void registerBroadcas() {
        //动态方式注册广播接收者
        IntentFilter filter = new IntentFilter();
        this.receiver = new ConversationBroadcastReceiver();
        filter.addAction(MyStatic.CONVERSATION_BOARD);
        this.registerReceiver(receiver, filter);
    }

    public class ConversationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean flag = false;
            Map<String, Object> map = null;
            String type = intent.getStringExtra(MyStatic.CONVERSATION_TYPE);
            String account = intent.getStringExtra(MyStatic.CONVERSATION_ACCOUNT);
            String message = intent.getStringExtra(MyStatic.CONVERSATION_MESSAGE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for(int i = 0;i < list.size(); ++ i){
                if (list.get(i).get(MyStatic.CONVERSATION_ACCOUNT).equals(account)){
                    map = list.get(i);
                    list.remove(i);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                ConversationList loadConversationList = new ConversationList(helper.getReadableDatabase());
                list.addAll(loadConversationList.selectAll());
                map = list.get(0);
                list.remove(0);
            }
            if (message != null) {
                ConversationList saveConversationList = new ConversationList(helper.getWritableDatabase());
                saveConversationList.setMessage(account, message);
                map.put(MyStatic.CONVERSATION_MESSAGE,  message);
                map.put(MyStatic.CONVERSATION_TIME, dateFormat.format(new java.util.Date()));
            }
            switch (type) {
                case "Update":
                    int sum = 0;
                    String count = (String) map.get(MyStatic.CONVERSATION_COUNT);
                    if (count.equals("")) {
                        sum = 1;
                    } else {
                        sum = Integer.parseInt(count) + 1;
                    }
                    map.put(MyStatic.CONVERSATION_COUNT, String.valueOf(sum));
                    map.put(MyStatic.CONVERSATION_IMAGE, R.drawable.badge_ifaux);
                    break;
                case "Default":
                    map.put(MyStatic.CONVERSATION_COUNT, "");
                    map.put(MyStatic.CONVERSATION_IMAGE, null);
                    break;
                    
            }
            list.add(0,map);
            simpleAdapter.notifyDataSetChanged();
            dataList.setAdapter(simpleAdapter);
        }
    }

}
