package com.sg.rapid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.Activity.AlaramDetailedPage;
import com.sg.rapid.Activity.ChangePassword;
import com.sg.rapid.Activity.SplashScreen;
import com.sg.rapid.Activity.UpdateProfile;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.Dialogs.LogOutDialog;
import com.sg.rapid.FireBaseServices.Config;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.Utilities.CircleTransform;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.OptionGalleryCamera;
import com.sg.rapid.Utilities.SpinnerManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Response;

public class ProfileFragment extends Fragment {

    public static SharedPreferences pref;
    private Button mUpdateProfile, mChangepass, mLogout;
    private TextView  mUsername,mPhoneNumber;
    private ImageView mProfilePic;
    private UserProfileResponse mProfileResponse;
    private String base64data = "";
    private  byte[]  imageBytes;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.profile_frag, null);
        mUpdateProfile = (Button) mView.findViewById(R.id.updateprofile);
        mChangepass = (Button) mView.findViewById(R.id.updatepass);
        mLogout = (Button) mView.findViewById(R.id.logout);

        mUsername = (TextView) mView.findViewById(R.id.myname);
        mProfilePic = (ImageView) mView.findViewById(R.id.mypic);
        mPhoneNumber = (TextView) mView.findViewById(R.id.phone);

        mUpdateProfile.setTypeface(CustomFonts.getNexaBold(getContext()));
        mChangepass.setTypeface(CustomFonts.getNexaBold(getContext()));
        mLogout.setTypeface(CustomFonts.getNexaBold(getContext()));
        mUsername.setTypeface(CustomFonts.getNexaBold(getContext()));
        mPhoneNumber.setTypeface(CustomFonts.getNexaBold(getContext()));


        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

        mUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateprofile = new Intent(getActivity(), UpdateProfile.class);
                startActivity(updateprofile);
                getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        mChangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changepass = new Intent(getActivity(), ChangePassword.class);
                startActivity(changepass);
                getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOutDialog mdialog = new LogOutDialog(getActivity());
                mdialog.show();
            }
        });



        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();

        getProfile(getActivity());
    }

    private void getProfile(final Context mContext) {
        SpinnerManager.showSpinner(mContext, "Loading");
        UserProfileService.fetchProfile(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<UserProfileResponse>> mres = (Response<List<UserProfileResponse>>) response;
                List<UserProfileResponse> mData = mres.body();
                base64data = null;
                mProfileResponse = mData.get(0);
                mUsername.setText("UserName : " + mProfileResponse.getEmailAddress());
                mPhoneNumber.setText("Phone Number: " + mProfileResponse.getTelNo());
               try {
                    base64data = mProfileResponse.getProfilepicture().toString();
                   if(base64data != null && base64data != ""){
                       imageBytes = Base64.decode(base64data, Base64.DEFAULT);
                       File f = new File(mContext.getCacheDir(), "Profilepic");
                       f.createNewFile();

                       FileOutputStream fos = new FileOutputStream(f);
                       fos.write(imageBytes);
                       fos.flush();
                       fos.close();
                       ImageCapture.loadImageWithPicasso(Uri.fromFile(f),mProfilePic,getActivity());
                   }


               }catch (Exception e){
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
                Toast.makeText(mContext, "Something went wrong, please try again", Toast.LENGTH_LONG).show();

            }
        });

    }
}