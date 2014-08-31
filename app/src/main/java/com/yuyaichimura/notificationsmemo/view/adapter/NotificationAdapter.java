package com.yuyaichimura.notificationsmemo.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.yuyaichimura.notificationsmemo.R;
import com.yuyaichimura.notificationsmemo.controller.Controller;
import com.yuyaichimura.notificationsmemo.data.Notification;
import com.yuyaichimura.notificationsmemo.view.NotificationList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuya on 7/23/14.
 */
public class NotificationAdapter extends BaseAdapter {

    static List<Notification> notificationList;

    private static LayoutInflater inflater = null;
    Context context;

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.notificationList = notificationList;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

        if(notificationList == null){
            this.notificationList = new ArrayList<Notification>();
        }
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {

        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        int pos = position;
        System.out.println("Adapter - getView()");

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(
                    R.layout.row_notification,
                    parent, false);
        }
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        TextView desc = (TextView) view.findViewById(R.id.tv_desc);
        Switch onOff = (Switch) view.findViewById(R.id.s_onoff);

        Notification n = notificationList.get(position);

        title.setText(n.getTitle());
        desc.setText(n.getDescription());
        onOff.setChecked(n.isOn());
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Controller.setBooleanNotification(position, isChecked);
                NotificationList.controller.refreshNotifications();
            }
        });


        return view;
    }
/*    void refresh(){
        ((NotificationList)context).refreshNotifications();
    }*/
}