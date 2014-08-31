package com.yuyaichimura.notificationsmemo.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.yuyaichimura.notificationsmemo.R;
import com.yuyaichimura.notificationsmemo.controller.Controller;
import com.yuyaichimura.notificationsmemo.view.dialog.NotificationAdd;
import com.yuyaichimura.notificationsmemo.view.fragment.Fragment_List;
import com.yuyaichimura.notificationsmemo.view.fragment.Fragment_Preferences;


public class NotificationList extends Activity {

    public static Controller controller = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        initData();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new Fragment_List())
                    .commit();
        }
    }

    void initData(){
        this.controller = new Controller(this, NotificationList.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
//            Toast.makeText(this, "Adding NOtification", Toast.LENGTH_SHORT).show();
            addNotification();

            return true;
        } else if (id == R.id.action_enable_all) {

            controller.enableAll();
            return true;
        } else if (id == R.id.action_disable_all) {

            controller.disableAll();
            return true;
        } else if (id == R.id.action_settings) {

            displayPreferences();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNotification() {

        FragmentManager fragmentManager = getFragmentManager();
        NotificationAdd addNotification = new NotificationAdd();

        addNotification.show(fragmentManager, "dialog");

    }

    void displayPreferences() {

        getFragmentManager().beginTransaction().replace(R.id.container, new Fragment_Preferences()).commit();
    }
}