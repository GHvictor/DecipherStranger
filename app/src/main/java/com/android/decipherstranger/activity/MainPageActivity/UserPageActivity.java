package com.android.decipherstranger.activity.MainPageActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;
import com.android.decipherstranger.util.SharedPreferencesUtils;

/**
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
 *
 * @author penghaitao
 * @version V1.0
 * @Date 2015/5/6 21:39
 * @e-mail 785351408@qq.com
 */
public class UserPageActivity extends BaseActivity {
    
    private MyApplication application = null;

    private ImageView PopPortrait = null;
    
    private ImageView portraitImage = null;
    private TextView nameText = null;
    private TextView accountText = null;
    private TextView sexText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_user);
        this.init();
    }
    
    private void init() {
        this.application = (MyApplication) getApplication();

        this.portraitImage = (ImageView) super.findViewById(R.id.portraitImage);
        this.nameText = (TextView) super.findViewById(R.id.nameText);
        this.accountText = (TextView) super.findViewById(R.id.accountText);
        this.sexText = (TextView) super.findViewById(R.id.sexText);

        portraitImage.setImageBitmap(application.getPortrait());
        this.nameText.setText(application.getName());
        this.accountText.setText(application.getAccount());
        this.sexText.setText(application.getSex());
    }
    
    public void MePageOnClick(View view) {
        switch (view.getId()) {
            case R.id.myPortrait:
                // 从本地文档获取图片并上传
                break;
            case R.id.portraitImage:
                //  放大头像
                break;
            case R.id.myName:
                //  改变昵称并上传
                break;
            case R.id.button:
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,MyStatic.FILENAME_USER);
                sharedPreferencesUtils.clear();
                sharedPreferencesUtils.set(MyStatic.USER_LOGIN, false);
                MyApplication.getInstance().exit();
                this.finish();
                break;
        }
    }
}
