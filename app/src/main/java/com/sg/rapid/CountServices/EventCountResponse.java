package com.sg.rapid.CountServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventCountResponse {

    @SerializedName("EventCount")
    @Expose
    private Integer eventCount;

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

}