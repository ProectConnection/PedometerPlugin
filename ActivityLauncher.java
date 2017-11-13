package com.example.androidpedometer;

/**
 * Created by yuuta on 2017/11/01.
 */

import android.app.Activity;
import android.content.Intent;
import com.unity3d.player.UnityPlayer;

public class ActivityLauncher {

    public static void launchActivity(String type) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.setClassName(UnityPlayer.currentActivity,type);

        UnityPlayer.currentActivity.startActivity(i);
    }
}