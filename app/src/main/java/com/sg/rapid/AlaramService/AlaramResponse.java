package com.sg.rapid.AlaramService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AlaramResponse  implements Serializable {

    @SerializedName("AlarmLogId")
    @Expose
    private Integer alarmLogId;
    @SerializedName("AssetName")
    @Expose
    private String assetName;
    @SerializedName("AssetType")
    @Expose
    private String assetType;
    @SerializedName("FaultDecription")
    @Expose
    private String faultDecription;
    @SerializedName("AlarmTagId")
    @Expose
    private Integer alarmTagId;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("Alarm_Values")
    @Expose
    private String alarmValues;
    @SerializedName("UserName")
    @Expose
    private String  userName;
    @SerializedName("Acknowledge_Notes")
    @Expose
    private Object acknowledgeNotes;
    @SerializedName("Acknowledged_On")
    @Expose
    private Object acknowledgedOn;
    @SerializedName("Acknowledgement_Status")
    @Expose
    private Integer acknowledgementStatus;
    @SerializedName("aType")
    @Expose
    private String aType;
    @SerializedName("aPriority")
    @Expose
    private String aPriority;

    public Integer getAlarmLogId() {
        return alarmLogId;
    }

    public void setAlarmLogId(Integer alarmLogId) {
        this.alarmLogId = alarmLogId;
    }

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

    public Integer getAlarmTagId() {
        return alarmTagId;
    }

    public void setAlarmTagId(Integer alarmTagId) {
        this.alarmTagId = alarmTagId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getAlarmValues() {
        return alarmValues;
    }

    public void setAlarmValues(String alarmValues) {
        this.alarmValues = alarmValues;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getAcknowledgeNotes() {
        return acknowledgeNotes;
    }

    public void setAcknowledgeNotes(Object acknowledgeNotes) {
        this.acknowledgeNotes = acknowledgeNotes;
    }

    public Object getAcknowledgedOn() {
        return acknowledgedOn;
    }

    public void setAcknowledgedOn(Object acknowledgedOn) {
        this.acknowledgedOn = acknowledgedOn;
    }

    public Integer getAcknowledgementStatus() {
        return acknowledgementStatus;
    }

    public void setAcknowledgementStatus(Integer acknowledgementStatus) {
        this.acknowledgementStatus = acknowledgementStatus;
    }

    public String getAType() {
        return aType;
    }

    public void setAType(String aType) {
        this.aType = aType;
    }

    public String getAPriority() {
        return aPriority;
    }

    public void setAPriority(String aPriority) {
        this.aPriority = aPriority;
    }

}