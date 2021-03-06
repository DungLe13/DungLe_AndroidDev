package com.dungle1310.personalnotes;

/**
 * Created by Dung on 1/18/2017.
 */

public class Trash {
    private int id;
    private String title, description, dateTime;

    public Trash(int id, String title, String description, String dateTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
