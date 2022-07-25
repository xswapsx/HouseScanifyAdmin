package com.appynitty.adminapp.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations_table")
public class OffLocation {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String locationObj;

    private String date;

    public OffLocation(String locationObj, String date) {
        this.locationObj = locationObj;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationObj() {
        return locationObj;
    }

    public void setLocationObj(String locationObj) {
        this.locationObj = locationObj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
