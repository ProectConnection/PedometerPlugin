package com.example.androidpedometer;

/**
 * Created by yuuta on 2017/11/11.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class ServiceLauncher {
    static public void StartService(){
        Activity currentActivity = UnityPlayer.currentActivity;
        Context context = currentActivity.getApplicationContext();
        currentActivity.startService(new Intent(context,AndroidPedometer.class));
    }
}