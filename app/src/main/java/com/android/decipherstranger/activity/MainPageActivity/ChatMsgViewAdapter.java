package com.android.decipherstranger.activity.MainPageActivity;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.drm.DrmRights;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.ChatMsgEntity;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.util.ChangeUtils;
import com.android.decipherstranger.util.SoundMeter;

public class ChatMsgViewAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 1;
		int IMVT_TO_MSG = 0;
	}

	private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
	private static final int IS_COM_MESSAGE = 1;
	private List<Contacts> coll;

	private Context ctx;

	private LayoutInflater mInflater;
	private MediaPlayer mMediaPlayer = new MediaPlayer();
	private Boolean flag = false;

	public ChatMsgViewAdapter(Context context, List<Contacts> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		Contacts entity = coll.get(position);
		if (entity.getWho() == IS_COM_MESSAGE) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final Contacts entity = coll.get(position);
		int isComMsg = entity.getWho();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg == IS_COM_MESSAGE) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			}

			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.ivUserPhoto = (ImageView) convertView.
					findViewById(R.id.iv_userhead);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.tvTime = (TextView) convertView
					.findViewById(R.id.tv_time);
			viewHolder.isComMsg = isComMsg;

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvSendTime.setText(entity.getDatetime());
		
		if (entity.getMessage().contains(".amr")) {
			viewHolder.tvContent.setText("");
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.chatto_voice_playing, 0);
			viewHolder.tvTime.setText(entity.getTimeLen());
		} else {
			viewHolder.tvContent.setText(entity.getMessage());
			viewHolder.tvContent.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			viewHolder.tvTime.setText("");
		}
		viewHolder.tvContent.setOnClickListener(new OnClickListener() {
			ViewHolder viewHolder = new ViewHolder();
			public void onClick(View v) {
				if (entity.getMessage().contains(".amr") && !flag) {
					flag = true;
					playMusic(android.os.Environment.getExternalStorageDirectory() + "/" + entity.getMessage());
				} else {
					mMediaPlayer.stop();
					flag = false;
				}
			}
		});
		viewHolder.tvUserName.setText(entity.getUsername());
		Drawable drawable = new BitmapDrawable(convertView.getResources(),entity.getPortrait());
		viewHolder.ivUserPhoto.setImageDrawable(drawable);
		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public TextView tvTime;
		public int isComMsg;
		public ImageView ivUserPhoto;
	}

	/**
	 * @Description
	 * @param name
	 */
	private void playMusic(String name) {
		try {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(name);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void stop() {

	}

}
