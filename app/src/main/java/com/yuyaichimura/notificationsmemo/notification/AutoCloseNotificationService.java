package com.yuyaichimura.notificationsmemo.notification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by yuya on 7/26/14.
 */
public class AutoCloseNotificationService extends Service {

    public static int NOTIFICATION_ID = 123;
    NotificationManager manager;
    final IBinder binder = new NotificationBinder(this);

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        this.manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        this.manager.cancel(NOTIFICATION_ID);
    }

    public class NotificationBinder extends Binder {

        public final Service service;

        public NotificationBinder(Service service){
            this.service = service;
        }
    }
}
