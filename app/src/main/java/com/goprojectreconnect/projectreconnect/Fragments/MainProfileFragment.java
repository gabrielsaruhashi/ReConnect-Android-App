package com.goprojectreconnect.projectreconnect.Fragments;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gabrielsaruhashi on 8/25/17.
 */

public class MainProfileFragment extends Fragment {
    Context context;
    ParseUser currentUser;

    // UI Views
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.ivProfilePicture)
    ImageView ivProfilePicture;
    @BindView(R.id.tvReferenceCount)
    TextView tvReferenceCount;
    @BindView(R.id.llRequestReConnection)
    LinearLayout llRequestReConnection;
    @BindView(R.id.tvAbout)
    TextView tvAbout;
    @BindView(R.id.tvHope)
    TextView tvHope;
    @BindView(R.id.tvIntegration)
    TextView tvIntegration;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment & context
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);

        currentUser = ReConnectApplication.getCurrentUser();

        setupViews();

        return view;
    }

    public void setupViews() {
        tvName.setText(currentUser.getString("name"));
        tvLocation.setText(currentUser.getString("city"));
        tvAbout.setText(currentUser.getString("about_essay"));
        tvHope.setText(currentUser.getString("hope_essay"));
        tvIntegration.setText(currentUser.getString("integration_essay"));

        // resize image to fit width
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;

        Glide.with(context)
                .load(currentUser.getString("profile_image_url"))
                .centerCrop()
                .dontAnimate()
                .into(ivProfilePicture);

        llRequestReConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO allow users to edit profile
            }
        });
    }
}
