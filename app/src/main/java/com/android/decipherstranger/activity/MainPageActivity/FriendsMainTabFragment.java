package com.android.decipherstranger.activity.MainPageActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.decipherstranger.R;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class FriendsMainTabFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main_friend_list,container,false);
    }
}
