package com.android.decipherstranger.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.decipherstranger.entity.NearbyUserInfo;
import com.android.decipherstranger.view.HandyTextView;
import com.android.decipherstranger.R;

import java.util.ArrayList;

/**
 * *
 * へ　　　　　／|
 * 　　/＼7　　　 ∠＿/
 * 　 /　│　　 ／　／
 * 　│　Z ＿,＜　／　　 /`ヽ
 * 　│　　　　　ヽ　　 /　　〉
 * 　 Y　　　　　`　 /　　/
 * 　ｲ●　､　●　　⊂⊃〈　　/
 * 　()　 へ　　　　|　＼〈
 * 　　>ｰ ､_　 ィ　 │ ／／      去吧！
 * 　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　7　　　　　　　|／
 * 　　＞―r￣￣`ｰ―＿
 * Created by WangXin on 2015/4/6 0006.
 */
public class NearbyListViewAdapter extends BaseAdapter {
    private ArrayList<NearbyUserInfo> nearbyUserData;
    private LayoutInflater inflater;
    final String MAN = "男";
    final  String WOMAN = "女";

    public NearbyListViewAdapter(ArrayList<NearbyUserInfo> nearbyUserData,Context context) {
        this.nearbyUserData = nearbyUserData;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return nearbyUserData.size();
    }

    @Override
    public Object getItem(int position) {
        return nearbyUserData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NearbyUserInfo  nearbyUserInfo = nearbyUserData.get(position);
        viewHolder holder;
        if (convertView == null){
            holder = new viewHolder();
            convertView = inflater.inflate(R.layout.nearby_view_item,null);
            holder.nearbyUserPhoto = (ImageView) convertView.findViewById(R.id.nearby_list_view_user_photo);
            holder.nearbyUserName = (HandyTextView) convertView.findViewById(R.id.nearby_list_view_user_name);
            holder.nearbyUserSex = (ImageView) convertView.findViewById(R.id.nearby_list_view_sex);
            holder.nearbyUserDistance = (HandyTextView) convertView.findViewById(R.id.nearby_list_view_distance);
        }else{
            holder = (viewHolder) convertView.getTag();
        }
        try {
            Drawable drawable = new BitmapDrawable(convertView.getResources(),nearbyUserInfo.getImgId());
            holder.nearbyUserPhoto.setImageDrawable(drawable);
            holder.nearbyUserName.setText(nearbyUserInfo.getUserName());
            holder.nearbyUserDistance.setText(nearbyUserInfo.getDistance());
            if (nearbyUserInfo.getSex() == MAN){
                holder.nearbyUserSex.setImageResource(R.drawable.ic_sex_male);
            }else if(nearbyUserInfo.getSex() == WOMAN){
                holder.nearbyUserSex.setImageResource(R.drawable.ic_sex_female);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }
    class viewHolder{
        ImageView nearbyUserPhoto;
        HandyTextView nearbyUserName;
        HandyTextView nearbyUserDistance;
        ImageView nearbyUserSex;
    }
}
