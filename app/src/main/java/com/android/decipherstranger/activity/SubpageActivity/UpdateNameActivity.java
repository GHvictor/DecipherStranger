package com.android.decipherstranger.activity.SubpageActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.decipherstranger.R;
import com.android.decipherstranger.activity.Base.BaseActivity;
import com.android.decipherstranger.activity.Base.MyApplication;
import com.android.decipherstranger.util.MyStatic;

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
 * @Date 2015/5/16 13:18
 * @e-mail 785351408@qq.com
 */
public class UpdateNameActivity extends BaseActivity {
    
    private EditText editText = null;
    private MyApplication application = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_user_update_name);
        this.init();
    }
    
    private void init() {
        this.editText = (EditText) super.findViewById(R.id.nameText);
        this.application = (MyApplication) getApplication();
    }
    
    private void updateName() {
        String Name = this.editText.getText().toString();
        application.setName(Name);       
        Intent intent = new Intent(MyStatic.USER_BOARD);
        sendBroadcast(intent);
        // 上传至服务器
    }
    
    public void UpdateNameOnClick(View view) {
        switch (view.getId()) {
            case R.id.save_btn:
                updateName();
                onBackPressed();
                break;
            case R.id.updateName_back_button:
                onBackPressed();
        }
    }
}
