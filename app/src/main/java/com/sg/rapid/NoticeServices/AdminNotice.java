package com.sg.rapid.NoticeServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminNotice {

    @SerializedName("statuscode")
    @Expose
    private Integer statuscode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Data")
    @Expose
    private List<AdminNotices> data = null;

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

    public List<AdminNotices> getData() {
        return data;
    }

    public void setData(List<AdminNotices> data) {
        this.data = data;
    }

}