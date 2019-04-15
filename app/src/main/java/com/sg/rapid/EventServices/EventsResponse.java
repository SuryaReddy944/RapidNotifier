package com.sg.rapid.EventServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EventsResponse  implements Serializable{

    @SerializedName("EventLogId")
    @Expose
    private Integer eventLogId;
    @SerializedName("AssetName")
    @Expose
    private String assetName;
    @SerializedName("AssetType")
    @Expose
    private String assetType;
    @SerializedName("EventDecription")
    @Expose
    private String eventDecription;
    @SerializedName("EventsTagId")
    @Expose
    private Integer eventsTagId;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("Event_Values")
    @Expose
    private String eventValues;
    @SerializedName("UserName")
    @Expose
    private String userName;
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

    public Integer getEventLogId() {
        return eventLogId;
    }

    public void setEventLogId(Integer eventLogId) {
        this.eventLogId = eventLogId;
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

    public String getEventDecription() {
        return eventDecription;
    }

    public void setEventDecription(String eventDecription) {
        this.eventDecription = eventDecription;
    }

    public Integer getEventsTagId() {
        return eventsTagId;
    }

    public void setEventsTagId(Integer eventsTagId) {
        this.eventsTagId = eventsTagId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEventValues() {
        return eventValues;
    }

    public void setEventValues(String eventValues) {
        this.eventValues = eventValues;
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