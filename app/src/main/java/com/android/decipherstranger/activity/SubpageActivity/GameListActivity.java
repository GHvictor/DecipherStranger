package com.android.decipherstranger.activity.SubpageActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.GameActivity.RockPaperScissorsActivity;
import com.android.decipherstranger.activity.GameActivity.SetGradeActivity;
import com.android.decipherstranger.activity.GameActivity.WelcomeRspActivity;

/**
 *       へ　　　　　／|
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
 * @Date 2015/5/8 12:47
 * @e-mail 785351408@qq.com
 */
public class GameListActivity extends Activity {
    
    private Intent intent = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_game_list);
    }
    
    public void GameListOnClick(View view) {
        switch (view.getId()) {
            case R.id.game_rsp:
                this.intent = new Intent(this, WelcomeRspActivity.class);
                this.startActivity(intent);
                break;
            case R.id.button1:
                this.intent = new Intent(this, SetGradeActivity.class);
                this.startActivity(intent);
                break;
        }
    }
}
