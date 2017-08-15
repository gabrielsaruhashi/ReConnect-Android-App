package com.goprojectreconnect.projectreconnect.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class SignUpActivity extends AppCompatActivity {

    ParseUser currentUser;
    Context context;

    // UI References
    @BindView(R.id.ivProfilePicture) ImageView ivProfilePicture;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.etPhone) EditText etPhoneNumber;
    @BindView(R.id.etCity) EditText etCity;
    @BindView(R.id.etCountry) EditText etCountry;
    @BindView(R.id.btSave) Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        // instantiate context and current user
        context = this;
        currentUser = ReConnectApplication.getCurrentUser();

        // populate views
        setupViews();
    }

    public void setupViews() {
        tvName.setText(currentUser.getString("name"));

        Glide.with(context)
                .load(currentUser.getString("profile_image_url"))
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .fitCenter()
                .bitmapTransform(new RoundedCornersTransformation(context, 30, 10))
                .into(ivProfilePicture);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save info in the database

                // start Home activity
            }
        });
    }
}
