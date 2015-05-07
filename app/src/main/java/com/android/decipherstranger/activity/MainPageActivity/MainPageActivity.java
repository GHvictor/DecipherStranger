package com.android.decipherstranger.activity.MainPageActivity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decipherstranger.R;

import java.util.ArrayList;
import java.util.List;

public class MainPageActivity extends ActionBarActivity implements OnPageChangeListener {

    private ViewPager pager = null;
    private TextView textTab = null;
    private LocalActivityManager manager = null;
    private TextView text1, text2, text3, text4;
    private ImageView image1, image2, image3, image4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData(savedInstanceState);
        initView();
        initViewPage();
    }

    private void initData(Bundle savedInstanceState) {
        this.manager = new LocalActivityManager(this, true);
        this.manager.dispatchCreate(savedInstanceState);
    }
    
    private void initView() {
        this.textTab = (TextView) super.findViewById(R.id.textTab);
        this.image1 = (ImageView) super.findViewById(R.id.conversationImage);
        this.image2 = (ImageView) super.findViewById(R.id.contactsImage);
        this.image3 = (ImageView) super.findViewById(R.id.moreImage);
        this.image4 = (ImageView) super.findViewById(R.id.userImage);
        this.text1 = (TextView) super.findViewById(R.id.conversationText);
        this.text2 = (TextView) super.findViewById(R.id.contactsText);
        this.text3 = (TextView) super.findViewById(R.id.moreText);
        this.text4 = (TextView) super.findViewById(R.id.userText);
    }

    private void initViewPage() {
        List<View> mListViews = new ArrayList<View>();
        this.pager = (ViewPager) findViewById(R.id.viewpager_user_main);
        Intent intent1 = new Intent(MainPageActivity.this, ConversationPageActivity.class); // 加载activity到viewpage
        mListViews.add(getView("A", intent1));
        Intent intent2 = new Intent(MainPageActivity.this, ContactsPageActivity.class); // 加载activity到viewpage
        mListViews.add(getView("B", intent2));
        Intent intent3 = new Intent(MainPageActivity.this, ServicePageActivity.class); // 加载activity到viewpage
        mListViews.add(getView("C", intent3));
        Intent intent4 = new Intent(MainPageActivity.this, UserPageActivity.class); // 加载activity到viewpage
        mListViews.add(getView("D", intent4));
        this.pager.setAdapter(new MyFramePagerAdapter(mListViews));
        this.pager.setCurrentItem(1);
        this.pager.setOnPageChangeListener(this);
        this.textTab.setText("通讯录");
        this.text2.setTextColor(getResources().getColor(R.color.text_blue));
        this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_press));
    }

    public void PageOnClick(View view) {
        switch (view.getId()) {
            case R.id.conversationPage:
                this.pager.setCurrentItem(0);
                this.textTab.setText("会话");
                this.text1.setTextColor(getResources().getColor(R.color.text_blue));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_press));
                this.text2.setTextColor(getResources().getColor(R.color.text_hint));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_normal));
                this.text3.setTextColor(getResources().getColor(R.color.text_hint));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_normal));
                this.text4.setTextColor(getResources().getColor(R.color.text_hint));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_normal));
                break;
            case R.id.contactsPage:
                this.pager.setCurrentItem(1);
                this.textTab.setText("通讯录");
                this.text1.setTextColor(getResources().getColor(R.color.text_hint));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_normal));
                this.text2.setTextColor(getResources().getColor(R.color.text_blue));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_press));
                this.text3.setTextColor(getResources().getColor(R.color.text_hint));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_normal));
                this.text4.setTextColor(getResources().getColor(R.color.text_hint));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_normal));
                break;
            case R.id.morePage:
                this.pager.setCurrentItem(2);
                this.textTab.setText("服务");
                this.text1.setTextColor(getResources().getColor(R.color.text_hint));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_normal));
                this.text2.setTextColor(getResources().getColor(R.color.text_hint));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_normal));
                this.text3.setTextColor(getResources().getColor(R.color.text_blue));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_press));
                this.text4.setTextColor(getResources().getColor(R.color.text_hint));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_normal));
                break;
            case R.id.userPage:
                this.pager.setCurrentItem(3);
                this.textTab.setText("我");
                this.text1.setTextColor(getResources().getColor(R.color.text_hint));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_normal));
                this.text2.setTextColor(getResources().getColor(R.color.text_hint));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_normal));
                this.text3.setTextColor(getResources().getColor(R.color.text_hint));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_normal));
                this.text4.setTextColor(getResources().getColor(R.color.text_blue));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_press));
            default:
                break;
        }
    }

    /**
     * 加载activity
     */
    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    /*-------------------page----------------------------------------------------------------------------------------------------------*/
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        switch (arg0) {
            case 0:
                this.pager.setCurrentItem(0);
                this.textTab.setText("会话");
                this.text1.setTextColor(getResources().getColor(R.color.text_blue));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_press));
                this.text2.setTextColor(getResources().getColor(R.color.text_hint));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_normal));
                this.text3.setTextColor(getResources().getColor(R.color.text_hint));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_normal));
                this.text4.setTextColor(getResources().getColor(R.color.text_hint));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_normal));
                break;
            case 1:
                this.pager.setCurrentItem(1);
                this.textTab.setText("通讯录");
                this.text1.setTextColor(getResources().getColor(R.color.text_hint));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_normal));
                this.text2.setTextColor(getResources().getColor(R.color.text_blue));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_press));
                this.text3.setTextColor(getResources().getColor(R.color.text_hint));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_normal));
                this.text4.setTextColor(getResources().getColor(R.color.text_hint));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_normal));
                break;
            case 2:
                this.pager.setCurrentItem(2);
                this.textTab.setText("功能");
                this.text1.setTextColor(getResources().getColor(R.color.text_hint));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_normal));
                this.text2.setTextColor(getResources().getColor(R.color.text_hint));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_normal));
                this.text3.setTextColor(getResources().getColor(R.color.text_blue));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_press));
                this.text4.setTextColor(getResources().getColor(R.color.text_hint));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_normal));
                break;
            case 3:
                this.pager.setCurrentItem(3);
                this.textTab.setText("我");
                this.text1.setTextColor(getResources().getColor(R.color.text_hint));
                this.image1.setImageDrawable(getResources().getDrawable(R.drawable.conversation_normal));
                this.text2.setTextColor(getResources().getColor(R.color.text_hint));
                this.image2.setImageDrawable(getResources().getDrawable(R.drawable.contacts_normal));
                this.text3.setTextColor(getResources().getColor(R.color.text_hint));
                this.image3.setImageDrawable(getResources().getDrawable(R.drawable.more_normal));
                this.text4.setTextColor(getResources().getColor(R.color.text_blue));
                this.image4.setImageDrawable(getResources().getDrawable(R.drawable.user_press));
            default:
                break;
        }
    }

    public class MyFramePagerAdapter extends PagerAdapter {

        public List<View> mListViews;

        public MyFramePagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

    }

}
