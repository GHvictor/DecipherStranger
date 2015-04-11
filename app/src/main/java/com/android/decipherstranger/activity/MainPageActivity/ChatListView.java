package com.android.decipherstranger.activity.MainPageActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.decipherstranger.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by WangXin on 2015/4/1 0001.
 */
public class ChatListView extends ListView implements OnScrollListener {

    //顶部布局文件
    View Header;
    //顶部布局文件的高度
    int headerHeight;
    //当前第一个可见的Item的位置
    int firstVisibleItem;
    //标记，当前是在ListView最顶端按下的
    boolean isRemark;
    //摁下时的Y值
    int startY;
    //当前的状态
    int state;
    //当前滚动状态
    int scrollState;
    //正常状态
    final int NONE = 0;
    //提示下拉刷新状态
    final int PULL = 1;
    //提示释放状态
    final int RELESE = 2;
    //刷新状态
    final int REFRESHING = 3;
    //刷新数据接口
    IRefreshListener iRefreshListener;

    public ChatListView(Context context) {
        super(context);
        initView(context);
    }

    public ChatListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChatListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    //初始化界面，顶部布局文件添加到ListView界面
    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        Header = inflater.inflate(R.layout.activity_chat_listview_header,null);
        measureView(Header);
        headerHeight = Header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(Header);
        this.setOnScrollListener(this);
    }

    //通知父布局，所占用的高度，宽度
    private void measureView(View view){
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if(p == null){
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0,0,p.width);
        int height;
        int tempHeight = p.height;
        if(tempHeight>0){
            height = MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);
        }else {
            height = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width,height);
    }
    //设置header布局的上边距
    private void topPadding(int topPadding){
        Header.setPadding(Header.getPaddingLeft(),topPadding,Header.getPaddingRight(),Header.getPaddingBottom());
        Header.invalidate();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem == 0){
                    isRemark = true;
                    startY = (int) event.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(event);
                break;
            case MotionEvent.ACTION_UP:
                if(state == RELESE){
                    state =REFRESHING;
                    ReFreshViewByState();
                    //加载最新数据
                    iRefreshListener.onRefresh();
                }else if(state == PULL){
                    state = NONE;
                    isRemark = false;
                    ReFreshViewByState();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    //判断移动过程中的操作
    private void onMove(MotionEvent event){
        if(!isRemark){
            return;
        }
        int tempY = (int) event.getY();
        int distance = tempY - startY;
        int topPadding = distance - headerHeight;
        switch (state){
            case NONE:
                if(distance>0){
                    state = PULL;
                    ReFreshViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if(distance>headerHeight+50
                        &&scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    state = RELESE;
                    ReFreshViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if(distance<headerHeight+50){
                    state = PULL;
                    ReFreshViewByState();
                }else if(distance <= 0){
                    state = NONE;
                    isRemark = false;
                    ReFreshViewByState();
                }
                break;
        }
    }
    //根据当前状态改变界面显示
    private void ReFreshViewByState(){
        TextView tip = (TextView)Header.findViewById(R.id.tip);
        ImageView arrow = (ImageView)Header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar)Header.findViewById(R.id.progress);
        RotateAnimation anim = new RotateAnimation(0,180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1 = new RotateAnimation(180,0,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch (state){
            case NONE:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText(getResources().getString(R.string.drop_down_refresh));
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText(getResources().getString(R.string.loosen_refresh));
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;
            case REFRESHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText(getResources().getString(R.string.is_refreshing));
                arrow.clearAnimation();
                break;
        }
    }
    //获取数据完毕
    public void reFreshComplete(){
        state = NONE;
        isRemark = false;
        ReFreshViewByState();
        TextView lastUpdateTime = (TextView) Header.findViewById(R.id.last_update_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
        Date data = new Date(System.currentTimeMillis());
        String time = format.format(data);
        lastUpdateTime.setText(time);
    }
    public void setInterface(IRefreshListener iRefreshListener){
        this.iRefreshListener = iRefreshListener;
    }
    //刷新数据接口
    public interface IRefreshListener{
        public void onRefresh();
    }
}
