package com.example.unitysdklibary;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import java.io.ByteArrayOutputStream;

public class UnityNotificationSDK {
    private static UnityNotificationSDK ourInstance = null;

    public static UnityNotificationSDK getInstance() {
        return ourInstance;
    }

    public final String Channel_1_ID = "channel1";

    public Bitmap DownLoadbitmap = null;

    private String URLAddress = "";

    private UnityPlayerActivity tempactivity;

    private boolean bDebugLog = false;

    public UnityNotificationSDK(UnityPlayerActivity activity)
    {
        tempactivity = activity;

        ourInstance = this;

        OnCreateNotification();
    }

    private void OnCreateNotification()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    Channel_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = tempactivity.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    public void OnNotificationShow(String Title, String ContentText)
    {
        NotificationManager notificationManager =
                (NotificationManager)tempactivity.getSystemService(Context.NOTIFICATION_SERVICE);

        Resources res = tempactivity.getResources();
        int iconIdx = res.getIdentifier("app_icon", "mipmap", tempactivity.getPackageName());

        String idx = String.valueOf(iconIdx);
        Log.d("IconIdx", idx);

        Notification notification = new Notification.Builder(tempactivity, Channel_1_ID)
                .setSmallIcon(iconIdx)
                .setContentTitle(Title)
                .setContentText(ContentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }


    String Objectname = "Button";
    String Functionname = "OnBackGroundDebug";
    public void OnStartForeService()
    {
        String Image = BitmapToString(DownLoadbitmap);

        UnityPlayer.UnitySendMessage(Objectname,Functionname,Image);

        tempactivity.startForegroundService(new Intent(tempactivity, UnityAndroidService.class));
    }

    private String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] bytes = baos.toByteArray();
        String temp = Base64.encodeToString(bytes, Base64.DEFAULT);
        return temp;
    }

    public void OnStopForeService()
    {
        tempactivity.stopService(new Intent(tempactivity, UnityAndroidService.class));
    }

    public void OnStartBackgroundService()
    {
        tempactivity.startService(new Intent(tempactivity, UnityBackgroundService.class));

        DebugLog("UnityBackground", "BackGround Start");
    }

    public void OnStopBackGroundService()
    {
        tempactivity.stopService(new Intent(tempactivity, UnityBackgroundService.class));

        DebugLog("UnityBackground", "BackGround Stop");
    }

    public String GeturlAddress()
    {
        return URLAddress;
    }

    public void DebugLog(String Tag, String Msg)
    {
        if(bDebugLog)
        {
            Log.d(Tag, Msg);
        }
    }

    public void setURLAddress(String urlAddress)
    {
        URLAddress = urlAddress;
    }

    public void setbDebugLog(boolean debugLog)
    {
        bDebugLog = debugLog;
    }

    public void setCallUnityFuction(String unityObjectname, String functionname)
    {
        Objectname = unityObjectname;
        Functionname = functionname;
    }

    /*public class Builder
    {
        UnityNotificationSDK Instance = null;
        boolean bDebugLog = false;
        String URL;

        String UnityObjectname = null;
        String Functionname = null;

        public Builder(UnityPlayerActivity activity)
        {
            Instance = new UnityNotificationSDK(activity);
        }

        public Builder setURLAddress(String UrlAddress)
        {
            URL = UrlAddress;

            return this;
        }

        public Builder setbDebugLog(boolean debugLog)
        {
            bDebugLog = debugLog;

            return this;
        }

        public Builder setCallUnityFuction(String unityObjectname, String functionname)
        {
            UnityObjectname = unityObjectname;
            Functionname = functionname;

            return this;
        }

        public UnityNotificationSDK build()
        {
            Instance.URLAddress = URL;
            Instance.bDebugLog = bDebugLog;
            Instance.Objectname = UnityObjectname;
            Instance.Functionname = Functionname;

            return Instance;
        }
    }*/
}
