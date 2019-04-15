package com.sg.rapid.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sg.rapid.AlaramService.AlaramResponse;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.Dialogs.UnAckDialog;
import com.sg.rapid.EventServices.EventsResponse;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.DateUtils;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.SpinnerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Response;

public class EventDeatilsPage extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private TextView mAlaramName,mAlaramType,mDescription,mCreateddate,mCreatedTime,mAckNotesBy,ackdateandtime,acknote,lblalaramdetail,lblpriority,lblackdetails,lblstatus;
    private EventsResponse mEventsResponse;
    public  BottomNavigationView mNavigation;
    private LinearLayout llacktitle,llacksection;

    private TextView mTitle,mUsername;
    private ImageView mProfilepic;
    private UserProfileResponse mProfileResponse;

    private String base64data = "";
    private byte[] imageBytes;
    private File serverimage;
    private Button mUnack;
    private SharedPreferences mPref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alara_detailed_page);
        initViews();
        setFonts(this);
        mPref  = PreferenceManager.getDefaultSharedPreferences(this);

        mEventsResponse = (EventsResponse)getIntent().getSerializableExtra("eventInfo");
        mNavigation = findViewById(R.id.navigationdetailed);
        mNavigation.setOnNavigationItemSelectedListener(this);


        if(mEventsResponse.getAPriority().equalsIgnoreCase("High")){
            lblstatus.setBackgroundResource(R.drawable.fullcircle_red);
            lblpriority.setText("High priority");
        }else if(mEventsResponse.getAPriority().equalsIgnoreCase("Normal")){
            lblstatus.setBackgroundResource(R.drawable.fullcircle_green);
            lblpriority.setText("Normal priority");
        }else if(mEventsResponse.getAPriority().equalsIgnoreCase("Low")){
            lblstatus.setBackgroundResource(R.drawable.fullcircle_yello);
            lblpriority.setText("Low priority");
        }else if(mEventsResponse.getAPriority().equalsIgnoreCase("Medium")){
            lblstatus.setBackgroundResource(R.drawable.fullcircle_orange);
            lblpriority.setText("Medium priority");
        }else{
            lblstatus.setBackgroundResource(R.drawable.fullcircle_green);
        }
        mAlaramName.setText("Asset : " +mEventsResponse.getAssetName());
        mAlaramType.setText("Subsystem : " +mEventsResponse.getAssetType());
        mDescription.setText("Description : " +mEventsResponse.getEventDecription());
        mCreateddate.setText("Event date/time : " + DateUtils.getDay(mEventsResponse.getCreateDate())+" "+DateUtils.getTime(mEventsResponse.getCreateDate()));

        String note = (String) mEventsResponse.getAcknowledgeNotes();
        if(note != null){
            mAckNotesBy.setText("Acknowledgement By: " + mEventsResponse.getUserName());
            ackdateandtime.setText("Acknowledgement On : " + DateUtils.getDay((String) mEventsResponse.getAcknowledgedOn())+" "+DateUtils.getTime(mEventsResponse.getCreateDate()));
            acknote.setText("Notes : " + note);

        }else{
            llacksection.setVisibility(View.GONE);
            llacktitle.setVisibility(View.GONE);

        }

        String username = mPref.getString("UserName","");
        if(null != note && username.equalsIgnoreCase(mEventsResponse.getUserName())){
            mUnack.setVisibility(View.VISIBLE);

        }else {
            mUnack.setVisibility(View.GONE);

        }



        mUnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnAckDialog mDialog = new UnAckDialog(EventDeatilsPage.this,String.valueOf(mEventsResponse.getEventLogId()),false);
                mDialog.show();
            }
        });


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
                View profile = HomePage.navigation.findViewById(R.id.navigation_profile);
                profile.performClick();
                break;
        }

        return false;
    }

    private void initViews(){
        mAlaramName = (TextView)findViewById(R.id.alaramname);
        mAlaramType = (TextView)findViewById(R.id.alaramtype);
        mDescription = (TextView)findViewById(R.id.alaramdescription);
        mCreateddate = (TextView)findViewById(R.id.createddate);
        mAckNotesBy = (TextView)findViewById(R.id.ackby);
        ackdateandtime = (TextView)findViewById(R.id.ackdateandtime);
        acknote = (TextView)findViewById(R.id.notesdata);
        lblalaramdetail = (TextView)findViewById(R.id.lbltitle);
        lblpriority = (TextView)findViewById(R.id.lblpriority);
        lblackdetails = (TextView)findViewById(R.id.lblackdetails);
        llacktitle = (LinearLayout)findViewById(R.id.llacktitle);
        llacksection  = (LinearLayout)findViewById(R.id.acklayout);
        lblstatus = (TextView)findViewById(R.id.colorcircle);
        mUnack = (Button)findViewById(R.id.buttonunack);
        mTitle = (TextView)findViewById(R.id.title);
        mUsername   = (TextView)findViewById(R.id.username);
        mProfilepic = (ImageView)findViewById(R.id.profilepicture);
        mTitle.setTypeface(CustomFonts.geRegular(this));
        mUsername.setTypeface(CustomFonts.geRegular(this));


    }
    private void setFonts(Context mContext){
        mAlaramName.setTypeface(CustomFonts.getNexaBold(mContext));
        mAlaramType.setTypeface(CustomFonts.getNexaBold(mContext));
        mDescription.setTypeface(CustomFonts.getNexaBold(mContext));
        mCreateddate.setTypeface(CustomFonts.getNexaBold(mContext));
        mAckNotesBy.setTypeface(CustomFonts.getNexaBold(mContext));
        ackdateandtime.setTypeface(CustomFonts.getNexaBold(mContext));
        acknote.setTypeface(CustomFonts.getNexaBold(mContext));
        lblackdetails.setTypeface(CustomFonts.getNexaBold(mContext));
        lblpriority.setTypeface(CustomFonts.getNexaBold(mContext));
        lblackdetails.setTypeface(CustomFonts.getNexaBold(mContext));
        mUnack.setTypeface(CustomFonts.getNexaBold(mContext));
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

