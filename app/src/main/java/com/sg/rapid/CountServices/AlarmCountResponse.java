package com.sg.rapid.CountServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmCountResponse {

    @SerializedName("AlarmCount")
    @Expose
    private Integer alarmCount;

    public Integer getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(Integer alarmCount) {
        this.alarmCount = alarmCount;
    }

}