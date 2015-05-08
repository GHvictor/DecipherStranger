package com.android.decipherstranger.activity.MainPageActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.android.decipherstranger.R;

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
public class UserPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main_user);
    }
    
    public void MePageOnClick(View view) {
        switch (view.getId()) {
            case R.id.myPortrait:
                break;
            case R.id.imageButton:
                break;
            case R.id.myName:
                break;
            case R.id.button:
                this.finish();
                break;
        }
    }
}
