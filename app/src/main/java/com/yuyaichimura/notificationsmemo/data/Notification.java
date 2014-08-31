package com.yuyaichimura.notificationsmemo.data;

/**
 * Created by yuya on 7/23/14.
 */
/*
 * The Notification object stores a single notification, with its id, title, description, and its on-or-off status
 */
public class Notification {

    int id;
    String title;
    String description;
    boolean on;

    /*
     * Default constructor
     */
    public Notification() {

    }

    /*
     * Constructor
     */
    public Notification(int id, String title, String description, boolean on) {
        this.id = id;

        this.title = title;
        this.description = description;

        this.on = on;
    }

    /*
     * Another constructor without the id
     */
    public Notification(String title, String description) {
        this.title = title;
        this.description = description;

        this.on = true;
    }

    /*
     * Getter and Setter methods for the fields
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean toggle() {
        this.on = !this.on;
        return this.on;
    }
}
