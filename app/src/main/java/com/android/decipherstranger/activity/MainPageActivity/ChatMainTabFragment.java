package com.android.decipherstranger.activity.MainPageActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.ChatData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class ChatMainTabFragment extends Fragment implements ChatListView.IRefreshListener{
    ArrayList<ChatData> recentChatData;
    private ChatListViewAdapter adapter;
    private ChatListView listView;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_chat,container,false);
        setData();
        showList(recentChatData);
        return view;
    }

    private void showList(ArrayList<ChatData> recentChatData){
            listView = (ChatListView)view.findViewById(R.id.chat_view_list);
            listView.setInterface(this);
            adapter = new ChatListViewAdapter(recentChatData,this.getActivity());
            listView.setAdapter(adapter);
            adapter.onDateChange(recentChatData);
    }
    public void setData(){
        recentChatData = new ArrayList<ChatData>();
        recentChatData.add(new ChatData("找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
        recentChatData.add(new ChatData("可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
        recentChatData.add(new ChatData("可惜没如果","倘若那天，把该说的话好好说，该体谅的不执著","PM 2:50",R.drawable.recent_chat_user_photo3));
        recentChatData.add(new ChatData("情歌王","爱你，不是因为你的很美而已","2-18",R.drawable.recent_chat_user_photo4));
    }
   public void setRefreshData(){
           ChatData chatData = new ChatData("思念是一种病","我想我的思念是一种病,久久不能痊愈","3-1",R.drawable.user_photo1);
           recentChatData.add(0,chatData);
   }
    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //获取最新数据
                setRefreshData();
                //通知界面显示
                showList(recentChatData);
                //通知listView数据刷新完毕
                listView.reFreshComplete();
            }
        },2000);
    }
}
