package com.sg.rapid.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UpdateUserProfile;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.UserProfileServices.UserUpdateResponse;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.SpinnerManager;

import java.util.List;

import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {

    private ImageView mBack;
    private TextView mHeader, mlabelemail, mlabelphone, mlablealerts;
    private Button mSave;
    private CheckBox mChPushNotifications, mChEmail, mChSMS;
    private EditText mEmail, mPhone;
    private UserProfileResponse mProfileResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateprofile);
        initViews();
        setFonts(this);
if(Helper.hasNetworkConnection(this)) {
    getProfile(this);
}else {
    Toast.makeText(this, R.string.noconnection,Toast.LENGTH_LONG).show();

}


    }


    private void initViews() {
        mBack = (ImageView) findViewById(R.id.backbutton);
        mHeader = (TextView) findViewById(R.id.title);
        mlabelemail = (TextView) findViewById(R.id.lblemail);
        mlabelphone = (TextView) findViewById(R.id.lblphone);
        mlablealerts = (TextView) findViewById(R.id.lblalerts);
        mSave = (Button) findViewById(R.id.buttonsave);
        mChPushNotifications = (CheckBox) findViewById(R.id.chpush);
        mChEmail = (CheckBox) findViewById(R.id.chemail);
        mChSMS = (CheckBox) findViewById(R.id.chsms);
        mEmail = (EditText) findViewById(R.id.editTextemail);
        mPhone = (EditText) findViewById(R.id.editTextphone);
        mSave.setOnClickListener(onClickListener);
        mBack.setOnClickListener(onClickListener);

    }

    private void setFonts(Context mContext) {
        mHeader.setTypeface(CustomFonts.getNexaBold(mContext));
        mlabelemail.setTypeface(CustomFonts.getNexaBold(mContext));
        mlabelphone.setTypeface(CustomFonts.getNexaBold(mContext));
        mlablealerts.setTypeface(CustomFonts.getNexaBold(mContext));
        mSave.setTypeface(CustomFonts.getNexaBold(mContext));
        mChPushNotifications.setTypeface(CustomFonts.getNexaBold(mContext));
        mChEmail.setTypeface(CustomFonts.getNexaBold(mContext));
        mChSMS.setTypeface(CustomFonts.getNexaBold(mContext));
        mEmail.setTypeface(CustomFonts.getNexaBold(mContext));
        mPhone.setTypeface(CustomFonts.getNexaBold(mContext));
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.buttonsave:
                    if(Helper.hasNetworkConnection(UpdateProfile.this)) {
                        updateProfile();
                    }else {
                        Toast.makeText(UpdateProfile.this, R.string.noconnection,Toast.LENGTH_LONG).show();

                    }
                    break;

                case R.id.backbutton:
                   finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void updateProfile() {
        String email = mEmail.getText().toString();
        String mphone = mPhone.getText().toString();
        if (email.isEmpty()) {
            mEmail.setError(getString(R.string.error_field_required));
            mEmail.requestFocus();
            return;
        }
        if (mphone.isEmpty()) {
            mPhone.setError(getString(R.string.error_field_required));
            mPhone.requestFocus();
            return;
        }

        UpdateUserProfile mData = new UpdateUserProfile();
        mData.setEmailAddress(email);
        mData.setTelNo(mphone);
        mData.setUserId(String.valueOf(mProfileResponse.getUserId()));
        if(mChPushNotifications.isChecked()){
            mData.setPush_Notification_Alert(true);
        }else{
            mData.setPush_Notification_Alert(false);
        }

        if(mChEmail.isChecked()){
            mData.setEmail_Alert(true);
        }else {
            mData.setEmail_Alert(false);
        }
        if(mChSMS.isChecked()){
            mData.setSMS_Alert(true);
        }else {
            mData.setSMS_Alert(false);
        }

        updateUserProfile(UpdateProfile.this,mData);


    }

    private void getProfile(final Context mContext){
        SpinnerManager.showSpinner(mContext,"Loading");
        UserProfileService.fetchProfile(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
             SpinnerManager.hideSpinner(mContext);
                Response<List<UserProfileResponse>> mres = (Response<List<UserProfileResponse>>)response;
                List<UserProfileResponse> mData = mres.body();
                mProfileResponse = mData.get(0);
                mEmail.setText(mData.get(0).getEmailAddress());
                mPhone.setText(mData.get(0).getTelNo());
            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);
            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                Toast.makeText(UpdateProfile.this,"Something went wrong, please try again",Toast.LENGTH_LONG).show();

            }
        });

    }


    private void updateUserProfile(final Context mContext,final UpdateUserProfile profileResponse ){
        SpinnerManager.showSpinner(mContext,"Loading");
        UserProfileService.updateUser(profileResponse, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<UserUpdateResponse>> mRes = (Response<List<UserUpdateResponse>>)response;
                List<UserUpdateResponse> mData = mRes.body();
                Toast.makeText(UpdateProfile.this,""+mData.get(0).getMessage(),Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);
            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<UserUpdateResponse>> mRes = (Response<List<UserUpdateResponse>>)response;
                List<UserUpdateResponse> mData = mRes.body();
                Toast.makeText(UpdateProfile.this,""+mData.get(0).getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
