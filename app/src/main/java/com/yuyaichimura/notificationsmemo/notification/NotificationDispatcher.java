package com.yuyaichimura.notificationsmemo.notification;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.Html;

import com.yuyaichimura.notificationsmemo.R;
import com.yuyaichimura.notificationsmemo.data.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuya on 8/2/14.
 */
public class NotificationDispatcher {

    Context context = null;
    Class pendingClass = null;

    public NotificationDispatcher(Context context, Class pendingClass) {
        this.context = context;
        this.pendingClass = pendingClass;
    }

    public void startNotification(List<Notification> list) {

        final ArrayList<Notification> notifications = (ArrayList<Notification>) list;

        ServiceConnection serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ((AutoCloseNotificationService.NotificationBinder) service).service.startService(new Intent(context, AutoCloseNotificationService.class));

                android.app.Notification.Builder builder = new android.app.Notification.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(context.getResources().getString(R.string.app_name))
                        .setContentText(context.getResources().getString(R.string.message_expand))
                        .setOngoing(true)
                        .setSubText(context.getResources().getString(R.string.message_colapse));

                android.app.Notification.InboxStyle inboxStyle = new android.app.Notification.InboxStyle();
                inboxStyle.setBigContentTitle(context.getResources().getString(R.string.app_name));
                for (Notification n : notifications) {
                    if (n.isOn())
                        inboxStyle.addLine(Html.fromHtml("<strong>" + n.getTitle() + "</strong> " + n.getDescription()));
                }
                builder.setStyle(inboxStyle);

                Intent intent = new Intent(context, pendingClass);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(pendingClass);
                stackBuilder.addNextIntent(intent);

                PendingIntent resultIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultIntent);

                android.app.NotificationManager manager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                manager.cancelAll();
                manager.notify(AutoCloseNotificationService.NOTIFICATION_ID, builder.build());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        context.bindService(new Intent(context, AutoCloseNotificationService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
