package com.sg.rapid.EventServices;

import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.AlaramService.AlaramServices;
import com.sg.rapid.CallBacks.ResponseListner;
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

public class EventService extends BaseService {
    public static void fetchEvents (final AlaramsRequest alaramsRequest,final ResponseListner mResponseListner){
        EventsInterFace mAlaramInterFace = retrofit.create(EventsInterFace.class);

        Call<List<EventsResponse>> mCall = mAlaramInterFace.getAllEvents(alaramsRequest);
        mCall.enqueue(new Callback<List<EventsResponse>>() {

            @Override
            public void onResponse(Call<List<EventsResponse>> call, Response<List<EventsResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else{
                    mResponseListner.failureResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<EventsResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }
        });

    }

    public interface EventsInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("GetEventsInfo")
        Call<List<EventsResponse>> getAllEvents(@Body AlaramsRequest alaramsRequest);
    }
}

