package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.SubpageActivity.FriendInfoActivity;
import com.android.decipherstranger.adapter.SortAdapter;
import com.android.decipherstranger.db.ContactsList;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.CharacterParser;
import com.android.decipherstranger.util.PinyinComparator;
import com.android.decipherstranger.view.BadgeView;
import com.android.decipherstranger.view.ClearEditText;
import com.android.decipherstranger.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;

public class ContactsPageActivity extends Activity {

	private ListView contactListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText clearEditText;

	private SQLiteOpenHelper helper;
	private ContactsList readerContactLog;
	private ContactsList writeContactLog;

	private ArrayList<User>mContactList;
	//汉字转换成拼音的类
	private CharacterParser characterParser;
	//根据拼音来排列ListView里面的数据类
	private PinyinComparator pinyinComparator;
	//新的好友提醒
	private BadgeView friendsRequestCount;
	private final static int NORMAL = 0;
	private LinearLayout newFriends;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main_contacts);
		this.helper = new DATABASE(this);
		initView();
		initData();
		friendsRequestCount(NORMAL);
	}

	private void initData() {
		readerContactLog = new ContactsList(this.helper.getReadableDatabase());
		mContactList = new ArrayList<>();
		mContactList = readerContactLog.getUserList();
		mContactList = filledData(mContactList);
		if(!mContactList.isEmpty()){
			Collections.sort(mContactList,pinyinComparator);
			adapter = new SortAdapter(this,mContactList);
			contactListView.setAdapter(adapter);
		}

	}

	private void initView() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);

		friendsRequestCount = (BadgeView)findViewById(R.id.friends_request_count);
		newFriends = (LinearLayout) findViewById(R.id.new_friends);
		newFriends.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				friendsRequestCount(NORMAL);
			}
		});

		sideBar.setTextView(dialog);
		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					contactListView.setSelection(position);
				}
			}
		});
		contactListView = (ListView) findViewById(R.id.contact_list);
		contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ContactsPageActivity.this, FriendInfoActivity.class);
				Bundle bundle =new Bundle();
				bundle.putParcelable("userPhoto", mContactList.get(position).getPortrait());
				bundle.putString("userName",mContactList.get(position).getUsername());
				bundle.putString("userSex",mContactList.get(position).getUserSex());
				bundle.putString("userAccount",mContactList.get(position).getAccount());
				bundle.putString("userAtavar",mContactList.get(position).getUsername());
				bundle.putString("userEmail",mContactList.get(position).getEmail());
				bundle.putString("userBirth",mContactList.get(position).getBirth());
				bundle.putString("userPhone",mContactList.get(position).getPhone());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		clearEditText = (ClearEditText)findViewById(R.id.filter_edit);
		clearEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				if (!mContactList.isEmpty()){
					filterData(s.toString());
				}
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

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
			sortModel.setUsername(contact.get(i).getUsername());
			sortModel.setAccount(contact.get(i).getAccount());
			sortModel.setPortrait(contact.get(i).getPortrait());
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


	//根据输入框中的值来过滤数据并更新ListView
	private void filterData(String filterStr) {
		ArrayList<User> filterDateList = new ArrayList<User>();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = mContactList;
		} else {
			filterDateList.clear();
			for (User sortModel : mContactList) {
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

	//新的好友提醒
	public void friendsRequestCount(int friendsRequestCounts){
		if (friendsRequestCounts != 0){
			friendsRequestCount.setText(friendsRequestCounts);
			friendsRequestCount.show();
		}else {
			friendsRequestCount.hide();
		}
	}

	private void friendBroadcas() {
		//动态方式注册广播接收者
		LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
		FriendBroadcastReceiver receiver = new FriendBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.android.decipherstranger.FRIEND");
		broadcastManager.registerReceiver(receiver, filter);
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
					serverContactData = filledData(serverContactData);
					for(int i=0;i<serverContactData.size();i++){
						mContactList.add(serverContactData.get(i));
					}
					if (adapter == null){
						Collections.sort(mContactList, pinyinComparator);
						adapter = new SortAdapter(ContactsPageActivity.this, mContactList);
						contactListView.setAdapter(adapter);
					}else{
						Collections.sort(mContactList, pinyinComparator);
						adapter.updateListView(mContactList);
					}
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
