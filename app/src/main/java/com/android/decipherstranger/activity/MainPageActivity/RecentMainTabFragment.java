package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;
import android.widget.AdapterView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.db.RecentContacts;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.util.ChangeUtils;

import java.util.ArrayList;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class RecentMainTabFragment extends Fragment implements RecentListView.IRefreshListener{
    private RecentListViewAdapter adapter;
    private RecentListView listView;
    private View view;
    private ArrayList<Contacts>recentChatLog;
    private SQLiteOpenHelper helper;
    private RecentContacts mRecentContacts;
    private ChangeUtils mChangeUtils = new ChangeUtils();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_recent,container,false);
        this.helper = new DATABASE(getActivity());
        setData();
        showList(recentChatLog);
        itemClick();
        return view;
    }

    private void itemClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userAccount = recentChatLog.get(position).getAccount();
                String userName = recentChatLog.get(position).getUsername();
                Bitmap userPhoto = recentChatLog.get(position).getPortrait();
                Intent intent = new Intent(getActivity(),ChatMsgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userAccount",userAccount);
                bundle.putString("userName", userName);
                bundle.putParcelable("userPhoto", userPhoto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showList(ArrayList<Contacts> recentChatData){
        if(adapter == null) {
            listView = (RecentListView) view.findViewById(R.id.recent_view_list);
            listView.setInterface(this);
            adapter = new RecentListViewAdapter(recentChatData, this.getActivity());
            listView.setAdapter(adapter);
        }else {
            adapter.onDateChange(recentChatData);
        }
    }
    public void setData(){
        recentChatLog = new ArrayList<Contacts>();
        this.mRecentContacts = new RecentContacts(this.helper.getReadableDatabase());
        recentChatLog = mRecentContacts.refresh();
    }
   public void setRefreshData(){

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
                showList(recentChatLog);
                //通知listView数据刷新完毕
                listView.reFreshComplete();
            }
        },2000);
    }
}
