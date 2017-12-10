package com.sanyusha.goals;

/**
 * Created by Александра on 09.12.2017.
 */

public class Goal {
    private String title;
    private String description;
    private String type;
    private int date;
    private int tId;

    public Goal(String title, String description, String type, int date, int tId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.type = type;
        this.tId = tId;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }
}
