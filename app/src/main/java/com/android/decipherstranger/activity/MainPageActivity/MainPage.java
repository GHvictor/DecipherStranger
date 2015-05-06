package com.android.decipherstranger.activity.MainPageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.Network.NetworkService;
import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.FriendInfoActivity;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.CharacterParser;
import com.android.decipherstranger.util.GlobalMsgUtils;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.PinyinComparator;
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
    //通讯录List
    private ListView contactListView;
    private SortAdapter contactAdapter;
    //汉字转换成拼音的类
    private CharacterParser characterParser;
    private ArrayList<User> SourceDateList;
    private ArrayList<User>contactList;

    //根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;
    private SQLiteOpenHelper helper;
    private ContactsList readerContactLog;
    private ContactsList writeContactLog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.helper = new DATABASE(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_page);
        //ArrayList<User> serverContactData = new ArrayList<>();
        //this.getIntent().getSerializableExtra("friend");
        //Toast.makeText(this,,Toast.LENGTH_LONG).show();
        initView();
        initEvent();
        setSelect(0);
        setUnReadMessage(7,unReadMessageCount);
    }

    private void initContactListView() {
//        readerContactLog = new ContactsList(this.helper.getReadableDatabase());
//        contactList = readerContactLog.getUserList();
        characterParser = CharacterParser.getInstance();
        contactList = new ArrayList<>();
        User user = new User();
        user.setUsername("cccccc");
        user.setAccount("cccccc");
        contactList.add(user);
        SourceDateList = new ArrayList<>();
        SourceDateList = filledData(contactList);
        pinyinComparator = new PinyinComparator();
        contactListView = (ListView) findViewById(R.id.contact_list);
        if (!SourceDateList.isEmpty()) {
            //根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
            contactAdapter = new SortAdapter(this,SourceDateList);
            contactListView.setAdapter(contactAdapter);
        }
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainPage.this,FriendInfoActivity.class);
                Bundle bundle =new Bundle();
                bundle.putParcelable("userPhoto", SourceDateList.get(position).getPortrait());
                bundle.putString("userName",SourceDateList.get(position).getUsername());
                bundle.putString("userSex",SourceDateList.get(position).getUserSex());
                bundle.putString("userAccount",SourceDateList.get(position).getAccount());
                bundle.putString("userAtavar",SourceDateList.get(position).getUsername());
                bundle.putString("userEmail",SourceDateList.get(position).getEmail());
                bundle.putString("userBirth",SourceDateList.get(position).getBirth());
                bundle.putString("userPhone",SourceDateList.get(position).getPhone());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    //为ListView填充数据
    private ArrayList<User> filledData(ArrayList<User> contact) {
        ArrayList<User> mSortList = new ArrayList<User>();
        for (int i = 0; i < contact.size(); i++) {
            User sortModel = new User();
            String sortString = null;
            sortModel.setUsername(contact.get(i).getUsername());
            sortModel.setAccount(contact.get(i).getAccount());
//            sortModel.setPortrait();  //
            // 汉字转换成拼音
            String pinyin = characterParser.getSelling(contact.get(i).getUsername());
            sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
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
                initContactListView();
                //networkRequest();
                friendBroadcas();
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
            String msg = "type"+":"+Integer.toString(GlobalMsgUtils.msgFriendList)+":"+"account"+":"+ MyStatic.UserAccount;
            Log.v("aaaaa", msg);
            NetworkService.getInstance().sendUpload(msg);
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
