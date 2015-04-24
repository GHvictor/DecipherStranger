package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.FriendInfoActivity;
import com.android.decipherstranger.entity.User;
import com.android.decipherstranger.util.CharacterParser;
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
public class FriendsMainTabFragment extends Fragment{
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private View view;

    private BadgeView friendsRequestCount;
    private final static int NORMAL = 0;

    //������ת����ƴ��
    private CharacterParser characterParser;
    private List<User> SourceDateList;

    //����ƴ��������ListView���������
    private PinyinComparator pinyinComparator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_friend_list,container,false);
        initViews();
        itemOnclick();
        return view;
    }

    private void itemOnclick() {
        sortListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FriendInfoActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("userPhoto",SourceDateList.get(position).getPortrait());
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

    private void initViews() {
        //ʵ��������תƴ��
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        friendsRequestCount = (BadgeView) view.findViewById(R.id.friends_request_count);
        friendsRequestCount(NORMAL);
        sideBar.setTextView(dialog);

        // �����Ҳഥ������
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // ����ĸ�״γ��ֵ�λ��
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });
        sortListView = (ListView) view.findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
                Toast.makeText(getActivity(),
                        ((User) adapter.getItem(position)).getUsername(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        SourceDateList = filledData(getResources().getStringArray(R.array.date));
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(getActivity(), SourceDateList);
        sortListView.setAdapter(adapter);

        mClearEditText = (ClearEditText)view.findViewById(R.id.filter_edit);
        // �������������ֵ�ĸı�����������
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
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

    //ΪListView�������
    private List<User> filledData(String[] date) {
        List<User> mSortList = new ArrayList<User>();

        for (int i = 0; i < date.length; i++) {
            User sortModel = new User();
            sortModel.setUsername(date[i]);
//            sortModel.setPortraitUrl();  //����ͷ��
            // ����ת����ƴ��
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;
    }

    //����������ڵ�ֵ�����˲�����ListView
    private void filterData(String filterStr) {
        List<User> filterDateList = new ArrayList<User>();

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

        // ����a-z��������
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
    //����������Ŀ��ʾ
    public void friendsRequestCount(int friendsRequestCounts){
        if (friendsRequestCounts != 0){
            friendsRequestCount.setText(friendsRequestCounts);
            friendsRequestCount.show();
        }else {
            friendsRequestCount.hide();
        }
    }
}
