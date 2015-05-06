package com.android.decipherstranger.activity.MainPageActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.android.decipherstranger.view.ClearEditText;
import com.android.decipherstranger.view.SideBar;
import com.android.decipherstranger.view.SideBar.OnTouchingLetterChangedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by WangXin on 2015/3/25 0025.
 */
public class FriendsMainTabFragment extends Fragment {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private View view;

    private BadgeView friendsRequestCount;
    private final static int NORMAL = 0;

    private SQLiteOpenHelper helper;
    private ContactsList readerContactLog;
    private ContactsList writeContactLog;

    //汉字转换成拼音的类
    private CharacterParser characterParser;
    private ArrayList<User> SourceDateList;
    private ArrayList<User>contactList;

    //根据拼音来排列ListView里面的数据类
    private PinyinComparator pinyinComparator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_friend_list,container,false);
        this.helper = new DATABASE(getActivity());
        //friendBroadcas();

        initData();
        initViews();
//        itemOnclick();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //networkRequest();
    }

    private void initData() {
        readerContactLog = new ContactsList(this.helper.getReadableDatabase());
        contactList = new ArrayList<>();
        contactList = readerContactLog.getUserList();
        SourceDateList = new ArrayList<User>();
        User user = new User();
        user.setAccount("cccccc");
        user.setUsername("cccccc");
        SourceDateList.add(user);
        SourceDateList = filledData(contactList);

        if (!SourceDateList.isEmpty()) {
            //根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
            adapter = new SortAdapter(getActivity(), SourceDateList);
            sortListView.setAdapter(adapter);
        }
    }

//    private void itemOnclick() {
//        sortListView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
//                Bundle bundle =new Bundle();
//                bundle.putParcelable("userPhoto", SourceDateList.get(position).getPortrait());
//                bundle.putString("userName",SourceDateList.get(position).getUsername());
//                bundle.putString("userSex",SourceDateList.get(position).getUserSex());
//                bundle.putString("userAccount",SourceDateList.get(position).getAccount());
//                bundle.putString("userAtavar",SourceDateList.get(position).getUsername());
//                bundle.putString("userEmail",SourceDateList.get(position).getEmail());
//                bundle.putString("userBirth",SourceDateList.get(position).getBirth());
//                bundle.putString("userPhone",SourceDateList.get(position).getPhone());
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//    }

    private void initViews() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        friendsRequestCount = (BadgeView) view.findViewById(R.id.friends_request_count);
        friendsRequestCount(NORMAL);
        sideBar.setTextView(dialog);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });
        /*sortListView = (ListView) view.findViewById(R.id.contact_list);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //  这里要利用adapter.getItem(position)来获取当前position所对应的对象
                Toast.makeText(getActivity(),
                        ((User) adapter.getItem(position)).getUsername(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
        mClearEditText = (ClearEditText)view.findViewById(R.id.filter_edit);
        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //为ListView填充数据
    private ArrayList<User> filledData(ArrayList<User> contact) {
        ArrayList<User> mSortList = new ArrayList<User>();
        for (int i = 0; i < contact.size(); i++) {
            User sortModel = new User();
            String sortString = null;
            try {
                sortModel.setUsername(contact.get(i).getUsername());
//            sortModel.setPortrait();  //����ͷ��
                // 汉字转换成拼音
                String pinyin = characterParser.getSelling(contact.get(i).getUsername());
                sortString = pinyin.substring(0, 1).toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }

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


    //根据输入框中的值来过滤数据并更新ListView
    private void filterData(String filterStr) {
        ArrayList<User> filterDateList = new ArrayList<User>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (User sortModel : SourceDateList) {
                String name = sortModel.getUsername();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
    //好友请数量
    public void friendsRequestCount(int friendsRequestCounts){
        if (friendsRequestCounts != 0){
            friendsRequestCount.setText(friendsRequestCounts);
            friendsRequestCount.show();
        }else {
            friendsRequestCount.hide();
        }
    }

    /*private void networkRequest(){
        if(NetworkService.getInstance().getIsConnected()) {
            String userInfo = "type"+":"+Integer.toString(GlobalMsgUtils.msgFriendList)+":"+"account"+":"+ MyStatic.UserAccount;
            Log.v("aaaaa", userInfo);
            NetworkService.getInstance().sendUpload(userInfo);
        }
        else {
            NetworkService.getInstance().closeConnection();
            Log.v("Login", "已经执行T（）方法");
        }
    }*/


    /*private void friendBroadcas() {
        //动态方式注册广播接收者
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        FriendBroadcastReceiver receiver = new FriendBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.android.decipherstranger.FRIEND");
        broadcastManager.registerReceiver(receiver, filter);
    }

    public class FriendBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(getActivity().getIntent().getAction().equals("com.android.decipherstranger.FRIEND")){
                if(getActivity().getIntent().getBooleanExtra("reResult", false)) {
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
                *//*
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
                }*//*
                }
                else{
                    Toast.makeText(context, "第一次？", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(context, "bbbbbb", Toast.LENGTH_SHORT).show();
            }
        }
    }*/


}

