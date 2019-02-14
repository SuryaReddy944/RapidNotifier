package com.sg.rapid.filteredAlaramServices;

public class SeverityResponse {

    private String SeverityNmae;
    private String Priority;

    public String getSeverityNmae() {
        return SeverityNmae;
    }

    public void setSeverityNmae(String severityNmae) {
        SeverityNmae = severityNmae;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }
}
