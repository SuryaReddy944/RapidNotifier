package com.sg.rapid.filteredAlaramServices;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class SubsystemService extends BaseService {

    public static void fetchSubsystems(final ResponseListner mResponseListner){

        SubsystemInterFace mInterFace = retrofit.create(SubsystemInterFace.class);

        Call<List<SubsystemResponse>> mCall = mInterFace.getSubsystems();

        mCall.enqueue(new Callback<List<SubsystemResponse>>() {
            @Override
            public void onResponse(Call<List<SubsystemResponse>> call, Response<List<SubsystemResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<SubsystemResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }
        });

    }



    public interface SubsystemInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("GetSubSystems")
        Call<List<SubsystemResponse>> getSubsystems();
    }
}
