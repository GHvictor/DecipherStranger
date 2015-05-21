package com.android.decipherstranger.activity.SubpageActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.adapter.NearbyListViewAdapter;
import com.android.decipherstranger.entity.NearbyUserInfo;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.DialogManager;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

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

    private MyApplication application = null;
    private ListView nearbyListView;
    private NearbyListViewAdapter adapter;
    private ArrayList<NearbyUserInfo>nearbyUserInfos = new ArrayList<>();
    private NearbyBroadcastReceiver receiver = null;
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private ProgressDialog progressDialog = null;
    private double mLatitude;
    private double mLongtitude;
    private boolean isFristIn=true;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        application = (MyApplication) getApplication();
        setContentView(R.layout.activity_nearby_listview);
        initLocation();
        nearbyBroadcas();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
    }

    @Override
    protected  void onStop(){
        super.onStop();
        mLocationClient.stop();
    }


    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location){
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            if (isFristIn){
                sendMsg();
            }
        }
    }
    private void initView() {
        nearbyListView = (ListView) findViewById(R.id.nearby_list_view);
        nearbyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NearbyListViewActivity.this,NearbyInfoActivity.class);
                intent.putExtra("account",nearbyUserInfos.get(position).getUserAccount());
                intent.putExtra("photo",nearbyUserInfos.get(position).getImgId());
                intent.putExtra("name",nearbyUserInfos.get(position).getUserName());
                intent.putExtra("sex",nearbyUserInfos.get(position).getSex());
                startActivity(intent);
            }
        });
        showList(nearbyUserInfos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        super.unregisterReceiver(NearbyListViewActivity.this.receiver);
    }


    private void showList(ArrayList<NearbyUserInfo>nearbyUserInfos) {
        nearbyListView = (ListView) findViewById(R.id.nearby_list_view);
        adapter = new NearbyListViewAdapter(nearbyUserInfos,this);
        nearbyListView.setAdapter(adapter);
    }

    private void sendMsg() {
        this.progressDialog = new ProgressDialog(NearbyListViewActivity.this);
        //创建我们的进度条
        progressDialog.setMessage("正在搜寻附近的人");
        progressDialog.onStart();
        progressDialog.show();
        if (NetworkService.getInstance().getIsConnected()) {
            String Msg = "type" + ":" + Integer.toString(GlobalMsgUtils.msgNearBy) + ":" +
                    "account" + ":" + application.getAccount() + ":" + "latitude" + ":" + mLatitude + ":" +
                    "longtitude" + ":" + mLongtitude;
            Log.v("aaaaa", Msg);
            NetworkService.getInstance().sendUpload(Msg);
        } else {
            NetworkService.getInstance().closeConnection();
            Log.v("Login", "已经执行T（）方法");
        }
    }
    private void nearbyBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new NearbyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.NEARBY");
        this.registerReceiver(receiver, filter);
    }

    public class NearbyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.NEARBY")) {
                if(intent.getBooleanExtra("reResult", false)) {
                    NearbyUserInfo info = new NearbyUserInfo();
                    info.setUserAccount(intent.getStringExtra("reAccount"));
                    info.setUserName(intent.getStringExtra("reName"));
                    info.setImgId(ChangeUtils.toBitmap(intent.getStringExtra("rePhoto")));
                    info.setSex(intent.getIntExtra("reGender", 1) + "");
                    info.setLatitude(Double.parseDouble(intent.getStringExtra("reLatitude")));
                    info.setLongtitude(Double.parseDouble(intent.getStringExtra("reLongtitude")));
                    info.setDistance(intent.getStringExtra("reDistance"));
                    nearbyUserInfos.add(info);
                    System.out.println("+++++++++" + info.getDistance());
                    //Todo 数据接收
                }else if(intent.getBooleanExtra("isfinish", false)){
                    //Todo 数据处理
                    initView();
                    progressDialog.dismiss();
                }else{
                    //Todo 没有人
                    Toast.makeText(context, "竟然没有人:)", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(NearbyListViewActivity.this, "附近好像还没有人哦( ⊙ o ⊙ )！啦啦啦~~", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
