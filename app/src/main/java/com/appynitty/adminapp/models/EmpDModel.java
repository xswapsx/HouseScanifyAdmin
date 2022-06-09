package com.appynitty.adminapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmpDModel {

    @SerializedName("qrEmpId")
    @Expose
    private int qrEmpId;

    @SerializedName("appId")
    @Expose
    private String appId = null;

    @SerializedName("qrEmpName")
    @Expose
    private String qrEmpName;

    @SerializedName("qrEmpNameMar")
    @Expose
    private String qrEmpNameMar = null;

    @SerializedName("qrEmpPassword")
    @Expose
    private String qrEmpPassword;

    @SerializedName("qrEmpMobileNumber")
    @Expose
    private String qrEmpMobileNumber;

    @SerializedName("qrEmpAddress")
    @Expose
    private String qrEmpAddress;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("typeId")
    @Expose
    private int typeId;

    @SerializedName("userEmployeeNo")
    @Expose
    private String userEmployeeNo = null;

    @SerializedName("imoNo")
    @Expose
    private String imoNo;

    @SerializedName("bloodGroup")
    @Expose
    private String bloodGroup;

    @SerializedName("isActive")
    @Expose
    private boolean isActive;

    @SerializedName("target")
    @Expose
    private String target = null;

    @SerializedName("lastModifyDate")
    @Expose
    private String lastModifyDate = null;

    @SerializedName("qrEmpLoginId")
    @Expose
    private String qrEmpLoginId;

    @SerializedName("LoginId")
    @Expose
    private String LoginId = null;

    @SerializedName("Password")
    @Expose
    private String Password = null;


    public EmpDModel(String qrEmpName, String qrEmpMobileNumber, String qrEmpAddress, boolean isActive) {
        this.qrEmpName = qrEmpName;
        this.qrEmpMobileNumber = qrEmpMobileNumber;
        this.qrEmpAddress = qrEmpAddress;
        this.isActive = isActive;
    }

    public int getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(int qrEmpId) {
        this.qrEmpId = qrEmpId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getQrEmpName() {
        return qrEmpName;
    }

    public void setQrEmpName(String qrEmpName) {
        this.qrEmpName = qrEmpName;
    }

    public String getQrEmpNameMar() {
        return qrEmpNameMar;
    }

    public void setQrEmpNameMar(String qrEmpNameMar) {
        this.qrEmpNameMar = qrEmpNameMar;
    }

    public String getQrEmpPassword() {
        return qrEmpPassword;
    }

    public void setQrEmpPassword(String qrEmpPassword) {
        this.qrEmpPassword = qrEmpPassword;
    }

    public String getQrEmpMobileNumber() {
        return qrEmpMobileNumber;
    }

    public void setQrEmpMobileNumber(String qrEmpMobileNumber) {
        this.qrEmpMobileNumber = qrEmpMobileNumber;
    }

    public String getQrEmpAddress() {
        return qrEmpAddress;
    }

    public void setQrEmpAddress(String qrEmpAddress) {
        this.qrEmpAddress = qrEmpAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getUserEmployeeNo() {
        return userEmployeeNo;
    }

    public void setUserEmployeeNo(String userEmployeeNo) {
        this.userEmployeeNo = userEmployeeNo;
    }

    public String getImoNo() {
        return imoNo;
    }

    public void setImoNo(String imoNo) {
        this.imoNo = imoNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(String lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getQrEmpLoginId() {
        return qrEmpLoginId;
    }

    public void setQrEmpLoginId(String qrEmpLoginId) {
        this.qrEmpLoginId = qrEmpLoginId;
    }

    public String getLoginId() {
        return LoginId;
    }

    public void setLoginId(String loginId) {
        LoginId = loginId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "EmpDModel{" +
                "qrEmpId=" + qrEmpId +
                ", appId='" + appId + '\'' +
                ", qrEmpName='" + qrEmpName + '\'' +
                ", qrEmpNameMar='" + qrEmpNameMar + '\'' +
                ", qrEmpPassword='" + qrEmpPassword + '\'' +
                ", qrEmpMobileNumber='" + qrEmpMobileNumber + '\'' +
                ", qrEmpAddress='" + qrEmpAddress + '\'' +
                ", type='" + type + '\'' +
                ", typeId=" + typeId +
                ", userEmployeeNo='" + userEmployeeNo + '\'' +
                ", imoNo='" + imoNo + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", isActive=" + isActive +
                ", target='" + target + '\'' +
                ", lastModifyDate='" + lastModifyDate + '\'' +
                ", qrEmpLoginId='" + qrEmpLoginId + '\'' +
                ", LoginId='" + LoginId + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
