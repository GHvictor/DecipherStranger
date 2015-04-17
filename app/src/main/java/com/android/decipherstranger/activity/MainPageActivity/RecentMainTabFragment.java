package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.widget.AdapterView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.RecentData;

import java.util.ArrayList;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class RecentMainTabFragment extends Fragment implements RecentListView.IRefreshListener{
    ArrayList<RecentData> recentChatData;
    private RecentListViewAdapter adapter;
    private RecentListView listView;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_recent,container,false);
        setData();
        showList(recentChatData);
        itemClick();
        return view;
    }

    private void itemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userAccount = recentChatData.get(position-1).getRecentUserAccount();
                String userName = recentChatData.get(position-1).getRecentUserName();
                int userPhotoId = recentChatData.get(position-1).getRecentUserPhotoId();
                Intent intent = new Intent(getActivity(),ChatMsgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userAccount",userAccount);
                bundle.putString("userName",userName);
                bundle.putInt("userPhotoId",userPhotoId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showList(ArrayList<RecentData> recentChatData){
            listView = (RecentListView)view.findViewById(R.id.recent_view_list);
            listView.setInterface(this);
            adapter = new RecentListViewAdapter(recentChatData,this.getActivity());
            listView.setAdapter(adapter);
            adapter.onDateChange(recentChatData);
    }
    public void setData(){
        recentChatData = new ArrayList<RecentData>();
        recentChatData.add(new RecentData("2220","找个地方埋了吧","我们都在怀念过去，失去才懂得珍惜","昨天",R.drawable.recent_chat_user_photo1));
        recentChatData.add(new RecentData("338","可以了","走不到的路就算了，我们永远停在这了","AM 8:50",R.drawable.recent_chat_user_photo2));
        recentChatData.add(new RecentData("224","可惜没如果","倘若那天，把该说的话好好说，该体谅的不执著","PM 2:50",R.drawable.recent_chat_user_photo3));
        recentChatData.add(new RecentData("556","情歌王","爱你，不是因为你的很美而已","2-18",R.drawable.recent_chat_user_photo4));
    }
   public void setRefreshData(){
           RecentData chatData = new RecentData("119","思念是一种病","我想我的思念是一种病,久久不能痊愈","3-1",R.drawable.user_photo1);
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
