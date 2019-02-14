package com.sg.rapid.LoginServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("UserID")
    @Expose
    private Integer userID;
    @SerializedName("groupId")
    @Expose
    private String groupId;
    @SerializedName("userType")
    @Expose
    private String userType;

    public Integer getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(Integer statuscode) {
        this.statuscode = statuscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}