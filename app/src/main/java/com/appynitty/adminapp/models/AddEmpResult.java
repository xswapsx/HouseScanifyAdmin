package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddEmpResult {
    @SerializedName("ID")
    @Expose
    private Integer ID;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("messageMar")
    @Expose
    private String messageMar;

    @SerializedName("isAttendenceOff")
    @Expose
    private boolean isAttendenceOff;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageMar() {
        return messageMar;
    }

    public void setMessageMar(String messageMar) {
        this.messageMar = messageMar;
    }

    public boolean isAttendenceOff() {
        return isAttendenceOff;
    }

    public void setAttendenceOff(boolean attendenceOff) {
        isAttendenceOff = attendenceOff;
    }
}
