package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.SetGradeActivity;
import com.android.decipherstranger.activity.ShakeActivity.ShakeActivity;
import com.android.decipherstranger.activity.ShowMapActivity;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MyLocationData;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class DiscoverMainTabFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout friendsState;
    private RelativeLayout sharkItOff;
    private RelativeLayout nearby;
    private RelativeLayout showMapNearby;
    private RelativeLayout addFriendSetting;
    private Intent intent;
    private double mLatitude;
    private double mLongtitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_discover,container,false);
        friendsState = (RelativeLayout) view.findViewById(R.id.friends_state);
        sharkItOff = (RelativeLayout) view.findViewById(R.id.shark_it_off);
        nearby = (RelativeLayout) view.findViewById(R.id.nearby);
        showMapNearby = (RelativeLayout) view.findViewById(R.id.show_map_nearby);
        addFriendSetting = (RelativeLayout) view.findViewById(R.id.add_friend_settings);
        friendsState.setOnClickListener(this);
        sharkItOff.setOnClickListener(this);
        nearby.setOnClickListener(this);
        showMapNearby.setOnClickListener(this);
        addFriendSetting.setOnClickListener(this);
        resetBckColor();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.friends_state:
                friendsState.setBackgroundColor(getResources().getColor(R.color.silvery));
                goTo(0);
                break;
            case R.id.shark_it_off:
                sharkItOff.setBackgroundColor(getResources().getColor(R.color.silvery));
                goTo(1);
                break;
            case R.id.nearby:
                nearby.setBackgroundColor(getResources().getColor(R.color.silvery));
                goTo(2);
                break;
            case R.id.show_map_nearby:
                showMapNearby.setBackgroundColor(getResources().getColor(R.color.silvery));
                goTo(3);
                break;
            case R.id.add_friend_settings:
                goTo(4);
                break;
        }
    }

    private void resetBckColor() {

        friendsState.setBackgroundColor(getResources().getColor(R.color.black_blue));
        sharkItOff.setBackgroundColor(getResources().getColor(R.color.black_blue));
        nearby.setBackgroundColor(getResources().getColor(R.color.black_blue));
        showMapNearby.setBackgroundColor(getResources().getColor(R.color.black_blue));
    }

    private void goTo(int i) {
        switch (i){
            case 0:
//                intent = new Intent(getActivity(),ShowMapActivity.class);
//                startActivity(intent);
                break;
            case 1:
                intent = new Intent(getActivity(),ShakeActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getActivity(),NearbyListViewActivity.class);
                startActivity(intent);
                sendMsg();
                break;
            case 3:
                intent = new Intent(getActivity(),ShowMapActivity.class);
                startActivity(intent);
                sendMsg();
                break;
            case 4:
                intent = new Intent(getActivity(),SetGradeActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location){
            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude()).//
                            build();
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
        }
    }


    private void sendMsg(){
        if(NetworkService.getInstance().getIsConnected()) {
            String Msg = "type"+":"+Integer.toString(GlobalMsgUtils.msgNearBy)+":"+
                              "account"+":"+ MyStatic.UserAccount+":"+"latitude"+":"+mLatitude+":"+
                              "longtitude"+":"+mLongtitude;
            Log.v("aaaaa", Msg);
            NetworkService.getInstance().sendUpload(Msg);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Log.v("Login", "已经执行T（）方法");
        }
    }
}
