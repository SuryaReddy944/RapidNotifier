package com.sg.rapid.UserProfileServices;

import android.arch.lifecycle.LifecycleObserver;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.EventServices.EventsResponse;
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
import retrofit2.http.PUT;

public class UserProfileService extends BaseService {


    public static void fetchProfile(final ResponseListner mResponseListner){
        ProfileInterFace profileInterFace = retrofit.create(ProfileInterFace.class);

        Call<List<UserProfileResponse>> mCall =  profileInterFace.getUserProfile();
        mCall.enqueue(new Callback<List<UserProfileResponse>>() {
            @Override
            public void onFailure(Call<List<UserProfileResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<UserProfileResponse>> call, Response<List<UserProfileResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }
        });


    }

    public static void updateUser(final UpdateUserProfile profiledata,final ResponseListner mResponseListner){
        UpdateProfileInterFace updateProfileInterFace = retrofit.create(UpdateProfileInterFace.class);

        Call<List<UserUpdateResponse>> mCall = updateProfileInterFace.updateUserProfile(profiledata);
        mCall.enqueue(new Callback<List<UserUpdateResponse>>() {
            @Override
            public void onResponse(Call<List<UserUpdateResponse>> call, Response<List<UserUpdateResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else{
                    mResponseListner.failureResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<UserUpdateResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }
        });
    }

    public static void updatePass(final UserPassword data,final ResponseListner mResponseListner){
        ChangePassInterFace mChangePassInterFace = retrofit.create(ChangePassInterFace.class);
        Call<List<UserUpdateResponse>> mCall = mChangePassInterFace.updateUserPass(data);
        mCall.enqueue(new Callback<List<UserUpdateResponse>>() {
            @Override
            public void onResponse(Call<List<UserUpdateResponse>> call, Response<List<UserUpdateResponse>> response) {
                if(response.isSuccessful()){
                    mResponseListner.onSucess(response,response.code());
                }else {
                    mResponseListner.failureResponse(response);
                }
            }

            @Override
            public void onFailure(Call<List<UserUpdateResponse>> call, Throwable t) {
                mResponseListner.onFailure(t);
            }
        });
    }

    public interface ProfileInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @GET("GetUserProfile")
        Call<List<UserProfileResponse>> getUserProfile();
    }

    public interface UpdateProfileInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @PUT("UpdateUserProfile")
        Call<List<UserUpdateResponse>> updateUserProfile(@Body UpdateUserProfile profiledata);
    }

    public interface ChangePassInterFace {
        @Headers("Content-Type: application/json; charset=utf-8")
        @PUT("UpdateUserPassword")
        Call<List<UserUpdateResponse>> updateUserPass(@Body UserPassword passdata);
    }
}
