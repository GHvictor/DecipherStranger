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
	private CharacterParser characterParser;
	private PinyinComparator pinyinComparator;
	private BadgeView friendsRequestCount;
	private final static int NORMAL = 0;
	private LinearLayout newFriends;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main_contacts);
		this.helper = new DATABASE(this);
        friendBroadcas();
		initView();
		initData();
		friendsRequestCount(NORMAL);
	}

	@Override
	protected void onRestart() {
		initView();
		initData();
		super.onRestart();
	}

    /*@Override
    protected void onStart() {
        initView();
        initData();
        super.onStart();
    }*/

    private void initData() {
		readerContactLog = new ContactsList(this.helper.getReadableDatabase());
		mContactList = new ArrayList<>();

		if ( !readerContactLog.getUserList().isEmpty()){
			mContactList = readerContactLog.getUserList();
		}else {
			mContactList = (ArrayList<User>) this.getIntent().getSerializableExtra("contactList");
		}
		mContactList = filledData(mContactList);
		if(!mContactList.isEmpty()){
			Collections.sort(mContactList, pinyinComparator);
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
		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
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

	//
	private ArrayList<User> filledData(ArrayList<User> contact) {
		ArrayList<User> mSortList = new ArrayList<User>();
		for (int i = 0; i < contact.size(); i++) {
			User sortModel = new User();
			String sortString = null;
			sortModel.setUsername(contact.get(i).getUsername());
			sortModel.setAccount(contact.get(i).getAccount());
			sortModel.setPortrait(contact.get(i).getPortrait());
			//
			String pinyin = characterParser.getSelling(contact.get(i).getUsername());
			sortString = pinyin.substring(0, 1).toUpperCase();
			//
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;
	}


	//
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
		//
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	//
	public void friendsRequestCount(int friendsRequestCounts){
		if (friendsRequestCounts == 0){
			friendsRequestCount.hide();
		}else {
			friendsRequestCount.setText(friendsRequestCounts);
			friendsRequestCount.show();
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
            //Toast.makeText(context, "行不行了", Toast.LENGTH_SHORT).show();
            if(intent.getAction().equals("com.android.decipherstranger.FRIEND")){
                if(intent.getBooleanExtra("reResult", false)) {
                    User contact = new User();
                    contact.setAccount(intent.getStringExtra("reAccount"));
                    contact.setUsername(intent.getStringExtra("reName"));
                    Toast.makeText(context, "接接", Toast.LENGTH_SHORT).show();
                    contact.setPortrait(ChangeUtils.toBitmap(intent.getStringExtra("rePhoto")));
                    mContactList.add(contact);
                    System.out.println(mContactList.get(0).getAccount().toString());
                    //Todo 数据接收
                }else if(intent.getBooleanExtra("isfinish", false)){
                    //onRestart();
                    initView();
                    //Todo 数据处理
                    //System.out.println("aacxzzxc");
                    //Toast.makeText(context, "第一次？", Toast.LENGTH_SHORT).show();
                }else{
                    //Todo 没有好友
                    Toast.makeText(context, "bbbbbb", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
