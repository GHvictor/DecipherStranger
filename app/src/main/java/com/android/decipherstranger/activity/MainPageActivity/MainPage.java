package com.android.decipherstranger.activity.MainPageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.view.BadgeView;

import java.util.ArrayList;
import java.util.Collections;
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

    private BadgeView badgeView ;
    //未读消息的个数
    private LinearLayout unReadMessageCount;
    //友请求的个数
    private LinearLayout newFriendsCount;
    //新消息的总数
    private int unReadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_page);
        friendBroadcas();
        //ArrayList<User> serverContactData = new ArrayList<>();
        //this.getIntent().getSerializableExtra("friend");
        //Toast.makeText(this,,Toast.LENGTH_LONG).show();
        initView();
        initEvent();
        setSelect(0);
        setUnReadMessage(7,unReadMessageCount);
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
                networkRequest();
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
        unReadMessageCount = (LinearLayout) findViewById(R.id.unread_message_count);
        newFriendsCount = (LinearLayout) findViewById(R.id.new_friends_count);

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
    //未读消息提醒
    public void setUnReadMessage(int unReadMessageNum,LinearLayout unReadMessageType){
        badgeView = new BadgeView(this,unReadMessageType);
        badgeView.setText(String.valueOf(unReadMessageNum));
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        if (unReadMessageNum != 0){
            badgeView.show();
        }else {
            badgeView.hide();
        }
    }

    private void networkRequest(){
        if(NetworkService.getInstance().getIsConnected()) {
            String userInfo = "type"+":"+Integer.toString(GlobalMsgUtils.msgFriendList)+":"+"account"+":"+ MyStatic.UserAccount;
            Log.v("aaaaa", userInfo);
            NetworkService.getInstance().sendUpload(userInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Log.v("Login", "已经执行T（）方法");
        }
    }

    private void friendBroadcas() {
        //动态方式注册广播接收者
        FriendBroadcastReceiver receiver = new FriendBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.FRIEND");
        this.registerReceiver(receiver, filter);
    }

    public class FriendBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("com.android.decipherstranger.FRIEND")){
                if(intent.getBooleanExtra("reResult", false)) {
                    ArrayList<User> serverContactData = new ArrayList<>();

                    //Toast.makeText(context, "aaaa", Toast.LENGTH_SHORT).show();
                    //serverContactData = ((ArrayList) intent.getSerializableExtra("friend"));
                    //Toast.makeText(context, serverContactData.get(0).getAccount().toString(),Toast.LENGTH_LONG).show();
                    int sum = intent.getIntExtra("sum", 0);
                    for (int i = 0; i < sum; i++) {
                        String s[] = new String[5];
                        s = intent.getStringExtra(Integer.toString(i)).split(":");
                        Bitmap bitmap = ChangeUtils.toBitmap(s[2]);
                        User user = new User();
                        user.setAccount(s[0]);
                        user.setUsername(s[1]);
                        user.setPortrait(bitmap);
                        serverContactData.add(user);
                        System.out.println("qqqqqqqq" + serverContactData.get(i).getAccount());
                    }
                    Toast.makeText(context, "成功了", Toast.LENGTH_LONG).show();
                /*
                for(int i=0;i<serverContactData.size();i++){
                    SourceDateList.add(serverContactData.get(i));
                }
                if (adapter == null){
                    Collections.sort(SourceDateList, pinyinComparator);
                    adapter = new SortAdapter(getActivity(), SourceDateList);
                    sortListView.setAdapter(adapter);
                }else{
                    Collections.sort(SourceDateList, pinyinComparator);
                    adapter.updateListView(SourceDateList);
                }*/
                }
                else{
                    Toast.makeText(context, "第一次？", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(context, "bbbbbb", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
