package com.sg.rapid.CountServices;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.NoticeServices.AdminNotice;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class CountService  extends BaseService {


    public static void fetchUnackAlarmCount(final ResponseListner mResponseListner){

        getUnackAlarmcountInterface mInterface = retrofit.create(getUnackAlarmcountInterface.class);

        Call<List<AlarmCountResponse>> mCall = mInterface.getUnAckAlarmCount();

        mCall.enqueue(new Callback<List<AlarmCountResponse>>() {
            @Override
            public void onFailure(Call<List<AlarmCountResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<AlarmCountResponse>> call, Response<List<AlarmCountResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });

    }


    public static void fetchUnackEventCount(final ResponseListner mResponseListner){

        getUnackEventcountInterface mInterface = retrofit.create(getUnackEventcountInterface.class);

        Call<List<EventCountResponse>> mCall = mInterface.getUnAckEventCount();

        mCall.enqueue(new Callback<List<EventCountResponse>>() {
            @Override
            public void onFailure(Call<List<EventCountResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<EventCountResponse>> call, Response<List<EventCountResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });

    }




    public interface getUnackAlarmcountInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("GetAlarmInfoCount")
        Call<List<AlarmCountResponse>> getUnAckAlarmCount();
    }

    public interface getUnackEventcountInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("GetEventsInfoCount")
        Call<List<EventCountResponse>> getUnAckEventCount();
    }
}
