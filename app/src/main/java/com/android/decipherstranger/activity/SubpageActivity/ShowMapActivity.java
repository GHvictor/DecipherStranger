package com.android.decipherstranger.activity.SubpageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.WelcomeRspActivity;
import com.android.decipherstranger.entity.NearbyUserInfo;
import com.android.decipherstranger.util.MyStatic;
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

import java.util.List;

/**
 * Created by Administrator on 2015/3/19 0019.
 */
public class ShowMapActivity extends Activity {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    //定位相关
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private Boolean isFristIn = true;
    private double mLatitude;
    private double mLongtitude;

    private Context context;
    private BitmapDescriptor mMarker;
    private RelativeLayout mMarkerlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.show_map);
        initView();
        this.context = this;
        //初始化定位
        initLocation();
        //覆盖物相关
        initMarker();
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                NearbyUserInfo nearByUserInfo = (NearbyUserInfo) extraInfo.getSerializable("nearByUserInfo");
                ImageView nearByUserPhoto = (ImageView) mMarkerlayout.findViewById(R.id.nearby_user_photo);
                HandyTextView nearByUserName = (HandyTextView) mMarkerlayout.findViewById(R.id.nearby_user_name);
                HandyTextView nearByUserSex = (HandyTextView) mMarkerlayout.findViewById(R.id.nearby_user_sex);
                HandyTextView distance = (HandyTextView) mMarkerlayout.findViewById(R.id.distance);

                nearByUserPhoto.setImageResource(nearByUserInfo.getImgId());
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
                Intent it = new Intent(ShowMapActivity.this,WelcomeRspActivity.class);
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

    private void initMarker() {
         addOverlays(NearbyUserInfo.infos);
         mMarkerlayout = (RelativeLayout) findViewById(R.id.nearby_info);
    }

    //添加覆盖物
    private void addOverlays(List<NearbyUserInfo> infos) {
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for(NearbyUserInfo nearByUserInfo:infos){
            //经纬度
            latLng = new LatLng(nearByUserInfo.getLatitude(),nearByUserInfo.getLongtitude());
            //图标
            mMarker = BitmapDescriptorFactory.fromResource(nearByUserInfo.getImgId());
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle bundle = new Bundle();
            bundle.putSerializable("nearByUserInfo", nearByUserInfo) ;
            marker.setExtraInfo(bundle);
        }
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

    private void showMapBroadcas() {
        //动态方式注册广播接收者
        ShowMapBroadcastReceiver receiver = new ShowMapBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.NEARBY");
        this.registerReceiver(receiver, filter);
    }

    public class ShowMapBroadcastReceiver extends BroadcastReceiver {
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
