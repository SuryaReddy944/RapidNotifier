package com.sg.rapid.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UserPassword;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.UserProfileServices.UserUpdateResponse;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.SpinnerManager;

import java.util.List;

import retrofit2.Response;

public class ChangePassword extends Activity {

    private TextView lbltop, lblcurrentpass, lblnewpass, lblconfirmpass;
    private EditText mCurrent, mNewpass, mConfirmpass;
    private Button mSave;
    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        initViews();
        setFonts(this);
    }

    private void initViews() {
        mBack = (ImageView) findViewById(R.id.backbutton);
        lbltop = (TextView) findViewById(R.id.title);
        lblcurrentpass = (TextView) findViewById(R.id.lblcurrentpass);
        lblnewpass = (TextView) findViewById(R.id.lblnewpass);
        lblconfirmpass = (TextView) findViewById(R.id.lblconfirmpass);
        mCurrent = (EditText) findViewById(R.id.editTextcurrentpass);
        mNewpass = (EditText) findViewById(R.id.editTextnewpass);
        mConfirmpass = (EditText) findViewById(R.id.editTextconfirmpass);
        mSave = (Button) findViewById(R.id.buttonsave);
        mSave.setOnClickListener(onClickListener);
        mBack.setOnClickListener(onClickListener);

    }

    private void setFonts(Context mContext) {
        lbltop.setTypeface(CustomFonts.getNexaBold(mContext));
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

                case R.id.backbutton:
                    finish();
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
}
