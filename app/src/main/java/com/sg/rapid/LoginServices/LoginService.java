package com.sg.rapid.LoginServices;

import com.sg.rapid.Activity.LoginScreen;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.ServiceLayer.BaseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class LoginService extends BaseService {

    /**
     * this method is used for login user
     * @param mUserInfo
     * @param mResponseListner
     */
    public static void userLogin(final UserInfo mUserInfo, final ResponseListner mResponseListner) {

        LoginInterface mLoginInterface = retrofit.create(LoginInterface.class);

        Call<LoginResponse> mCall = mLoginInterface.makeLogin(mUserInfo);
        mCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                mResponseListner.onFailure(t);

            }

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.isSuccessful()){

                    mResponseListner.onSucess(response,response.code());
                }else{
                    mResponseListner.failureResponse(response);
                }
            }
        });

    }

    public static void sendFirebaseToken(final TokenModel model,final ResponseListner responseListner){
        FCMTokenInterface fcmTokenInterface = retrofit.create(FCMTokenInterface.class);
        Call<List<TokenResponse>> mCall = fcmTokenInterface.sendToken(model);
        mCall.enqueue(new Callback<List<TokenResponse>>() {
            @Override
            public void onFailure(Call<List<TokenResponse>> call, Throwable t) {
                responseListner.onFailure(t);
            }

            @Override
            public void onResponse(Call<List<TokenResponse>> call, Response<List<TokenResponse>> response) {
                if(response.isSuccessful()){
                    responseListner.onSucess(response,response.code());
                }
            }
        });
    }


    public interface LoginInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("UserLogin")
        Call<LoginResponse> makeLogin(@Body UserInfo mUserInfo);
    }


    public interface FCMTokenInterface{
        @Headers("Content-Type: application/json; charset=utf-8")
        @POST("UpdateUserKeys")
        Call<List<TokenResponse>> sendToken(@Body TokenModel tokenModel);
    }


}
