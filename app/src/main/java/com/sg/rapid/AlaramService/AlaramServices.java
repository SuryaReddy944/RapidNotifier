package com.sg.rapid.AlaramService;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.LoginServices.TokenModel;
import com.sg.rapid.Models.AlaramsRequest;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AlaramServices extends BaseService {

    public static void fetchAlarams (final AlaramsRequest  alaramsRequest,final ResponseListner mResponseListner){
        AlaramInterFace mAlaramInterFace = retrofit.create(AlaramInterFace.class);

        Call<List<AlaramResponse>> mCall = mAlaramInterFace.getAllAlarams(alaramsRequest);
        mCall.enqueue(new Callback<List<AlaramResponse>>() {

            @Override
            public void onResponse(Call<List<AlaramResponse>> call, Response<List<AlaramResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else{
                    mResponseListner.failureResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<AlaramResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }
        });

    }

    public interface AlaramInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("GetAlarmInfo")
        Call<List<AlaramResponse>> getAllAlarams(@Body AlaramsRequest alaramsRequest);
    }
}
