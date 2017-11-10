package com.example.androidpedometer;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.telecom.Call;
import android.util.Log;
import android.content.Context;


import android.widget.TextView;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;


import static android.content.Context.SENSOR_SERVICE;

public class AndroidPedometer extends Service{

    private TextView mStepCounterText;
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetecterSensor;
    private static float steps;
    private float initStep;
    private boolean isinit = true;

    Class<?> cla_Activity;

    @Override
    public void onCreate() {
        super.onCreate();
        StartPedometer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //public void StartPedometer(String GameObjectName, String MethodName, Activity CalledActivity) {
    public void StartPedometer() {
        steps = 0;
        /*
        try {
            cla_Activity = Class.forName(CalledActivity);

        }
        catch (java.lang.ClassNotFoundException e){

        }
        try{
            selfActivity = (Activity) cla_Activity.newInstance();
        }
        catch (java.lang.IllegalAccessException e){

        }
        catch (java.lang.InstantiationException e){

        }
        */

        //KITKAT以上かつTYPE_STEP_COUNTERが有効ならtrue
        boolean isTarget = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isTarget) {
            //TYPE_STEP_COUNTERが有効な場合の処理
            Log.d("hasStepCounter", "STEP-COUNTER is available!!!");
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);



            setStepCounterListener();

            //UnityPlayer.UnitySendMessage(GameObjectName,MethodName,"0");
        } else {
            //TYPE_STEP_COUNTERが無効な場合の処理
            Log.d("hasStepCounter", "STEP-COUNTER is NOT available.");
            //UnityPlayer.UnitySendMessage(GameObjectName,MethodName,"1");
        }

    }

    public void GetSteps(String GameObjectName,String MethodName){
        float   t_step = steps - initStep;
        UnityPlayer.UnitySendMessage(GameObjectName,MethodName,Float.toString(t_step));
    }

    private void setStepCounterListener() {
        if (mStepCounterSensor != null) {
            //ここでセンサーリスナーを登録する
            mSensorManager.registerListener(mStepCountListener, mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
        if(mStepDetecterSensor != null){
            mSensorManager.registerListener(mStepCountListener,mStepDetecterSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }

    }



    private final SensorEventListener mStepCountListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //センサーを値をstepに格納
            Log.d("SensorListener", "SensorChanged");
            if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
                if(isinit == true) {
                    initStep = sensorEvent.values[0];
                    isinit = false;
                }
                else {
                    steps = sensorEvent.values[0];
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
