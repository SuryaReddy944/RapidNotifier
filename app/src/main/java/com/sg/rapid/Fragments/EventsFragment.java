package com.sg.rapid.Fragments;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.rapid.AcknowledgeServices.AckInfo;
import com.sg.rapid.AcknowledgeServices.AlaramAcknowledgeService;
import com.sg.rapid.Activity.EventDeatilsPage;
import com.sg.rapid.Activity.HomePage;
import com.sg.rapid.CallBacks.RecyclerTouchListener;
import com.sg.rapid.CallBacks.RecyclerViewClickListener;
import com.sg.rapid.CallBacks.ResponseListner;
import com.sg.rapid.CountServices.CountService;
import com.sg.rapid.CountServices.EventCountResponse;
import com.sg.rapid.CustomControllers.SwipeController;
import com.sg.rapid.CustomControllers.SwipeControllerActions;
import com.sg.rapid.EventServices.EventService;
import com.sg.rapid.EventServices.EventsResponse;
import com.sg.rapid.EventsAdapter.EventSectionAdapter;
import com.sg.rapid.EventsAdapter.EventSectionHeader;
import com.sg.rapid.Models.AlaramsRequest;
import com.sg.rapid.R;
import com.sg.rapid.Utilities.CustomFonts;
import com.sg.rapid.Utilities.Helper;
import com.sg.rapid.Utilities.SpinnerManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static com.sg.rapid.Fragments.NotificationsFragment.lbleventcount;

