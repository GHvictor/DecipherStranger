package com.android.decipherstranger.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.util.MediaManager;

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
	private Boolean flag = false;
	private int mMinItemWidth;
	private int mMaxItemWidth;
	private View mAnimView;

	public ChatMsgViewAdapter(Context context, List<Contacts> coll) {
		ctx = context;
		this.coll = coll;
		mInflater = LayoutInflater.from(context);

		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(outMetrics);
		mMaxItemWidth = (int) (outMetrics.widthPixels*0.7f);
		mMinItemWidth = (int) (outMetrics.widthPixels*0.15f);
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
			viewHolder.mLength = convertView
					.findViewById(R.id.recorder_length);
			viewHolder.isComMsg = isComMsg;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvSendTime.setText(entity.getDatetime());

		if (entity.getMessage().contains(".amr")) {
			viewHolder.tvContent.setVisibility(View.GONE);
			viewHolder.mLength.setVisibility(View.VISIBLE);
			viewHolder.tvTime.setText(entity.getTimeLen() + "\"");
			ViewGroup.LayoutParams lp = viewHolder.mLength.getLayoutParams();
			int timeLength = Integer.parseInt(entity.getTimeLen());
			lp.width = (mMinItemWidth + (mMaxItemWidth / 60 * timeLength));
		} else {
			viewHolder.tvContent.setVisibility(View.VISIBLE);
			viewHolder.mLength.setVisibility(View.GONE);
			viewHolder.tvContent.setText(entity.getMessage());
			viewHolder.tvTime.setText("");
		}
		viewHolder.mLength.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAnimView != null) {
					mAnimView.setBackgroundResource(R.drawable.adj);
					mAnimView = null;
				}
				if (entity.getMessage().contains(".amr")) {
					mAnimView = v.findViewById(R.id.recorder_anim);
					mAnimView.setBackgroundResource(R.drawable.play_anim);
					AnimationDrawable anim = (AnimationDrawable) mAnimView.getBackground();
					anim.start();
					MediaManager.playSound(entity.getMessage(), new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mAnimView.setBackgroundResource(R.drawable.adj);
						}
					});
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
		private View mLength;
	}
}
