package com.sg.rapid.AcknowledgeServices;

import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AlaramAcknowledgeService extends BaseService {

public static void ackAlaram(final AckInfo ackInfo, final ResponseListner responseListner){
    AlaramAckInterFace ackInterFace = retrofit.create(AlaramAckInterFace.class);
    Call<Object> mCall = ackInterFace.sendAlaramAck(ackInfo);
    mCall.enqueue(new Callback<Object>() {
        @Override
        public void onResponse(Call<Object> call, Response<Object> response) {
            if(response.isSuccessful()){
                responseListner.onSucess(response,response.code());
            }else{
                responseListner.failureResponse(response);
            }
        }

        @Override
        public void onFailure(Call<Object> call, Throwable t) {
            responseListner.onFailure(t);
        }
    });

}

    public interface AlaramAckInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("SendAcknowledgement")
        Call<Object> sendAlaramAck(@Body AckInfo ackInfo);
    }
}
