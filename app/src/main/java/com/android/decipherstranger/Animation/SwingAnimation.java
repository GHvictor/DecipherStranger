package com.android.decipherstranger.Animation;

import android.view.animation.Animation;
import android.view.animation.Transformation;

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
 * @Date 2015/5/24 22:13
 * @e-mail 785351408@qq.com
 */
public class SwingAnimation extends Animation {

    private float mMoveDegrees;     //  摆动角度

    private int mPivotXType = RELATIVE_TO_SELF;
    private int mPivotYType = RELATIVE_TO_SELF;
    private float mPivotXValue = 0.0f;      //  旋转X坐标
    private float mPivotYValue = 0.0f;      //  旋转Y坐标

    private float mPivotX;      //  旋转X坐标
    private float mPivotY;      //  旋转Y坐标

    public SwingAnimation(float moveDegrees, float pivotXValue, float pivotYValue) {
        mMoveDegrees = moveDegrees;
        mPivotXValue = pivotXValue;
        mPivotYValue = pivotYValue;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        float degrees;
/*        if (interpolatedTime >= 0 && interpolatedTime < 0.25 ){
            degrees = 4 * mMoveDegrees * interpolatedTime;
        } else if(interpolatedTime >= 0.25 && interpolatedTime < 0.75) {
            degrees = 2 * mMoveDegrees - 4 * mMoveDegrees * interpolatedTime;
        } else {
            degrees = -4 * mMoveDegrees + 4 * mMoveDegrees * interpolatedTime;
        }*/
        if (interpolatedTime >= 0 && interpolatedTime < 0.5 ){
            degrees = 2 * mMoveDegrees * interpolatedTime;
        } else {
            degrees = 2 * mMoveDegrees - 2 * mMoveDegrees * interpolatedTime;
        }
        float scale = getScaleFactor();
        
        if (mPivotX == 0.0f && mPivotY == 0.0f) {
            t.getMatrix().setRotate(degrees);
        } else {
            t.getMatrix().setRotate(degrees, mPivotX * scale, mPivotY * scale);
        }
    }
    
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mPivotX = resolveSize(mPivotXType, mPivotXValue, width, parentWidth);
        mPivotY = resolveSize(mPivotYType, mPivotYValue, height, parentHeight);
    }

}
