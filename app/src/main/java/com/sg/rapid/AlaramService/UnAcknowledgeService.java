package com.sg.rapid.AlaramService;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.Models.AlaramsRequest;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class UnAcknowledgeService extends BaseService {


     public static void unAcknowledge(final UnAckRequest mRequest, final ResponseListner mListner){

         UnAckInterFace mInterFace = retrofit.create(UnAckInterFace.class);

         Call<Object> mCall = mInterFace.unAcknowledge(mRequest);

         mCall.enqueue(new Callback<Object>() {
             @Override
             public void onResponse(Call<Object> call, Response<Object> response) {
                 if(response.isSuccessful()){
                     mListner.onSucess(response,response.code());
                 }else {
                     mListner.failureResponse(response);
                 }
             }

             @Override
             public void onFailure(Call<Object> call, Throwable t) {
                 mListner.onFailure(t);
             }
         });

     }

    public interface UnAckInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("SendAcknowledgement")
        Call<Object> unAcknowledge(@Body UnAckRequest request);
    }
}
