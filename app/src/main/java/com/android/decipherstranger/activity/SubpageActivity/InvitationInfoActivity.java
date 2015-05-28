package com.android.decipherstranger.activity.SubpageActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.GameOneActivity.WelcomeRspActivity;
import com.android.decipherstranger.util.ChangeUtils;

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
 * @Date 2015/5/28 13:01
 * @e-mail 785351408@qq.com
 */
public class InvitationInfoActivity extends BaseActivity {

    private TextView accountText = null;
    private TextView nameText = null;

    private String account = null;
    private String name = null;
    private int sex = 0;
    private String portrait = null;
    private Bitmap bitmap = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_invitation_info);
        this.init();
        this.setInfo();
    }

    private void init() {
        this.accountText = (TextView) super.findViewById(R.id.accountInvitation);
        this.nameText = (TextView) super.findViewById(R.id.nameInvitation);
    }

    private void setInfo() {
        Intent intent = getIntent();
        account = intent.getStringExtra("Account");
        name = intent.getStringExtra("Name");
        sex = intent.getIntExtra("Sex", 0);
        portrait = intent.getStringExtra("Portrait");
        bitmap = ChangeUtils.toBitmap(portrait);

        accountText.setText(account);
        nameText.setText(name);
    }

    public void InfoOnClick(View view){
        Intent intent = new Intent(this, WelcomeRspActivity.class);
        intent.putExtra("Type", "AddFriend");
        intent.putExtra("Account", account);
        intent.putExtra("Name", name);
        intent.putExtra("Sex", sex);
        intent.putExtra("Portrait", bitmap);
        startActivity(intent);
        finish();
    }
}
