package com.goprojectreconnect.projectreconnect.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.Models.CustomUser;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class SignUpBasicInfoFragment extends Fragment {

    private ParseUser currentUser;
    private Unbinder unbinder;
    Context context;

    // UI References
    @BindView(R.id.ivProfilePicture)
    ImageView ivProfilePicture;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.etPhone)
    EditText etPhoneNumber;
    @BindView(R.id.etCity) EditText etCity;
    @BindView(R.id.etCountry) EditText etCountry;
    @BindView(R.id.btSave)
    Button btSave;

    // checkboxes
    @BindView(R.id.cbSports)
    CheckBox cbSports;
    @BindView(R.id.cbMusic) CheckBox cbMusic;
    @BindView(R.id.cbCityTransportation) CheckBox cbCityTransportation;
    @BindView(R.id.cbCulture) CheckBox cbCulture;
    @BindView(R.id.cbLanguage) CheckBox cbLanguage;
    @BindView(R.id.cbFood) CheckBox cbFood;

    // listener will the activity instance containing fragment
    private OnNextClickedListener listener;

    public SignUpBasicInfoFragment() {
        // Required empty public constructor
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
                    + " must implement SignUpBasicinfoFragment.OnNextClickedListener");
        }
    }

    // Now we can fire the event when the user selects something in the fragment
    public void onNextClick() {
        listener.updateViewpager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_su_basic_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        // instantiate context and current user
        currentUser = ReConnectApplication.getCurrentUser();
        context = getActivity();

        // populate views
        setupViews();

        return view;
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
                // get preferences
                ArrayList<String> interests = getSelectedInterests();

                // save info in the database
                CustomUser newCustomUser = new CustomUser(currentUser);
                newCustomUser.setSomeArrayList("interests", interests);
                newCustomUser.setSomeString("phone", etPhoneNumber.getText().toString());
                newCustomUser.setSomeString("city", etCity.getText().toString());

                // call listener that changes viewpager page
                onNextClick();
            }
        });
    }

    public ArrayList<String> getSelectedInterests() {
        ArrayList<String> interests = new ArrayList<>();
        if (cbCityTransportation.isChecked()) {
            interests.add("transportation");
        }
        if (cbCulture.isChecked()) {
            interests.add("culture");
        }
        if (cbFood.isChecked()) {
            interests.add("food");
        }
        if (cbLanguage.isChecked()) {
            interests.add("language");
        }
        if(cbSports.isChecked()) {
            interests.add("sports");
        }
        if (cbMusic.isChecked()) {
            interests.add("music");
        }

        return interests;
    }

    // unbind when fragment is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
