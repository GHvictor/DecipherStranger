package com.android.decipherstranger.activity.SubpageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.adapter.NearbyListViewAdapter;
import com.android.decipherstranger.entity.NearbyUserInfo;
import com.android.decipherstranger.util.MyStatic;

import java.util.ArrayList;

/**
 * *
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
 * Created by WangXin on 2015/4/6 0006.
 */
public class NearbyListViewActivity extends BaseActivity {

    private ListView nearbyListView;
    private NearbyListViewAdapter adapter;
    private ArrayList<NearbyUserInfo>nearbyUserInfos;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_listview);
        initView();
    }

    private void initView() {
        nearbyListView = (ListView) findViewById(R.id.nearby_list_view);
        setData();
        showList(nearbyUserInfos);
    }

    private void setData() {
        nearbyUserInfos = new ArrayList<NearbyUserInfo>();
        for (int i = 0;i<5;i++){
            NearbyUserInfo nearbyUserInfo = new NearbyUserInfo();
            nearbyUserInfo.setImgId(R.drawable.user_photo5);
            nearbyUserInfo.setUserName("如果的事");
            nearbyUserInfo.setSex("女");
            nearbyUserInfo.setDistance("1公里");
            nearbyUserInfos.add(nearbyUserInfo);
            NearbyUserInfo nearbyUserInfo1 = new NearbyUserInfo();
            nearbyUserInfo1.setImgId(R.drawable.user_photo7);
            nearbyUserInfo1.setUserName("十年");
            nearbyUserInfo1.setSex("男");
            nearbyUserInfo1.setDistance("0.5公里");
            nearbyUserInfos.add(nearbyUserInfo1);
        }
    }

    private void showList(ArrayList<NearbyUserInfo>nearbyUserInfos) {
        nearbyListView = (ListView) findViewById(R.id.nearby_list_view);
        adapter = new NearbyListViewAdapter(nearbyUserInfos,this);
        nearbyListView.setAdapter(adapter);
    }

    private void nearbyBroadcas() {
        //动态方式注册广播接收者
        NearbyBroadcastReceiver receiver = new NearbyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.NEARBY");
        this.registerReceiver(receiver, filter);
    }

    public class NearbyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.NEARBY")) {
                if(intent.getStringExtra("result").equals(MyStatic.resultTrue)) {

                }
                else{
                    Toast.makeText(context, "没人？！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
