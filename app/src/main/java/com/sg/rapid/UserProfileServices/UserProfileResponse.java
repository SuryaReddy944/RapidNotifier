package com.sg.rapid.UserProfileServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileResponse {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserGroupId")
    @Expose
    private Integer userGroupId;
    @SerializedName("UserGroupType")
    @Expose
    private String userGroupType;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("UserStatus")
    @Expose
    private Integer userStatus;
    @SerializedName("TelNo")
    @Expose
    private String telNo;
    @SerializedName("EmailAddress")
    @Expose
    private String emailAddress;
    @SerializedName("UserPassword")
    @Expose
    private String userPassword;
    @SerializedName("AccessibleBranchId")
    @Expose
    private String accessibleBranchId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @SerializedName("UserLockCount")
    @Expose
    private Integer userLockCount;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("Column1")
    @Expose
    private Integer column1;
    @SerializedName("UserGroupName")
    @Expose
    private String userGroupName;
    @SerializedName("Email_Alert")
    @Expose
    private Boolean emailAlert;
    @SerializedName("SMS_Alert")
    @Expose
    private Boolean sMSAlert;
    @SerializedName("Push_Notification_Alert")
    @Expose
    private Boolean pushNotificationAlert;

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    @SerializedName("Profile_Picture")
    @Expose
    private String profilepicture;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserGroupType() {
        return userGroupType;
    }

    public void setUserGroupType(String userGroupType) {
        this.userGroupType = userGroupType;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getAccessibleBranchId() {
        return accessibleBranchId;
    }

    public void setAccessibleBranchId(String accessibleBranchId) {
        this.accessibleBranchId = accessibleBranchId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getUserLockCount() {
        return userLockCount;
    }

    public void setUserLockCount(Integer userLockCount) {
        this.userLockCount = userLockCount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getColumn1() {
        return column1;
    }

    public void setColumn1(Integer column1) {
        this.column1 = column1;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public Boolean getEmailAlert() {
        return emailAlert;
    }

    public void setEmailAlert(Boolean emailAlert) {
        this.emailAlert = emailAlert;
    }

    public Boolean getSMSAlert() {
        return sMSAlert;
    }

    public void setSMSAlert(Boolean sMSAlert) {
        this.sMSAlert = sMSAlert;
    }

    public Boolean getPushNotificationAlert() {
        return pushNotificationAlert;
    }

    public void setPushNotificationAlert(Boolean pushNotificationAlert) {
        this.pushNotificationAlert = pushNotificationAlert;
    }

}