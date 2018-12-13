package com.sg.rapid.CallBacks;

public interface ResponseListner {

    public void onSucess(Object response,int sttuscode);
    public void onFailure(Throwable error);
    public void failureResponse(Object response);
}
