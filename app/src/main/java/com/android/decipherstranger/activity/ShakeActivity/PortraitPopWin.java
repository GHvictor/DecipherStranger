package com.android.decipherstranger.activity.ShakeActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.decipherstranger.R;
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
 * @Date 2015/4/24 18:08
 * @e-mail 785351408@qq.com
 */
public class PortraitPopWin extends Activity {
    
    private ImageView imageView = null;
    
    public void onCreate(Bundle savedInstanceStated) {
        super.onCreate(savedInstanceStated);
        super.setContentView(R.layout.activity_shake_portrait);
        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap)getIntent().getParcelableExtra("DRAWABLE");
        Drawable drawable = new BitmapDrawable(bitmap);
        this.imageView.setImageBitmap(bitmap);
    }
}
