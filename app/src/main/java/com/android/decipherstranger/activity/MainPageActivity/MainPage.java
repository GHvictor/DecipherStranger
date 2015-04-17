package com.android.decipherstranger.activity.MainPageActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.decipherstranger.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class MainPage extends FragmentActivity implements OnClickListener{
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> fragmentPages;

    private TextView mChatTextView;
    private TextView mFriendsTextView;
    private TextView mDiscoverTextView;
    private TextView mSelected_page_name;
    private ImageView mChatIcon;
    private ImageView mFriendsIcon;
    private ImageView mDiscoverIcon;
    private RelativeLayout mChat;
    private RelativeLayout mFriendsList;
    private RelativeLayout mDiscover;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_page);

        initView();
        initEvent();
        setSelect(0);
    }

    private void initEvent() {
        mChat.setOnClickListener(this);
        mFriendsList.setOnClickListener(this);
        mDiscover.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat:
                setSelect(0);
                break;
            case R.id.friends_list:
                setSelect(1);
                break;
            case R.id.discover:
                setSelect(2);
                break;
        }
    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_page);
        mChatTextView = (TextView) findViewById(R.id.chat_text_view);
        mFriendsTextView = (TextView) findViewById(R.id.friends_text_view);
        mDiscoverTextView = (TextView) findViewById(R.id.discover_text_view);
        mChatIcon = (ImageView) findViewById(R.id.chat_icon);
        mFriendsIcon = (ImageView) findViewById(R.id.friends_icon);
        mDiscoverIcon = (ImageView) findViewById(R.id.discover_icon);
        mSelected_page_name = (TextView) findViewById(R.id.selected_page_name);
        mChat = (RelativeLayout) findViewById(R.id.chat);
        mFriendsList = (RelativeLayout) findViewById(R.id.friends_list);
        mDiscover = (RelativeLayout) findViewById(R.id.discover);

        fragmentPages = new ArrayList<Fragment>();

        RecentMainTabFragment recentMainTabFragment = new RecentMainTabFragment();
        FriendsMainTabFragment friendsMainTabFragment = new FriendsMainTabFragment();
        DiscoverMainTabFragment discoverMainTabFragment = new DiscoverMainTabFragment();

        fragmentPages.add(recentMainTabFragment);
        fragmentPages.add(friendsMainTabFragment);
        fragmentPages.add(discoverMainTabFragment);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int fragment) {
                return fragmentPages.get(fragment);
            }
            @Override
            public int getCount() {
                return fragmentPages.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                setTab(currentItem);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    //将图标和字改为暗色
    protected void resetTextView(){
        mChatTextView.setTextColor(Color.BLACK);
        mFriendsTextView.setTextColor(Color.BLACK);
        mDiscoverTextView.setTextColor(Color.BLACK);
        mChatIcon.setImageResource(R.drawable.chat_icon);
        mFriendsIcon.setImageResource(R.drawable.friends_list_icon);
        mDiscoverIcon.setImageResource(R.drawable.discover_icon);
    }

    public void setSelect(int select) {
        setTab(select);
        mViewPager.setCurrentItem(select);
    }
    public void setTab(int select){
        resetTextView();
        switch (select){
            case 0:
                mChatTextView.setTextColor(getResources().getColor(R.color.green));
                mChatIcon.setImageResource(R.drawable.chat_selected_icon);
                mSelected_page_name.setText(getResources().getString(R.string.chat));
                break;
            case 1:
                mFriendsTextView.setTextColor(getResources().getColor(R.color.green));
                mFriendsIcon.setImageResource(R.drawable.friends_list_selected_icon);
                mSelected_page_name.setText(getResources().getString(R.string.friend_list));
                break;
            case 2:
                mDiscoverTextView.setTextColor(getResources().getColor(R.color.green));
                mDiscoverIcon.setImageResource(R.drawable.discover_selected_icon);
                mSelected_page_name.setText(getResources().getString(R.string.discover));
                break;
        }
    }
}
