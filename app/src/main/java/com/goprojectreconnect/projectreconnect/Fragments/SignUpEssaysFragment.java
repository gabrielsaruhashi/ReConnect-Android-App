package com.goprojectreconnect.projectreconnect.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.goprojectreconnect.projectreconnect.Models.CustomUser;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpEssaysFragment extends Fragment {

    private Unbinder unbinder;
    private ParseUser currentUser;
    private OnNextClickedListener listener;
    @BindView(R.id.etAboutMe) EditText etAboutMe;
    @BindView(R.id.etHope) EditText etHope;
    @BindView(R.id.etIntegration) EditText etIntegration;
    @BindView(R.id.btNext) Button btNext;

    public SignUpEssaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_su_essays, container, false);
        unbinder = ButterKnife.bind(this, view);
        currentUser = ReConnectApplication.getCurrentUser();
        // click listener for next
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save info in the database
                CustomUser newCustomUser = new CustomUser(currentUser);
                newCustomUser.setSomeString("about_essay", etAboutMe.getText().toString());
                newCustomUser.setSomeString("hope_essay", etHope.getText().toString());
                newCustomUser.setSomeString("integration_essay", etIntegration.getText().toString());

                // call listener that changes viewpager page
                onNextClick();
            }
        });

        return view;
    }


    // Define the events that the fragment will use to communicate
    public interface OnNextClickedListener {
        // This can be any number of events to be sent to the activity
        public void updateViewpager();
    }

    // Store the listener (activity) that will have events fired once the fragment is attached
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNextClickedListener) {
            listener = (OnNextClickedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement SignUpEssaysFragment.OnNextClickedListener");
        }
    }

    // Now we can fire the event when the user selects something in the fragment
    public void onNextClick() {
        listener.updateViewpager();;
    }


}
