package com.android.decipherstranger.activity.SubpageActivity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.GameOneActivity.WelcomeRspActivity;
import com.android.decipherstranger.entity.NearbyUserInfo;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.view.HandyTextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/3/19 0019.
 */
public class ShowMapActivity extends BaseActivity {
    private MyApplication application = null;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private Boolean isFristIn = true;
    private ProgressDialog progressDialog = null;
    private double mLatitude;
    private double mLongtitude;
    private ArrayList<NearbyUserInfo>nearbyInfo;
    private ShowMapBroadcastReceiver receiver = null;

    private Context context;
    private BitmapDescriptor mMarker;
    private RelativeLayout mMarkerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        application = (MyApplication) getApplication();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.show_map);
        //初始化定位
        initLocation();
        sendMsg();
        showMapBroadcas();
        initView();
        this.context = this;
    }

    private void initMarker() {
         addOverlays(nearbyInfo);
         mMarkerlayout = (RelativeLayout) findViewById(R.id.nearby_info);
    }

    //添加覆盖物
    private void addOverlays(List<NearbyUserInfo> infos) {
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        if (!infos.isEmpty()){
            for(NearbyUserInfo nearByUserInfo:infos){
                //经纬度
                latLng = new LatLng(nearByUserInfo.getLatitude(),nearByUserInfo.getLongtitude());
                //图标
                mMarker = BitmapDescriptorFactory.fromBitmap(nearByUserInfo.getImgId());
                options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
                marker = (Marker) mBaiduMap.addOverlay(options);
                Bundle bundle = new Bundle();
                bundle.putSerializable("nearByUserInfo", nearByUserInfo) ;
                marker.setExtraInfo(bundle);
            }
        }
    }

    private void markerOnclick(){
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                NearbyUserInfo nearByUserInfo = (NearbyUserInfo) extraInfo.getSerializable("nearByUserInfo");
                ImageView nearByUserPhoto = (ImageView) mMarkerlayout.findViewById(R.id.nearby_user_photo);
                HandyTextView nearByUserName = (HandyTextView) mMarkerlayout.findViewById(R.id.nearby_user_name);
                HandyTextView nearByUserSex = (HandyTextView) mMarkerlayout.findViewById(R.id.nearby_user_sex);
                HandyTextView distance = (HandyTextView) mMarkerlayout.findViewById(R.id.distance);

                nearByUserPhoto.setImageBitmap(nearByUserInfo.getImgId());
                nearByUserName.setText(nearByUserInfo.getUserName());
                nearByUserSex.setText(nearByUserInfo.getSex());
                distance.setText(nearByUserInfo.getDistance());
                mMarkerlayout.setVisibility(View.VISIBLE);
                return true;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMarkerlayout.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        Button addFriend = (Button) mMarkerlayout.findViewById(R.id.add_friend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ShowMapActivity.this, WelcomeRspActivity.class);
                startActivity(it);
            }
        });
        Button back = (Button) mMarkerlayout.findViewById(R.id.nearby_info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMarkerlayout.setVisibility(View.GONE);
            }
        });
    }
    private void initLocation() {
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);

    }

    private void initView(){
        mMapView = (MapView)findViewById(R.id.user_map_view);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
    }

    @Override
    protected void onDestroy() {
        super.unregisterReceiver(ShowMapActivity.this.receiver);
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }
    //定位到我的位置
    private void CenterToMyLocation(){
        LatLng latLng = new LatLng(mLatitude,mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation location){
            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude()).//
                            build();
            mBaiduMap.setMyLocationData(data);
//            MyLocationConfiguration config = new MyLocationConfiguration(NORMAL,arg1,arg2);
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            if(isFristIn){
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
                isFristIn = false;
                Toast.makeText(context,location.getAddrStr(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendMsg() {
        this.progressDialog = new ProgressDialog(ShowMapActivity.this);
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

    private void showMapBroadcas() {
        //动态方式注册广播接收者
        this.receiver = new ShowMapBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.NEARBY");
        this.registerReceiver(receiver, filter);
    }

    public class ShowMapBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.android.decipherstranger.NEARBY")) {
                if(intent.getBooleanExtra("reResult", false)) {
                    //Todo 数据接收
                    NearbyUserInfo info = new NearbyUserInfo();
                    info.setUserAccount(intent.getStringExtra("reUserAccount"));
                    info.setUserName(intent.getStringExtra("reUserName"));
                    info.setImgId(ChangeUtils.toBitmap(intent.getStringExtra("rePhoto")));
                    info.setSex(intent.getStringExtra("reGender"));
                    info.setLatitude(Double.parseDouble(intent.getStringExtra("reLatitude")));
                    info.setLongtitude(Double.parseDouble(intent.getStringExtra("reLongtitude")));
                    info.setDistance(intent.getStringExtra("reDistance"));
                    nearbyInfo.add(info);
                }else if(intent.getBooleanExtra("isfinish", false)){
                    //Todo 数据处理
                    //覆盖物相关
                    initMarker();
                    markerOnclick();
                    progressDialog.dismiss();
                }else{
                    //Todo 没有人
                    Toast.makeText(context, "竟然没有人:)", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Toast.makeText(ShowMapActivity.this, "附近好像还没有人哦( ⊙ o ⊙ )！啦啦啦~~", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){// 防止连续两次返回键
            //这你写你的返回处理
            Intent intent = new Intent(ShowMapActivity.this,MainActivity.class);
            startActivity(intent);
            ShowMapActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
