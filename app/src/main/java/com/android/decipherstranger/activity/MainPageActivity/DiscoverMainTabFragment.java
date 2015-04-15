package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.SetGradeActivity;
import com.android.decipherstranger.activity.ShakeActivity.ShakeActivity;
import com.android.decipherstranger.activity.ShowMapActivity;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class DiscoverMainTabFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout friendsState;
    private RelativeLayout sharkItOff;
    private RelativeLayout nearby;
    private RelativeLayout showMapNearby;
    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_discover,container,false);
        friendsState = (RelativeLayout) view.findViewById(R.id.friends_state);
        sharkItOff = (RelativeLayout) view.findViewById(R.id.shark_it_off);
        nearby = (RelativeLayout) view.findViewById(R.id.nearby);
        showMapNearby = (RelativeLayout) view.findViewById(R.id.show_map_nearby);
        friendsState.setOnClickListener(this);
        sharkItOff.setOnClickListener(this);
        nearby.setOnClickListener(this);
        showMapNearby.setOnClickListener(this);
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
                break;
            case 3:
                intent = new Intent(getActivity(),ShowMapActivity.class);
                startActivity(intent);
                break;
        }
    }
}
