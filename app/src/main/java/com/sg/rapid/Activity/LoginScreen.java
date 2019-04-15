package com.sg.rapid.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.FireBaseServices.Config;
import com.sg.rapid.FireBaseServices.NotificationUtils;
import com.sg.rapid.LoginServices.LoginResponse;
import com.sg.rapid.LoginServices.LoginService;
import com.sg.rapid.LoginServices.TokenModel;

import com.sg.rapid.LoginServices.TokenResponse;
import com.sg.rapid.LoginServices.UserInfo;
import com.sg.rapid.Models.AlaramData;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.SpinnerManager;

import java.util.List;

import retrofit2.Response;

/**
 * Created by Surya on 25-10-2018.
 */
public class LoginScreen extends AppCompatActivity {

    private static final String TAG ="LoginPage";

    private TextView mLabel, mForgetpass;

    private EditText mEmail, mPass;

    private Button mSignIn;

    private CheckBox mRememberme;

    private  static SharedPreferences pref;


    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        this.initViews();
        this.applyFonts();
       pref  = PreferenceManager.getDefaultSharedPreferences(this);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();


                }
            }
        };

        displayFirebaseRegId();


    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);


    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void initViews() {
        mLabel = (TextView) findViewById(R.id.txtlabel);
        mForgetpass = (TextView) findViewById(R.id.forgetpass);
        mEmail = (EditText) findViewById(R.id.editTextemail);
        mPass = (EditText) findViewById(R.id.editTextpass);
        mSignIn = (Button) findViewById(R.id.buttonsignin);
        mRememberme = (CheckBox) findViewById(R.id.rememberme);
        mForgetpass.setOnClickListener(myClicklistner);
        mSignIn.setOnClickListener(myClicklistner);

    }

    private void applyFonts() {
        mLabel.setTypeface(CustomFonts.getNexaBold(this));
        mForgetpass.setTypeface(CustomFonts.getNexaBold(this));
        mEmail.setTypeface(CustomFonts.getNexaRegular(this));
        mPass.setTypeface(CustomFonts.getNexaRegular(this));
        mSignIn.setTypeface(CustomFonts.getNexaRegular(this));
        mRememberme.setTypeface(CustomFonts.getNexaRegular(this));

    }

    private View.OnClickListener myClicklistner = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            switch (v.getId()) {
                case R.id.buttonsignin:
                    doLogin();

                    break;
                default:
                    break;
            }
        }
    };


    private void doLogin(){
        String email = mEmail.getText().toString();
        String mpass = mPass.getText().toString();



        if(email.isEmpty()){
            mEmail.setError("Email is mandatory.");
            mEmail.requestFocus();
            return;
        }
        if(mpass.isEmpty()){
            mPass.setError("Please enter password.");
            mPass.requestFocus();
            return;
        }

        UserInfo mUserInfo = new UserInfo();
        mUserInfo.setGrant_type("password");
        mUserInfo.setUsername(email);
        mUserInfo.setPassword(mpass);
        if(Helper.hasNetworkConnection(this)) {
            this.makeUserLogin(this, mUserInfo);
        }else {
            Toast.makeText(this, R.string.noconnection,Toast.LENGTH_LONG).show();

        }

    }

    /**
     * this method is used for login with credentials
     * @Prams : UserInfo
     */
    private void makeUserLogin(final Context mContext,final UserInfo mUserInfo){
        SpinnerManager.showSpinner(mContext,"Loading...");
        LoginService.userLogin(mUserInfo, new ResponseListner() {
            @Override
            public void onSucess(Object response,int statuscode) {
                SpinnerManager.hideSpinner(mContext);
                if(statuscode == 200){
                 Response<LoginResponse> mRes = (Response<LoginResponse>)response;

                 LoginResponse mData = mRes.body();
                 SharedPreferences.Editor edit = pref.edit();
                    edit.putString("token",mData.getAccessToken());
                    edit.apply();
                 TokenModel mModel = new TokenModel();
                 mModel.setDeviceId("Android");
                 mModel.setFCM_Token(getFcmToken());
                 sendFCMToken(mContext,mModel);
                 Intent homepage = new Intent(LoginScreen.this,HomePage.class);
                 startActivity(homepage);
                 finish();
             }else {

                 Toast.makeText(LoginScreen.this,"Provided username and password is incorrect",Toast.LENGTH_LONG).show();
             }
            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);
              error.printStackTrace();

            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                Toast.makeText(LoginScreen.this,"Provided username and password is incorrect",Toast.LENGTH_LONG).show();

            }
        });
    }


    private void sendFCMToken(final Context mContext, final TokenModel model){

        LoginService.sendFirebaseToken(model, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                if(sttuscode ==200){
                    Response<List<TokenResponse>> mres = (Response<List<TokenResponse>>)response;
                    List<TokenResponse> mData = mres.body();
                    TokenResponse mTokenRes = mData.get(0);
                    SharedPreferences.Editor mEditor = pref.edit();
                    mEditor.putString("UserType",mTokenRes.getUserType());
                    mEditor.putString("UserName",mTokenRes.getUsername());
                    SplashScreen.userType = mTokenRes.getUserType();
                    mEditor.apply();
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



    public String getFcmToken(){
        String fcmtoken = "";
        fcmtoken = pref.getString("regId","");
        return fcmtoken;
    }



}
