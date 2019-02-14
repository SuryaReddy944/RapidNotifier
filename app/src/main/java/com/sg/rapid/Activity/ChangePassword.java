package com.sg.rapid.Activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UserPassword;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.UserProfileServices.UserUpdateResponse;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.SpinnerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Response;

public class ChangePassword extends Activity  implements BottomNavigationView.OnNavigationItemSelectedListener{

    private TextView lblcurrentpass, lblnewpass, lblconfirmpass;
    private EditText mCurrent, mNewpass, mConfirmpass;
    private Button mSave;
    public BottomNavigationView navigationchnagepass;

    private TextView mTitle,mUsername;
    private ImageView mProfilepic;
    private UserProfileResponse mProfileResponse;

    private String base64data = "";
    private byte[] imageBytes;
    private File serverimage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        initViews();
        setFonts(this);
        navigationchnagepass = findViewById(R.id.navigationchangepass);
        View view = navigationchnagepass.findViewById(R.id.navigation_profile);
        view.performClick();
        navigationchnagepass.setOnNavigationItemSelectedListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getProfile(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                View home = HomePage.navigation.findViewById(R.id.navigation_home);
                home.performClick();
                break;

            case R.id.navigation_explore:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                View explore = HomePage.navigation.findViewById(R.id.navigation_explore);
                explore.performClick();
                break;

            case R.id.navigation_reports:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                View reports = HomePage.navigation.findViewById(R.id.navigation_reports);
                reports.performClick();
                break;
            case R.id.navigation_profile:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                break;
        }
        return false;
    }

    private void initViews() {
        lblcurrentpass = (TextView) findViewById(R.id.lblcurrentpass);
        lblnewpass = (TextView) findViewById(R.id.lblnewpass);
        lblconfirmpass = (TextView) findViewById(R.id.lblconfirmpass);
        mCurrent = (EditText) findViewById(R.id.editTextcurrentpass);
        mNewpass = (EditText) findViewById(R.id.editTextnewpass);
        mConfirmpass = (EditText) findViewById(R.id.editTextconfirmpass);
        mSave = (Button) findViewById(R.id.buttonsave);

        mTitle = (TextView)findViewById(R.id.title);
        mUsername   = (TextView)findViewById(R.id.username);
        mProfilepic = (ImageView)findViewById(R.id.profilepicture);
        mTitle.setTypeface(CustomFonts.geRegular(this));
        mUsername.setTypeface(CustomFonts.geRegular(this));

        mSave.setOnClickListener(onClickListener);

    }

    private void setFonts(Context mContext) {
        lblcurrentpass.setTypeface(CustomFonts.getNexaBold(mContext));
        lblnewpass.setTypeface(CustomFonts.getNexaBold(mContext));
        lblconfirmpass.setTypeface(CustomFonts.getNexaBold(mContext));
        mCurrent.setTypeface(CustomFonts.getNexaBold(mContext));
        mNewpass.setTypeface(CustomFonts.getNexaBold(mContext));
        mConfirmpass.setTypeface(CustomFonts.getNexaBold(mContext));
        mSave.setTypeface(CustomFonts.getNexaBold(mContext));
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.buttonsave:
                    saveButtonClicked();
                    break;

                default:
                    break;
            }
        }
    };

    private void saveButtonClicked() {
        String currentpass = mCurrent.getText().toString();
        String newpass = mNewpass.getText().toString();
        String confirm = mConfirmpass.getText().toString();

        if (currentpass.isEmpty()) {
            mCurrent.setError(getString(R.string.error_field_required));
            mCurrent.requestFocus();
            return;
        }
        if (newpass.isEmpty()) {
            mNewpass.setError(getString(R.string.error_field_required));
            mNewpass.requestFocus();
            return;
        }

        if (confirm.isEmpty()) {
            mConfirmpass.setError(getString(R.string.error_field_required));
            mConfirmpass.requestFocus();
            return;
        }

        if (!confirm.equalsIgnoreCase(newpass)) {
            mConfirmpass.setError(getString(R.string.error_password_match));
            mConfirmpass.requestFocus();
        }

        UserPassword mData = new UserPassword();
        mData.setUserPassword(currentpass);
        mData.setUserNewPassword(newpass);
        if (Helper.hasNetworkConnection(ChangePassword.this)) {
            updateUserPass(mData, ChangePassword.this);
        } else {
            Toast.makeText(ChangePassword.this, R.string.noconnection, Toast.LENGTH_LONG).show();

        }


    }


    private void updateUserPass(UserPassword mUserPassword, final Context mContext) {
        SpinnerManager.showSpinner(mContext, "Loading...");
        UserProfileService.updatePass(mUserPassword, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<UserUpdateResponse>> mRes = (Response<List<UserUpdateResponse>>)response;
                List<UserUpdateResponse> mData = mRes.body();
                Toast.makeText(ChangePassword.this,""+mData.get(0).getMessage(),Toast.LENGTH_LONG).show();
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
                Toast.makeText(ChangePassword.this,""+mData.get(0).getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    private void getProfile(final Context mContext) {
        // SpinnerManager.showSpinner(mContext, "Loading");
        UserProfileService.fetchProfile(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                // SpinnerManager.hideSpinner(mContext);
                Response<List<UserProfileResponse>> mres = (Response<List<UserProfileResponse>>) response;
                List<UserProfileResponse> mData = mres.body();
                mProfileResponse = mData.get(0);
                mUsername.setText(mProfileResponse.getUserName());

                try {
                    base64data = mProfileResponse.getProfilepicture().toString();
                    if (base64data != null && base64data != "") {
                        imageBytes = Base64.decode(base64data, Base64.DEFAULT);
                        serverimage = new File(mContext.getCacheDir(), "Profilepic");
                        serverimage.createNewFile();

                        FileOutputStream fos = new FileOutputStream(serverimage);
                        fos.write(imageBytes);
                        fos.flush();
                        fos.close();
                        ImageCapture.loadprofilepic(Uri.fromFile(serverimage),mProfilepic,mContext);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);
            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                // Toast.makeText(UpdateProfile.this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();

            }
        });

    }
}
