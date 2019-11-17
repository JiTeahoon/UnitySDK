package com.example.unitysdklibary;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class UnityAndroidService extends Service {
    public UnityAndroidService()
    {
        super();
    }

    @Override
    @Nullable
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //시작
        super.onStartCommand(intent, flags, startId);

        Intent notificationIntent = new Intent(this, UnityActive.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Resources res = this.getResources();
        int iconIdx = res.getIdentifier("app_icon", "mipmap", this.getPackageName());

        String idx = String.valueOf(iconIdx);
        Log.d("IconIdx", idx);

        Notification notification = new Notification.Builder(this, UnityNotificationSDK.getInstance().Channel_1_ID)
                .setSmallIcon(iconIdx)
                .setContentTitle("SDK")
                .setContentText("SDK Notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setStyle(new Notification.BigPictureStyle()
                        .bigPicture(UnityNotificationSDK.getInstance().DownLoadbitmap))
                .build();

        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
