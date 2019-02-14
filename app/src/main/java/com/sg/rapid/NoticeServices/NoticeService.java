package com.sg.rapid.NoticeServices;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class NoticeService  extends BaseService{

    public static void postNotice(CreateUserNotice mNotice, final ResponseListner mResponseListner){
        CreateNoticeInterface mInterface = retrofit.create(CreateNoticeInterface.class);
        Call<Object> mCall = mInterface.createNotice(mNotice);
        mCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mResponseListner.onFailure(t);
            }
        });


    }

    public static void fetchNotice(final ResponseListner mResponseListner){
        GetNoticeInterface mInterface = retrofit.create(GetNoticeInterface.class);

        Call<List<AdminNotice>> mCall = mInterface.getNotice();
        mCall.enqueue(new Callback<List<AdminNotice>>() {

            @Override
            public void onFailure(Call<List<AdminNotice>> call, Throwable t) {
                mResponseListner.onFailure(t);
                int x = 0;

            }

            @Override
            public void onResponse(Call<List<AdminNotice>> call, Response<List<AdminNotice>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else{
                    mResponseListner.failureResponse(response);
                }
            }
        });

    }

    public static void fetchAllNotices(final ResponseListner mResponseListner){
        GetAllNoticeInterface mInterface = retrofit.create(GetAllNoticeInterface.class);

        Call<List<AllNotices>> mCall = mInterface.getAllNotice();

        mCall.enqueue(new Callback<List<AllNotices>>() {
            @Override
            public void onResponse(Call<List<AllNotices>> call, Response<List<AllNotices>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<AllNotices>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }
        });


    }

    public static  void deleteUserNotice(String id, final ResponseListner mResponseListner){

        DeleteNoticeInterface mInterface = retrofit.create(DeleteNoticeInterface.class);
        Call<Object> mCall = mInterface.deleteNotice(id);

        mCall.enqueue(new Callback<Object>() {
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });


    }


    public interface CreateNoticeInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("AddCommonNotice")
        Call<Object> createNotice(@Body CreateUserNotice mNotice);
    }

    public interface GetNoticeInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("GetNoticeForAdmin")
        Call<List<AdminNotice>> getNotice();
    }

    public interface GetAllNoticeInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("GetNoticeForAll")
        Call<List<AllNotices>> getAllNotice();
    }

    public interface DeleteNoticeInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @DELETE("DeleteNotice")
        Call<Object> deleteNotice(@Query("id") String id);
    }
}
