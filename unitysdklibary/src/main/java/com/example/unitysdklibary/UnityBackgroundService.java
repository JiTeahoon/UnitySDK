package com.example.unitysdklibary;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

public class UnityBackgroundService extends IntentService {
    public UnityBackgroundService()
    {
        super("UnityBackgroundService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        try {
            URL url = new URL(UnityNotificationSDK.getInstance().GeturlAddress());
            URLConnection conn = url.openConnection();
            conn.connect();

            int nSize = conn.getContentLength();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
            UnityNotificationSDK.getInstance().DownLoadbitmap = BitmapFactory.decodeStream(bis);

            bis.close();

            String Length = String.valueOf(nSize) + "/" + String.valueOf(UnityNotificationSDK.getInstance().DownLoadbitmap.getByteCount());

            UnityNotificationSDK.getInstance().OnStartForeService();
        }
        catch (Exception e) {
            // Restore interrupt status.
            e.printStackTrace();
        }
    }
}
