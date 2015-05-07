package com.android.decipherstranger.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.entity.Contacts;
import com.android.decipherstranger.entity.RecentData;
import com.android.decipherstranger.view.BadgeView;
import com.android.decipherstranger.view.HandyTextView;

import java.util.ArrayList;

/**
 * Created by WangXin on 2015/4/2 0002.
 */
public class RecentListViewAdapter extends BaseAdapter {
    private ArrayList<Contacts> recentChatData;
    LayoutInflater inflater;


    public RecentListViewAdapter(ArrayList<Contacts> recentChatData, Context context){
        this.recentChatData = recentChatData;
        this.inflater = LayoutInflater.from(context);
    }
    public void onDateChange(ArrayList<Contacts> recentChatData){
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
        Contacts recentData = recentChatData.get(position);
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.recent_view_item,null);
            holder.recentUserName = (HandyTextView) convertView.findViewById(R.id.recent_user_name);
            holder.recentMessage = (HandyTextView) convertView.findViewById(R.id.recent_message);
            holder.recentMessageTime = (HandyTextView) convertView.findViewById(R.id.recent_message_time);
            holder.recentUserPhoto = (ImageView) convertView.findViewById(R.id.recent_user_photo);
            holder.mBadgeView = (BadgeView) convertView.findViewById(R.id.new_message_count);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        try {
            holder.recentUserName.setText(recentData.getUsername());
            holder.recentMessage.setText(recentData.getMessage());
            holder.recentMessageTime.setText(recentData.getDatetime());
            Bitmap userPhoto = recentData.getPortrait();
            Drawable drawable = new BitmapDrawable(convertView.getResources(),userPhoto);
            holder.recentUserPhoto.setImageDrawable(drawable);
            holder.mBadgeView.setText("1");
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
        BadgeView mBadgeView;
    }
}
