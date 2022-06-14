package com.appynitty.adminapp.models;

public class AddEmpDTO {

    private String qrEmpId;
    private String qrEmpName;
    private String qrEmpLoginId;
    private String qrEmpPassword;
    private String qrEmpMobileNumber;
    private String qrEmpAddress;
    private String imoNo;
    private String isActive;


    public AddEmpDTO(String qrEmpName, String qrEmpMobileNumber, String qrEmpAddress, String qrEmpLoginId, String qrEmpPassword, String imoNo, String isActive) {
        this.qrEmpName = qrEmpName;
        this.qrEmpMobileNumber = qrEmpMobileNumber;
        this.qrEmpAddress = qrEmpAddress;
        this.qrEmpLoginId = qrEmpLoginId;
        this.qrEmpPassword = qrEmpPassword;
        this.imoNo = imoNo;
        this.isActive = isActive;
    }

    public AddEmpDTO(){

    }

    public String getQrEmpId() {
        return qrEmpId;
    }

    public void setQrEmpId(String qrEmpId) {
        this.qrEmpId = qrEmpId;
    }

    public String getQrEmpName() {
        return qrEmpName;
    }

    public void setQrEmpName(String qrEmpName) {
        this.qrEmpName = qrEmpName;
    }

    public String getQrEmpLoginId() {
        return qrEmpLoginId;
    }

    public void setQrEmpLoginId(String qrEmpLoginId) {
        this.qrEmpLoginId = qrEmpLoginId;
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

    public String getImoNo() {
        return imoNo;
    }

    public void setImoNo(String imoNo) {
        this.imoNo = imoNo;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "AddEmpDTO{" +
                "qrEmpId='" + qrEmpId + '\'' +
                ", qrEmpName='" + qrEmpName + '\'' +
                ", qrEmpLoginId='" + qrEmpLoginId + '\'' +
                ", qrEmpPassword='" + qrEmpPassword + '\'' +
                ", qrEmpMobileNumber='" + qrEmpMobileNumber + '\'' +
                ", qrEmpAddress='" + qrEmpAddress + '\'' +
                ", imoNo='" + imoNo + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }
}
