package com.sg.rapid.LoginServices;

public class TokenModel {
    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getFCM_Token() {
        return FCM_Token;
    }

    public void setFCM_Token(String FCM_Token) {
        this.FCM_Token = FCM_Token;
    }

    private String DeviceId;
    private String FCM_Token;
}
