package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.ChatData;
import com.android.decipherstranger.view.HandyTextView;

import java.util.ArrayList;

/**
 * Created by WangXin on 2015/4/2 0002.
 */
public class ChatListViewAdapter extends BaseAdapter {
    private ArrayList<ChatData> recentChatData;
    LayoutInflater inflater;

//    private Map<Integer,View>rowViews = new HashMap<Integer,View>();

    public ChatListViewAdapter(ArrayList<ChatData> recentChatData,Context context){
        this.recentChatData = recentChatData;
        this.inflater = LayoutInflater.from(context);
    }
    public void onDateChange(ArrayList<ChatData> recentChatData){
        this.recentChatData = recentChatData;
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return recentChatData.size();
    }

    @Override
    public Object getItem(int position) {
        return recentChatData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatData chatData = recentChatData.get(position);
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.chat_view_item,null);
            holder.recentUserName = (HandyTextView) convertView.findViewById(R.id.recent_user_name);
            holder.recentMessage = (HandyTextView) convertView.findViewById(R.id.recent_message);
            holder.recentMessageTime = (HandyTextView) convertView.findViewById(R.id.recent_message_time);
            holder.recentUserPhoto = (ImageView) convertView.findViewById(R.id.recent_user_photo);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        try {
            holder.recentUserName.setText(chatData.getRecentUserName());
            holder.recentMessage.setText(chatData.getRecentMessage());
            holder.recentMessageTime.setText(chatData.getRecentMessageTime());
            holder.recentUserPhoto.setImageResource(chatData.getRecentUserPhotoId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    class ViewHolder{
        HandyTextView recentUserName;
        HandyTextView recentMessage;
        HandyTextView recentMessageTime;
        ImageView recentUserPhoto;
    }
}
