package com.sg.rapid.filteredAlaramServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubsystemResponse {

    @SerializedName("FaultsTypeId")
    @Expose
    private Integer faultsTypeId;
    @SerializedName("FaultsType")
    @Expose
    private String faultsType;

    public Integer getFaultsTypeId() {
        return faultsTypeId;
    }

    public void setFaultsTypeId(Integer faultsTypeId) {
        this.faultsTypeId = faultsTypeId;
    }

    public String getFaultsType() {
        return faultsType;
    }

    public void setFaultsType(String faultsType) {
        this.faultsType = faultsType;
    }

}