public class EventsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recyclerView;
    private EventSectionAdapter adapterRecycler;
    private List<EventSectionHeader> sections;
    private List<EventsResponse> childList;
    private SwipeController swipeController;

    private LinearLayoutManager linearLayoutManager;
    private boolean loading = true;
    private boolean isack = false;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    public int startNo = 1;
    public int endNo = 20;
    public int minposition = 1;
    public int maxposition = endNo;
    final int initialViewHeight = NotificationsFragment.lltopsection.getLayoutParams().height;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static boolean isEventUnack = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.events_frag, null);

        //initialize RecyclerView
        recyclerView = (RecyclerView) mView.findViewById(R.id.recycler_view);
        //setLayout Manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sections = new ArrayList<>();
        childList = new ArrayList<>();

        mSwipeRefreshLayout = (SwipeRefreshLayout)mView.findViewById(R.id.swipe_container) ;
        mSwipeRefreshLayout.setOnRefreshListener(this);

        adapterRecycler = new EventSectionAdapter(getActivity(), sections);
        recyclerView.setAdapter(adapterRecycler);

        AlaramsRequest alaramsRequest = new AlaramsRequest();
        alaramsRequest.setStartNo(startNo);
        alaramsRequest.setEndNo(endNo);
        if (Helper.hasNetworkConnection(getActivity())) {
            getEvents(getActivity(), alaramsRequest);
        } else {
            Toast.makeText(getActivity(), R.string.noconnection, Toast.LENGTH_LONG).show();

        }

        swipeController = new SwipeController(getActivity(), new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                // Toast.makeText(getActivity(),"Ack with notes clicked",Toast.LENGTH_LONG).show();

                EventsResponse mEventsResponse = childList.get(position - 1);
                if (mEventsResponse.getAcknowledgeNotes() == null) {
                    EventNotesDialog mDialog = new EventNotesDialog(getActivity(), mEventsResponse.getEventLogId(),mEventsResponse);
                    mDialog.show();
                } else {
                    Toast.makeText(getActivity(), "Already acknowledged", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLeftClicked(int position) {
                //Toast.makeText(getActivity(),"Ack  clicked",Toast.LENGTH_LONG).show();
                EventsResponse mEventsResponse = childList.get(position - 1);
                if (mEventsResponse.getAcknowledgeNotes() == null) {
                    AckInfo mAckInfo = new AckInfo();
                    mAckInfo.setID(String.valueOf(mEventsResponse.getEventLogId()));
                    mAckInfo.setQType("ackevents");
                    mAckInfo.setAckNotes("");
                    if (Helper.hasNetworkConnection(getActivity())) {
                        sendEventAck(getActivity(), mAckInfo);
                    } else {
                        Toast.makeText(getActivity(), R.string.noconnection, Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getActivity(), "Already acknowledged", Toast.LENGTH_LONG).show();
                }

            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                if (position == 0) {
                    return;
                } else {
                    int mypos = recyclerView.getChildViewHolder(view).getAdapterPosition();
                    Intent detailed = new Intent(getActivity(), EventDeatilsPage.class);
                    detailed.putExtra("eventInfo", childList.get(mypos - 1));
                    startActivity(detailed);
                    getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

               /* ValueAnimator animator = ValueAnimator.ofInt(0, 1);
                if (dy > 0 ) {
                   // HomePage.navigation.setVisibility(View.GONE);
                    //Getting actual yourViewToHide params
                    ViewGroup.LayoutParams params = NotificationsFragment.lltopsection.getLayoutParams();
                    if (!animator.isRunning()) {
                        //Setting animation from actual value to the target value (here 0, because we're hiding the view)
                        animator.setIntValues(params.height, 0);
                        //Animation duration
                        animator.setDuration(1);
                        //In this listener we update the view
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                ViewGroup.LayoutParams params = NotificationsFragment.lltopsection.getLayoutParams();
                                params.height = (int) animation.getAnimatedValue();
                                NotificationsFragment.lltopsection.setLayoutParams(params);
                            }
                        });
                        //Starting the animation
                        animator.start();

                    }

                } else if (dy < 0 ) {
                   // HomePage.navigation.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = NotificationsFragment.lltopsection.getLayoutParams();
                    if (!animator.isRunning()) {
                        //Setting animation from actual value to the initial yourViewToHide height)
                        animator.setIntValues(params.height, initialViewHeight);
                        //Animation duration
                        animator.setDuration(1);
                        //In this listener we update the view
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                ViewGroup.LayoutParams params = NotificationsFragment.lltopsection.getLayoutParams();
                                params.height = (int) animation.getAnimatedValue();
                                NotificationsFragment.lltopsection.setLayoutParams(params);
                            }
                        });
                        //Starting the animation
                        animator.start();

                    }
                }*/
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            startNo = endNo + 1;
                            endNo = endNo + 10;
                            maxposition = endNo;
                            AlaramsRequest alaramsRequest = new AlaramsRequest();
                            alaramsRequest.setStartNo(startNo);
                            alaramsRequest.setEndNo(endNo);
                            if (Helper.hasNetworkConnection(getActivity())) {
                                getEvents(getActivity(), alaramsRequest);
                            } else {
                                Toast.makeText(getActivity(), R.string.noconnection, Toast.LENGTH_LONG).show();

                            }
                        }
                    }
                }


            }
        });

        return mView;
    }

    @Override
    public void onResume() {


        if(isEventUnack){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
            isEventUnack = false;
        }
        super.onResume();

    }

    private void getEvents(final Context mContext, final AlaramsRequest alaramsRequest) {
        SpinnerManager.showSpinner(mContext, "Loading...");
        EventService.fetchEvents(alaramsRequest, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                sections.clear();
                mSwipeRefreshLayout.setRefreshing(false);
                Response<List<EventsResponse>> mRes = (Response<List<EventsResponse>>) response;
                List<EventsResponse> mData = mRes.body();
                if (isack) {
                    childList.clear();
                }
                isack = false;
                if (mData.size() > 2) {
                    childList.addAll(mData);
                    loading = true;
                } else {
                    loading = false;
                    Toast.makeText(mContext, "You have reached end of Event List", Toast.LENGTH_LONG).show();
                    recyclerView.getLayoutManager().scrollToPosition(0);
                }

                // NotificationsFragment.lbleventcount.setText(String.valueOf(childList.size()));
                sections.add(new EventSectionHeader(childList, "2019", 1));
                adapterRecycler.notifyDataChanged(sections);

                SpinnerManager.hideSpinner(mContext);
            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(mContext);
                mSwipeRefreshLayout.setRefreshing(false);

                error.printStackTrace();
            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(mContext);
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    private void sendEventAck(final Context context, final AckInfo ackInfo) {
        SpinnerManager.showSpinner(context, "Loading...");
        AlaramAcknowledgeService.ackAlaram(ackInfo, new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                SpinnerManager.hideSpinner(context);
                Toast.makeText(context, "Acknowledgement sent", Toast.LENGTH_LONG).show();
                AlaramsRequest alaramsRequest = new AlaramsRequest();
                alaramsRequest.setStartNo(minposition);
                alaramsRequest.setEndNo(maxposition);
                isack = true;
                getUnackEventCounts();
                getEvents(context, alaramsRequest);
            }

            @Override
            public void onFailure(Throwable error) {
                SpinnerManager.hideSpinner(context);
                error.printStackTrace();
            }

            @Override
            public void failureResponse(Object response) {
                SpinnerManager.hideSpinner(context);
                Toast.makeText(context, "Acknowledgement failed please try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onRefresh() {
        AlaramsRequest alaramsRequest = new AlaramsRequest();
        alaramsRequest.setStartNo(1);
        alaramsRequest.setEndNo(endNo);
        if (Helper.hasNetworkConnection(getActivity())) {
            getEvents(getActivity(), alaramsRequest);
        } else {
            Toast.makeText(getActivity(), R.string.noconnection, Toast.LENGTH_LONG).show();

        }
    }

    public class EventNotesDialog extends Dialog implements
            View.OnClickListener {


        public Activity c;
        public Dialog d;
        private int mId;
        private TextView mTitle, mDes;
        private EditText mNotes;
        private Button mCancel, mGo;
        private EventsResponse eventsResponse;

        public EventNotesDialog(Activity a, int id, EventsResponse eventsResponse) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.mId = id;
            this.eventsResponse = eventsResponse;
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.ack_notes_layout);
            initViews();
            applyFonts();
            mDes.setText(eventsResponse.getEventDecription());


        }

        private void initViews() {
            mTitle = (TextView) findViewById(R.id.promotitle);
            mDes = (TextView) findViewById(R.id.promodes);
            mNotes = (EditText) findViewById(R.id.apppromocode);
            mCancel = (Button) findViewById(R.id.buttoncancel);
            mGo = (Button) findViewById(R.id.buttongo);
            mCancel.setOnClickListener(this);
            mGo.setOnClickListener(this);


        }

        private void applyFonts() {
            mTitle.setTypeface(CustomFonts.getNexaBold(c));
            mDes.setTypeface(CustomFonts.getNexaRegular(c));
            mNotes.setTypeface(CustomFonts.getNexaRegular(c));
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
                    String notes = mNotes.getText().toString();
                    AckInfo mAckInfo = new AckInfo();
                    mAckInfo.setID(String.valueOf(mId));
                    mAckInfo.setQType("ackevents");
                    mAckInfo.setAckNotes(notes);
                    if (Helper.hasNetworkConnection(c)) {
                        sendEvenAck(c, mAckInfo);
                    } else {
                        Toast.makeText(getActivity(), R.string.noconnection, Toast.LENGTH_LONG).show();

                    }
                    break;


                default:
                    break;
            }
            //dismiss();
        }

        private void sendEvenAck(final Context context, final AckInfo ackInfo) {
            SpinnerManager.showSpinner(context, "Loading...");
            AlaramAcknowledgeService.ackAlaram(ackInfo, new ResponseListner() {
                @Override
                public void onSucess(Object response, int sttuscode) {
                    SpinnerManager.hideSpinner(context);
                    dismiss();
                    Toast.makeText(context, "Acknowledgement sent", Toast.LENGTH_LONG).show();
                    AlaramsRequest alaramsRequest = new AlaramsRequest();
                    alaramsRequest.setStartNo(minposition);
                    alaramsRequest.setEndNo(maxposition);
                    isack = true;
                    getUnackEventCounts();
                    getEvents(context, alaramsRequest);

                }

                @Override
                public void onFailure(Throwable error) {
                    SpinnerManager.hideSpinner(context);
                    dismiss();
                    error.printStackTrace();
                }

                @Override
                public void failureResponse(Object response) {
                    SpinnerManager.hideSpinner(context);
                    dismiss();
                    Toast.makeText(context, "Acknowledgement failed please try again", Toast.LENGTH_LONG).show();
                }
            });

        }

    }


    private void getUnackEventCounts() {
        CountService.fetchUnackEventCount(new ResponseListner() {
            @Override
            public void onSucess(Object response, int sttuscode) {
                Response<List<EventCountResponse>> mRes = (Response<List<EventCountResponse>>) response;
                List<EventCountResponse> mData = mRes.body();
                EventCountResponse evres = mData.get(0);

                if (evres.getEventCount() == 0) {
                    lbleventcount.setVisibility(View.GONE);
                } else {
                    lbleventcount.setText(String.valueOf(evres.getEventCount()));
                }


            }

            @Override
            public void onFailure(Throwable error) {
                lbleventcount.setVisibility(View.GONE);
            }

            @Override
            public void failureResponse(Object response) {
                lbleventcount.setVisibility(View.GONE);
            }
        });
    }
}
