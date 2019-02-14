package com.sg.rapid.UserProfileServices;

public class UpdateUserProfile {

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String telNo) {
        TelNo = telNo;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public boolean isEmail_Alert() {
        return Email_Alert;
    }

    public void setEmail_Alert(boolean email_Alert) {
        Email_Alert = email_Alert;
    }

    public boolean isSMS_Alert() {
        return SMS_Alert;
    }

    public void setSMS_Alert(boolean SMS_Alert) {
        this.SMS_Alert = SMS_Alert;
    }

    public boolean isPush_Notification_Alert() {
        return Push_Notification_Alert;
    }

    public void setPush_Notification_Alert(boolean push_Notification_Alert) {
        Push_Notification_Alert = push_Notification_Alert;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getProfile_Picture() {
        return Profile_Picture;
    }

    public void setProfile_Picture(String profile_Picture) {
        Profile_Picture = profile_Picture;
    }

    public String TelNo;
    public String EmailAddress;
    public boolean Email_Alert;
    public boolean SMS_Alert;
    public boolean Push_Notification_Alert;
    public String UserId;



    private String Profile_Picture;

}
