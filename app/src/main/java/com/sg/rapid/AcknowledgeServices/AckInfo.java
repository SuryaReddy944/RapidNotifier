package com.sg.rapid.AcknowledgeServices;

public class AckInfo {
    private String ID;
    private String QType;
    private String AckNotes;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getQType() {
        return QType;
    }

    public void setQType(String QType) {
        this.QType = QType;
    }

    public String getAckNotes() {
        return AckNotes;
    }

    public void setAckNotes(String ackNotes) {
        AckNotes = ackNotes;
    }
}
