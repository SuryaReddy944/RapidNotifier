package com.sg.rapid.FireBaseServices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.LoginServices.LoginService;
import com.sg.rapid.LoginServices.TokenModel;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);
        Log.e(TAG, "Firebase reg id: " + refreshedToken);
        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
        TokenModel mModel = new TokenModel();
        mModel.setDeviceId("Android");
        mModel.setFCM_Token(token);
        sendFCMToken(this,mModel);
    }

    private void sendFCMToken(final Context mContext, final TokenModel model){

        LoginService.sendFirebaseToken(model, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                if(sttuscode ==200){

                }else{

                }
            }

            @Override
            public void onFailure(Throwable error) {
                error.printStackTrace();
            }

            @Override
            public void failureResponse(Object response) {

            }
        });

    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();
    }
}
