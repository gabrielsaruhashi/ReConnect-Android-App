package com.goprojectreconnect.projectreconnect.Activities;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    Context context;
    ParseUser profileOwner;
    ParseUser currentUser;
    boolean isCurrentUser;

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
    LinearLayout llRequest;
    @BindView(R.id.tvAbout)
    TextView tvAbout;
    @BindView(R.id.tvHope)
    TextView tvHope;
    @BindView(R.id.tvIntegration)
    TextView tvIntegration;
    @BindView(R.id.tvRequestBanner)
    TextView tvRequestReconnectionBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        context = this;
        currentUser = ReConnectApplication.getCurrentUser();

        // unwrap intent, if any
        profileOwner = getIntent().getParcelableExtra("profile_owner");
        isCurrentUser = profileOwner.getObjectId().equals(currentUser.getObjectId());

        setupViews();

    }

    public void setupViews() {
        tvName.setText(profileOwner.getString("name"));
        tvLocation.setText(profileOwner.getString("city"));
        tvAbout.setText(profileOwner.getString("about_essay"));
        tvHope.setText(profileOwner.getString("hope_essay"));
        tvIntegration.setText(profileOwner.getString("integration_essay"));

        // if current user, change CTO to Edit Profile
        if (isCurrentUser) {
            llRequest.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            tvRequestReconnectionBanner.setText(getResources().getString(R.string.edit_profile_badge));
        }

        // resize image to fit width
        Point size = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;

        Glide.with(context)
                .load(profileOwner.getString("profile_image_url"))
                .centerCrop()
                .dontAnimate()
                .into(ivProfilePicture);

        llRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO invite ReConnection
            }
        });
    }
}
