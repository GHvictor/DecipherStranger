package com.android.decipherstranger.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.MainPageActivity.RecentListView;
import com.android.decipherstranger.activity.MainPageActivity.RecentListViewAdapter;
import com.android.decipherstranger.entity.RecentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PengHaitao on 2015/2/12.
 */
public class MainActivity extends Activity implements RecentListView.IRefreshListener {

    private Intent intent = null;
    private Button button1 = null;
    private Button button2 = null;
    private List<RecentData> recentChatData;
    private RecentListViewAdapter adapter;
    private RecentListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = new RecentListView(this);
//        listView = (RecentListView)findViewById(R.id.recent_view_list);
//        setData();
//        showList(setData());
//        initView();
    }

//    private void initView(){
//
//        this.button1 = (Button)super.findViewById(R.id.main_button);
//        this.button1.setOnClickListener(new btn1_OnClickListenerImpl());
//        this.button2 = (Button)super.findViewById(R.id.main_button3);
//        this.button2.setOnClickListener(new btn2_OnclickListenerImpl());
//    }

    @Override
    public void onRefresh() {

    }

//
//    private class btn1_OnClickListenerImpl implements View.OnClickListener {
//        @Override
//        public void onClick(View view){
//            intent = new Intent(MainActivity.this,ShowMapActivity.class);
//            startActivity(intent);
//        }
//    }
//
//    private class btn2_OnclickListenerImpl implements View.OnClickListener{
//        @Override
//        public void onClick(View view){
//            intent = new Intent(MainActivity.this, ShakeActivity.class);
//            startActivity(intent);
//        }
//    }
    private void showList(List<RecentData>recentChatData){
//        if (adapter ==null){
//            listView.setInterface(this);
//            adapter = new ChatListViewAdapter(recentChatData,this);
//            listView.setAdapter(adapter);
//        }else{
//            adapter.onDateChange(recentChatData);
//        }
    }
//    private List<RecentData> setData(){
//        recentChatData = new ArrayList<RecentData>();
//        recentChatData.add(new RecentData("找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
//        recentChatData.add(new RecentData("可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
//        recentChatData.add(new RecentData("可惜没如果","倘若那天，把该说的话好好说，该体谅的不执著","PM 2:50",R.drawable.recent_chat_user_photo3));
//        recentChatData.add(new RecentData("情歌王","爱你，不是因为你的很美而已","2-18",R.drawable.recent_chat_user_photo4));
//        recentChatData.add(new RecentData("找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
//        recentChatData.add(new RecentData("可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
//        recentChatData.add(new RecentData("可惜没如果","倘若那天，把该说的话好好说，该体谅的不执著","PM 2:50",R.drawable.recent_chat_user_photo3));
//        recentChatData.add(new RecentData("情歌王","爱你，不是因为你的很美而已","2-18",R.drawable.recent_chat_user_photo4));
//        recentChatData.add(new RecentData("找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
//        recentChatData.add(new RecentData("可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
//        recentChatData.add(new RecentData("可惜没如果","倘若那天，把该说的话好好说，该体谅的不执著","PM 2:50",R.drawable.recent_chat_user_photo3));
//        recentChatData.add(new RecentData("情歌王","爱你，不是因为你的很美而已","2-18",R.drawable.recent_chat_user_photo4));
//        recentChatData.add(new RecentData("找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
//        recentChatData.add(new RecentData("可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
//        recentChatData.add(new RecentData("可惜没如果","倘若那天，把该说的话好好说，该体谅的不执著","PM 2:50",R.drawable.recent_chat_user_photo3));
//        recentChatData.add(new RecentData("情歌王","爱你，不是因为你的很美而已","2-18",R.drawable.recent_chat_user_photo4));
//        recentChatData.add(new RecentData("找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
//        recentChatData.add(new RecentData("可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
//        recentChatData.add(new RecentData("可惜没如果","倘若那天，把该说的话好好说，该体谅的不执著","PM 2:50",R.drawable.recent_chat_user_photo3));
//        recentChatData.add(new RecentData("情歌王","爱你，不是因为你的很美而已","2-18",R.drawable.recent_chat_user_photo4));
//        return recentChatData;
//    }
//    private List<RecentData> setRefreshData(){
//        recentChatData.add(0,new RecentData("找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
//        recentChatData.add(1,new RecentData("可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
//        return recentChatData;
//    }
}
