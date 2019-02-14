package com.sg.rapid.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.Adapters.AdminNoticeAdapter;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.CustomControllers.SwipeControllerActions;
import com.sg.rapid.CustomControllers.SwipeDeleteController;
import com.sg.rapid.CustomControllers.SwipeHelper;
import com.sg.rapid.NoticeServices.AdminNotice;
import com.sg.rapid.NoticeServices.AdminNotices;
import com.sg.rapid.NoticeServices.NoticeService;
import com.sg.rapid.R;
import com.sg.rapid.UserProfileServices.UserProfileResponse;
import com.sg.rapid.UserProfileServices.UserProfileService;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.ImageCapture;
import com.sg.rapid.Utilities.SpinnerManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Response;

public class MyNoticeBoard extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public BottomNavigationView navigationupdateprofile;
    private TextView  lblmynotice,lblnodata;
    private Button mNew;
    private RecyclerView mNoticeList;
    private AdminNoticeAdapter mAdapter;
    private List<AdminNotices> mData;
    private SwipeDeleteController mDeleteController;

    private TextView mTitle,mUsername;
    private ImageView mProfilepic;
    private UserProfileResponse mProfileResponse;

    private String base64data = "";
    private byte[] imageBytes;
    private File serverimage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_noticeboard);
        navigationupdateprofile = findViewById(R.id.navigationmynotice);
        View view = navigationupdateprofile.findViewById(R.id.navigation_explore);
        view.performClick();
        navigationupdateprofile.setOnNavigationItemSelectedListener(this);
        initView();
        setFonts(this);
        mNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mExplore = new Intent(MyNoticeBoard.this, CreateNotice.class);
                startActivity(mExplore);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                finish();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mNoticeList.setLayoutManager(mLayoutManager);
        mDeleteController = new SwipeDeleteController(this, new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                super.onRightClicked(position);

                AdminNotices adminNotice = mData.get(position);
                DeleteAdminNotice mDialog = new DeleteAdminNotice((Activity) MyNoticeBoard.this, String.valueOf(adminNotice.getID()));
                mDialog.show();

            }

            @Override
            public void onLeftClicked(int position) {
                return;
                // super.onLeftClicked(position);

            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(mDeleteController);
        itemTouchhelper.attachToRecyclerView(mNoticeList);
        mNoticeList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                mDeleteController.onDraw(c);
            }
        });


       /* SwipeHelper swipeHelper = new SwipeHelper(this, mNoticeList) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "DELETE",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int position) {
                                // TODO: onDelete

                                AdminNotices adminNotice = mData.get(position);

                                DeleteAdminNotice mDialog = new DeleteAdminNotice((Activity) MyNoticeBoard.this, String.valueOf(adminNotice.getID()));
                                mDialog.show();

                            }
                        }
                ));


            }
        };*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotices(this);
        getProfile(this);
    }

    private void initView() {
        lblmynotice = (TextView) findViewById(R.id.lblmynotice);
        mNew = (Button) findViewById(R.id.btnnew);
        lblnodata = (TextView) findViewById(R.id.nodata);
        mNoticeList = (RecyclerView) findViewById(R.id.recycler_viewadminnotice);

        mTitle = (TextView)findViewById(R.id.title);
        mUsername   = (TextView)findViewById(R.id.username);
        mProfilepic = (ImageView)findViewById(R.id.profilepicture);
        mTitle.setTypeface(CustomFonts.geRegular(this));
        mUsername.setTypeface(CustomFonts.geRegular(this));

    }

    private void setFonts(Context mContext) {
        lblmynotice.setTypeface(CustomFonts.getNexaBold(mContext));
        mNew.setTypeface(CustomFonts.getNexaBold(mContext));
        lblnodata.setTypeface(CustomFonts.getNexaBold(mContext));

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

    private void getNotices(final Context mContext) {
        SpinnerManager.showSpinner(mContext, "Loading...");
        NoticeService.fetchNotice(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(mContext);
                Response<List<AdminNotice>> mRes = (Response<List<AdminNotice>>) response;
                List<AdminNotice>     mNotice = mRes.body();
                mData = mNotice.get(0).getData();
                if(mData.size() > 0){
                    lblnodata.setVisibility(View.GONE);
                    mAdapter = new AdminNoticeAdapter(MyNoticeBoard.this, mData);
                    mNoticeList.setAdapter(mAdapter);

                }else {
                    mNoticeList.setVisibility(View.GONE);
                    lblnodata.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);

            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                Toast.makeText(mContext, "Server error, please try again later", Toast.LENGTH_LONG).show();

            }
        });
    }


    public class DeleteAdminNotice extends Dialog implements
            View.OnClickListener {


        public Activity c;
        private TextView mTitle, mDes;
        private Button mCancel, mGo;
        private String mId;

        public DeleteAdminNotice(Activity a, String id) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;

            mId = id;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.delete_admin_notice);
            initViews();
            applyFonts();


        }

        private void initViews() {
            mTitle = (TextView) findViewById(R.id.promotitle);
            mDes = (TextView) findViewById(R.id.promodes);

            mCancel = (Button) findViewById(R.id.buttoncancel);
            mGo = (Button) findViewById(R.id.buttongo);
            mCancel.setOnClickListener(this);
            mGo.setOnClickListener(this);


        }

        private void applyFonts() {
            mTitle.setTypeface(CustomFonts.getNexaBold(c));
            mDes.setTypeface(CustomFonts.getNexaRegular(c));

            mCancel.setTypeface(CustomFonts.getNexaRegular(c));
            mGo.setTypeface(CustomFonts.getNexaRegular(c));
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.buttoncancel:
                    dismiss();
                    break;
                case R.id.buttongo:
                    deleteNotice(c, mId);

                    break;


                default:
                    break;
            }
            //dismiss();
        }

        private void deleteNotice(final Context mContext, String id) {
            SpinnerManager.showSpinner(mContext, "Deleting...");

            NoticeService.deleteUserNotice(id, new ResponseListner() {
                @Override
                public void onSucess(Object response, int sttuscode) {
                    SpinnerManager.hideSpinner(mContext);
                    dismiss();
                    getNotices(c);
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
