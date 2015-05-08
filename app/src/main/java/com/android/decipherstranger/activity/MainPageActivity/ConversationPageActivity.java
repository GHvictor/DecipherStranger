package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.adapter.RecentListViewAdapter;
import com.android.decipherstranger.db.DATABASE;
import com.android.decipherstranger.db.RecentContacts;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.view.BadgeView;
import com.android.decipherstranger.view.RecentListView;

import java.util.ArrayList;

public class ConversationPageActivity extends Activity implements RecentListView.IRefreshListener {
	private RecentListViewAdapter adapter;
	private RecentListView listView;
	private ArrayList<Contacts> readerConversationLog;
	private SQLiteOpenHelper helper;
	private RecentContacts mRecentContacts;
	private BadgeView newMessageCount;

	private static final int NO_MESSAGE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_conversation);
		helper = new DATABASE(this);
		initData();
		initView();
	}

	private void initData() {
		mRecentContacts = new RecentContacts(this.helper.getReadableDatabase());
		readerConversationLog = new ArrayList<>();
		readerConversationLog = mRecentContacts.refresh();
		if(!readerConversationLog.isEmpty()){
			adapter = new RecentListViewAdapter(readerConversationLog,this);
			listView.setAdapter(adapter);
		}
	}

	private void initView() {
		listView = (RecentListView) findViewById(R.id.recent_view_list);
		listView.setInterface(this);
		newMessageCount = (BadgeView) findViewById(R.id.new_message_count);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ConversationPageActivity.this, ChatMsgActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("userAccount", readerConversationLog.get(position).getAccount());
				bundle.putString("userName", readerConversationLog.get(position).getUsername());
				bundle.putParcelable("userPhoto", readerConversationLog.get(position).getPortrait());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}
	//设置消息个数提示
	public void setNewMessageCount(int newMessage){
		if (newMessage != NO_MESSAGE){
			newMessageCount.setText(newMessage);
			newMessageCount.show();
		}else {
			newMessageCount.hide();
		}
	}


	@Override
	public void onRefresh() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//获取最新数据
				setRefreshData();
				//通知界面显示
				if (!readerConversationLog.isEmpty()){
					refreshList(readerConversationLog);
				}
				//通知listView数据刷新完毕
				listView.reFreshComplete();
			}
		},2000);
	}

	private void refreshList(ArrayList<Contacts>Conversation) {
		if (adapter == null){
			adapter = new RecentListViewAdapter(Conversation,this);
			listView.setAdapter(adapter);
		}else {
			adapter.onDateChange(Conversation);
		}
	}

	private void setRefreshData() {

	}
}
