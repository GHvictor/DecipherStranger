package com.android.decipherstranger.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 *             へ　　　　　／|
 *        　　/＼7　　　 ∠＿/
 *        　 /　│　　 ／　／
 *        　│　Z ＿,＜　／　　 /`ヽ
 *        　│　　　　　ヽ　　 /　　〉
 *        　 Y　　　　　`　 /　　/
 *        　ｲ●　､　●　　⊂⊃〈　　/
 *        　()　 へ　　　　|　＼〈
 *        　　>ｰ ､_　 ィ　 │ ／／      去吧！
 *        　 / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 *        　 ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 *        　　7　　　　　　　|／
 *        　　＞―r￣￣`ｰ―＿
 *
 *       @ClassName        ShakeListener
 *       @author            penghaitao
 *       @Deprecated       摇晃监听
 *       @version           1.0
 *       @Date              2015/4/2.
 *       @e-mail           785351408@qq.com
 **/

public class ShakeListener implements SensorEventListener {
    // 两次检测的时间间隔
    private static final int UPTATE_INTERVAL_TIME = 1000;
    // 传感器管理器
    private SensorManager sensorManager;
    // 传感器
    private Sensor sensor;
    // 重力感应监听器
    private OnShakeListener onShakeListener;
    // 上下文
    private Context context;
    // 上次检测时间
    private long lastUpdateTime = 0;
    // 构造器
    public ShakeListener(Context context) {
        // 获得监听对象
        this.context = context;
    }

    // 开始
    public void start() {
        // 获得传感器管理器
        sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            // 获得重力传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
        // 注册
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }

    }

    // 停止检测
    public void stop() {
        sensorManager.unregisterListener(this);
    }

    // 摇晃监听接口
    public interface OnShakeListener {
        public void onShake();
    }

    // 设置重力感应监听器
    public void setOnShakeListener(OnShakeListener listener) {
        onShakeListener = listener;
    }

    // 重力感应器感应获得变化数据
    public void onSensorChanged(SensorEvent event) {
        // 现在检测时间
        long currentUpdateTime = System.currentTimeMillis();
        // 两次检测的时间间隔
        long timeInterval = currentUpdateTime - lastUpdateTime;
        // 判断是否达到了检测时间间隔
        if (timeInterval < UPTATE_INTERVAL_TIME)
            return;

        float[] values = event.values;
        float x = values[0]; // x轴方向的重力加速度，向右为正
        float y = values[1]; // y轴方向的重力加速度，向前为正
        float z = values[2]; // z轴方向的重力加速度，向上为正
        // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
        int value = 19;// 三星 i9250怎么晃都不会超过20，没办法，只设置19了
        if (Math.abs(x) > value || Math.abs(y) > value || Math.abs(z) > value ) {
            // 现在的时间变成last时间
            lastUpdateTime = currentUpdateTime;
            onShakeListener.onShake();
            System.out.println("###我被调用了");
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}