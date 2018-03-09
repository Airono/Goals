package com.sanyusha.goals.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sanyusha.goals.R;

import java.net.Proxy;

/**
 * Created by Александра on 09.12.2017.
 */

public class Goal {

    public enum Types {
        week,
        month,
        year,
        life,
        unknown;

        public int getColor() {
            switch(this) {
                case week:
                    return R.color.weekColor;
                case month:
                    return R.color.monthColor;
                case year:
                    return R.color.yearColor;
                case life:
                    return R.color.lifeColor;
                default:
                    return R.color.unknownColor;
            }
        }
    }

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("date")
    @Expose
    private long date;

    @SerializedName("tId")
    @Expose
    private String tId;

    public Goal(String title, String description, int type, long date, String tId) {
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

    public Types getType() {
        try {
            return Types.values()[type];
        }
        catch(IllegalArgumentException e) {
            return Types.unknown;
        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }
}
