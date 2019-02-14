package com.sg.rapid.Activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.Adapters.SeverityReportAdapter;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.R;
import com.sg.rapid.ReportServices.OccuranceRequest;
import com.sg.rapid.ReportServices.OccuranceService;
import com.sg.rapid.ReportServices.SeverityResultData;
import com.sg.rapid.ReportServices.SeverityResultResponse;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.SpinnerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Response;

public class FilteredResult extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public BottomNavigationView navigationoccuranceresults;
    private RecyclerView mReportsData;
    private TextView mNodata;
    private SeverityReportAdapter mSeverityReportAdapter;

    private TextView mTitle,mUsername;
    private ImageView mProfilepic;
    private UserProfileResponse mProfileResponse;

    private String base64data = "";
    private byte[] imageBytes;
    private File serverimage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.occurance_results);
        OccuranceRequest mRequest = (OccuranceRequest)getIntent().getExtras().getSerializable("requestdate");
        navigationoccuranceresults = findViewById(R.id.navigationoccuranceresults);
        View view = navigationoccuranceresults.findViewById(R.id.navigation_reports);
        view.performClick();
        navigationoccuranceresults.setOnNavigationItemSelectedListener(this);
        initViews();
        if(Helper.hasNetworkConnection(this)) {


            showResults(this, mRequest);
        }else {
            Toast.makeText(this, R.string.noconnection, Toast.LENGTH_LONG).show();

        }

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
                View reports = HomePage.navigation.findViewById(R.id.navigation_explore);
                reports.performClick();
                break;

            case R.id.navigation_reports:
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

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
        mReportsData = (RecyclerView)findViewById(R.id.recycler_reportlist);
        mNodata = (TextView)findViewById(R.id.noresultdata);
        mNodata.setTypeface(CustomFonts.getNexaBold(this));

        mTitle = (TextView)findViewById(R.id.title);
        mUsername   = (TextView)findViewById(R.id.username);
        mProfilepic = (ImageView)findViewById(R.id.profilepicture);
        mTitle.setTypeface(CustomFonts.geRegular(this));
        mUsername.setTypeface(CustomFonts.geRegular(this));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mReportsData.setLayoutManager(mLayoutManager);
    }

    private void showResults(final Context mContext, OccuranceRequest data){
        SpinnerManager.showSpinner(mContext,"Loading...");
        OccuranceService.getFilteredResult(data, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<SeverityResultResponse>> mRes = (Response<List<SeverityResultResponse>>)response ;
                List<SeverityResultResponse> mData = mRes.body();

                SeverityResultResponse dataobj = mData.get(0);
                List<SeverityResultData> mLsit = dataobj.getData();

                if(mLsit.size() >0){
                    mSeverityReportAdapter = new SeverityReportAdapter(mContext,mLsit);
                    mReportsData.setVisibility(View.VISIBLE);
                    mReportsData.setAdapter(mSeverityReportAdapter);
                    mSeverityReportAdapter.notifyDataSetChanged();

                }else {
                    mReportsData.setVisibility(View.GONE);
                    mNodata.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);

            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);

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
