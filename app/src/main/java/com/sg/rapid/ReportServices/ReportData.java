package com.sg.rapid.ReportServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportData {

    @SerializedName("AssetName")
    @Expose
    private String assetName;
    @SerializedName("AssetType")
    @Expose
    private String assetType;
    @SerializedName("FaultDecription")
    @Expose
    private String faultDecription;
    @SerializedName("Occurance")
    @Expose
    private Integer occurance;

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getFaultDecription() {
        return faultDecription;
    }

    public void setFaultDecription(String faultDecription) {
        this.faultDecription = faultDecription;
    }

    public Integer getOccurance() {
        return occurance;
    }

    public void setOccurance(Integer occurance) {
        this.occurance = occurance;
    }

}