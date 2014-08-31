package com.yuyaichimura.notificationsmemo.controller;

import android.app.NotificationManager;
import android.content.Context;
import android.widget.Toast;

import com.yuyaichimura.notificationsmemo.data.Notification;
import com.yuyaichimura.notificationsmemo.view.adapter.NotificationAdapter;
import com.yuyaichimura.notificationsmemo.data.NotificationHandler;
import com.yuyaichimura.notificationsmemo.notification.NotificationDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuya on 8/2/14.
 */
public class Controller {

    static List<Notification> notificationList = new ArrayList<Notification>();
    static NotificationHandler handler = null;
    static NotificationAdapter adapter = null;
    Class thisClass = null;
    NotificationDispatcher dispatcher = null;

    Context context = null;

    public Controller(Context context, Class thisClass) {

        this.context = context;
        this.thisClass = thisClass;

        handler = new NotificationHandler(context);
        notificationList = handler.getAllNotifications();
        adapter = new NotificationAdapter(context, notificationList);

        dispatcher = new NotificationDispatcher(context, thisClass);
    }

    public void enableAll() {


        for (Notification n : notificationList) {
            n.setOn(true);
            handler.updateNotification(n);
        }

        adapter.notifyDataSetInvalidated();
        refreshNotifications();
    }

    public void disableAll() {

        for (Notification n : notificationList) {
            n.setOn(false);
            handler.updateNotification(n);
        }

        adapter.notifyDataSetInvalidated();
        refreshNotifications();
    }

    public void refreshNotifications() {
        boolean on = false;

        Toast.makeText(context, "Refreshing Notification", Toast.LENGTH_SHORT).show();

        String result = "";

        for (Notification n : notificationList) {
            if (n.isOn()) {
                on = true;
                result += n.getTitle();
                result += '\n';
            }
        }

        if (on) {
            dispatcher.startNotification(notificationList);
        } else {
            ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        }
        System.out.println("cursize = " + notificationList.size());
    }

    public static void setBooleanNotification(int position, boolean isChecked) {

        notificationList.get(position).setOn(isChecked);
        handler.updateNotification(notificationList.get(position));
    }

    public static void addNotification(String title, String description) {

        Notification n = new Notification(title, description);
        notificationList.add(n);
        handler.addNotification(n);
    }

    public static void editNotification(String title, String description, int position) {

        Notification n = notificationList.get(position);
        n.setTitle(title);
        n.setDescription(description);

        handler.updateNotification(n);
    }

    public static Notification getNotification(int position) {
        return notificationList.get(position);
    }

    public static NotificationAdapter getAdapter() {
        return adapter;
    }

    public static void deleteNotification(int position) {

        handler.deleteNotification(notificationList.remove(position));
        adapter.notifyDataSetInvalidated();

    }
}
