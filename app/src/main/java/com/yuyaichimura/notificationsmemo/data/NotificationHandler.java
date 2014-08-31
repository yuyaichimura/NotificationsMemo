package com.yuyaichimura.notificationsmemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yuyaichimura.notificationsmemo.data.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuya on 7/23/14.
 */
/*
 * This class is handles the SQLite stuff such as inserting rows and stuff.
 */
public class NotificationHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 3;

    // Database name
    private static final String DATABASE_NAME = "notificationManager";

    // Table name
    private static final String TABLE_NOTIFICATION = "notifications";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_ON = "onoff";

    // Constructor for the handler
    public NotificationHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        System.out.println("Handler-constructor");
    }

    // onCreate Method creates a new table
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Handler-onCreate");
        System.out.println("onCreate");
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NOTIFICATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TITLE
                + " VARCHAR(30)," + KEY_DESC + " TEXT," + KEY_ON + " boolean )";
        db.execSQL(CREATE_TABLE);
    }

    // onUpgrade drops the table and reinserts the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Handler-onUpgrade");
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);

        onCreate(db);
    }

    // Given the Notification as the parameter, a row is inserted
    public void addNotification(Notification notification) {
        System.out.println("Handler-addNotification");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, notification.getTitle());
        values.put(KEY_DESC, notification.getDescription());
        values.put(KEY_ON, notification.isOn());

        // Inserting Row
        db.insert(TABLE_NOTIFICATION, null, values);
        // Close connection
        db.close();
    }

    // Get notification object, given the row id
    public Notification getNotification(int id) {
        System.out.println("Handler-getNotification");
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTIFICATION, new String[] { KEY_ID,
                        KEY_TITLE, KEY_DESC, KEY_ON }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Notification notification = new Notification(Integer.parseInt(cursor
                .getString(0)), cursor.getString(1), cursor.getString(2),
                "1".equals(cursor.getString(3)));

        return notification;
    }

    public List<Notification> getAllNotifications() {
        System.out.println("Handler-getAllNotifications");
        List<Notification> notificationList = new ArrayList<Notification>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification();
                notification.setId(Integer.parseInt(cursor.getString(0)));
                notification.setTitle(cursor.getString(1));
                notification.setDescription(cursor.getString(2));
                notification.setOn("1".equals(cursor.getString(3)));

                notificationList.add(notification);
            } while (cursor.moveToNext());
        }

        return notificationList;
    }

    public int getNotificationCount() {
        System.out.println("Handler-getNotificationCount");

        String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateNotification(Notification notification) {
        System.out.println("Handler-updateContact");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, notification.getTitle());
        values.put(KEY_DESC, notification.getDescription());
        values.put(KEY_ON, notification.isOn());

        // updating row
        return db.update(TABLE_NOTIFICATION, values, KEY_ID + " = ?",
                new String[] { String.valueOf(notification.getId()) });
    }

    public void deleteNotification(Notification notification) {
        System.out.println("Handler-deleteContact");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTIFICATION, KEY_ID + " = ?",
                new String[] { String.valueOf(notification.getId()) });
        db.close();
    }

}

