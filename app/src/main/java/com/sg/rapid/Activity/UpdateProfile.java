package com.sg.rapid.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.Fragments.ProfileFragment;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UpdateUserProfile;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.UserProfileServices.UserUpdateResponse;
import com.sg.rapid.Utilities.CircleTransform;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.OptionGalleryCamera;
import com.sg.rapid.Utilities.SpinnerManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private TextView mHeader, mlabelemail, mlabelphone, mlablealerts, mlblprofilepic;
    private Button mSave;
    private CheckBox mChPushNotifications, mChEmail, mChSMS;
    private EditText mEmail, mPhone;
    private UserProfileResponse mProfileResponse;
    public BottomNavigationView navigationupdateprofile;
    private ImageView mProfilePic;
    private static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private String base64Image = "";

    private String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE


    };
    private String base64data = "";
    private byte[] imageBytes;
    private ImageCapture mCapture;
    private File serverimage;

    private TextView mTitle,mUsername;
    private ImageView mProfilepic;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateprofile);
        initViews();
        setFonts(this);
        navigationupdateprofile = findViewById(R.id.navigationupdateprofile);
        View view = navigationupdateprofile.findViewById(R.id.navigation_profile);
        view.performClick();
        navigationupdateprofile.setOnNavigationItemSelectedListener(this);
        mCapture = new ImageCapture();


        if (Helper.hasNetworkConnection(this)) {
            getProfile(this);
        } else {
            Toast.makeText(this, R.string.noconnection, Toast.LENGTH_LONG).show();

        }


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
        mHeader = (TextView) findViewById(R.id.title);
        mlabelemail = (TextView) findViewById(R.id.lblemail);
        mlabelphone = (TextView) findViewById(R.id.lblphone);
        mlablealerts = (TextView) findViewById(R.id.lblalerts);
        mlblprofilepic = (TextView) findViewById(R.id.profilepic);
        mSave = (Button) findViewById(R.id.buttonsave);
        mChPushNotifications = (CheckBox) findViewById(R.id.chpush);
        mChEmail = (CheckBox) findViewById(R.id.chemail);
        mChSMS = (CheckBox) findViewById(R.id.chsms);
        mEmail = (EditText) findViewById(R.id.editTextemail);
        mPhone = (EditText) findViewById(R.id.editTextphone);
        mProfilePic = (ImageView) findViewById(R.id.imageprofilepicture);

        mTitle = (TextView)findViewById(R.id.title);
        mUsername   = (TextView)findViewById(R.id.username);
        mProfilepic = (ImageView)findViewById(R.id.profilepicture);
        mTitle.setTypeface(CustomFonts.geRegular(this));
        mUsername.setTypeface(CustomFonts.geRegular(this));

        mSave.setOnClickListener(onClickListener);
        mProfilePic.setOnClickListener(onClickListener);

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
        mlblprofilepic.setTypeface(CustomFonts.getNexaBold(mContext));
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.buttonsave:
                    if (Helper.hasNetworkConnection(UpdateProfile.this)) {
                        updateProfile();
                    } else {
                        Toast.makeText(UpdateProfile.this, R.string.noconnection, Toast.LENGTH_LONG).show();

                    }
                    break;

                case R.id.imageprofilepicture:
                    if (checkPermissions()) {
                        mCapture.chooseImage(UpdateProfile.this, 20);
                    } else {

                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void updateProfile() {
        String email = mEmail.getText().toString();
        String mphone = mPhone.getText().toString();
        try{
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
        if (mChPushNotifications.isChecked()) {
            mData.setPush_Notification_Alert(true);
        } else {
            mData.setPush_Notification_Alert(false);
        }

       /* if (mChEmail.isChecked()) {
            mData.setEmail_Alert(true);
        } else {
            mData.setEmail_Alert(false);
        }
        if (mChSMS.isChecked()) {
            mData.setSMS_Alert(true);
        } else {
            mData.setSMS_Alert(false);
        }*/

          if (OptionGalleryCamera.getInstance().getWhichImage().equalsIgnoreCase("Camera") || OptionGalleryCamera.getInstance().getWhichImage().equalsIgnoreCase("Gallery")) {

              final InputStream imageStream = getContentResolver().openInputStream(mCapture.fileUri);
              final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
              base64Image = encodeImage(selectedImage);
              mData.setProfile_Picture(base64Image);
          } else {
              Bitmap bm = BitmapFactory.decodeFile(serverimage.getPath());
              ByteArrayOutputStream bOut = new ByteArrayOutputStream();
              bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut);
              base64Image = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT);
              mData.setProfile_Picture(mProfileResponse.getProfilepicture());
          }


            updateUserProfile(UpdateProfile.this, mData);

      }catch (Exception e){
            e.printStackTrace();
      }




    }

    private void getProfile(final Context mContext) {
        SpinnerManager.showSpinner(mContext, "Loading");
        UserProfileService.fetchProfile(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<UserProfileResponse>> mres = (Response<List<UserProfileResponse>>) response;
                List<UserProfileResponse> mData = mres.body();
                mProfileResponse = mData.get(0);
                mUsername.setText(mProfileResponse.getUserName());
                mEmail.setText(mProfileResponse.getEmailAddress());
                mPhone.setText(mProfileResponse.getTelNo());
              //  mChEmail.setChecked(mProfileResponse.getEmailAlert());
                if(mProfileResponse.getPushNotificationAlert() != null){
                    mChPushNotifications.setChecked(mProfileResponse.getPushNotificationAlert());
                }
              //  mChSMS.setChecked(mProfileResponse.getSMSAlert());

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
                        ImageCapture.loadImageWithPicasso(Uri.fromFile(serverimage),mProfilePic,mContext);
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
                Toast.makeText(UpdateProfile.this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();

            }
        });

    }


    private void updateUserProfile(final Context mContext, final UpdateUserProfile profileResponse) {
        SpinnerManager.showSpinner(mContext, "Loading");
        UserProfileService.updateUser(profileResponse, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<UserUpdateResponse>> mRes = (Response<List<UserUpdateResponse>>) response;
                List<UserUpdateResponse> mData = mRes.body();
                Toast.makeText(UpdateProfile.this, "" + mData.get(0).getMessage(), Toast.LENGTH_LONG).show();
                finish();

            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);
            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<UserUpdateResponse>> mRes = (Response<List<UserUpdateResponse>>) response;
                List<UserUpdateResponse> mData = mRes.body();
                Toast.makeText(UpdateProfile.this, "" + mData.get(0).getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (OptionGalleryCamera.getInstance().getWhichImage().equals("Gallery")) {
                try {


                    mCapture.fileUri  = data.getData();

                   mCapture.loadImageWithPicasso( mCapture.fileUri, mProfilePic,UpdateProfile.this);


                } catch (Exception e) {
                  e.printStackTrace();
                }
            } else if (OptionGalleryCamera.getInstance().getWhichImage().equals("Camera")) {
                try {


                    if (mCapture.file.length() != 0) {

                       mCapture.loadImageWithPicasso(mCapture.fileUri, mProfilePic,UpdateProfile.this);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }




    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,80,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


}


