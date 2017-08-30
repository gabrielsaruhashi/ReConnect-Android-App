package com.goprojectreconnect.projectreconnect.Fragments;


import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.goprojectreconnect.projectreconnect.Adapters.PlaceAutoCompleteAdapter;
import com.goprojectreconnect.projectreconnect.Models.CustomUser;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;

public class SignUpBasicInfoFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;
    private ParseUser currentUser;
    private Unbinder unbinder;
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));
    Context context;
    private PlaceAutoCompleteAdapter mAdapter;


    // UI References
    @BindView(R.id.ivProfilePicture)
    ImageView ivProfilePicture;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.etPhone)
    EditText etPhoneNumber;
    @BindView(R.id.etCity) AutoCompleteTextView etCity;
    @BindView(R.id.etCountry) AutoCompleteTextView etCountry;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        // Construct a GoogleApiClient for the {@link Places#GEO_DATA_API} using AutoManage
        // functionality, which automatically sets up the API client to handle Activity lifecycle
        // events. If your activity does not extend FragmentActivity, make sure to call connect()
        // and disconnect() explicitly.
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                //.enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate the layout
        View view = inflater.inflate(R.layout.fragment_su_basic_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        // instantiate context and current user
        currentUser = ReConnectApplication.getCurrentUser();

        // populate views
        setupViews();

        return view;
    }

    public void setupViews() {
        tvName.setText(currentUser.getString("name"));

        // // register a listener that receives callbacks when a suggestion has been selected
        etCity.setOnItemClickListener(mAutocompleteClickListener);
        etCountry.setOnItemClickListener(mAutocompleteClickListener);

        // Set up the invitationsAdapter that will retrieve suggestions from the Places Geo Data API that cover
        // the entire world.
        mAdapter = new PlaceAutoCompleteAdapter(context, mGoogleApiClient, BOUNDS_GREATER_SYDNEY,
                null);
        etCity.setAdapter(mAdapter);

        Glide.with(context)
                .load(currentUser.getString("profile_image_url"))
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(context))
                .into(ivProfilePicture);

        checkDatabaseForFields();

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
                newCustomUser.setSomeString("country", etCountry.getText().toString());

                // call listener that changes viewpager page
                onNextClick();
            }
        });
    }

    public void checkDatabaseForFields() {
        if (currentUser.getString("phone") != null) {
            etPhoneNumber.setText(currentUser.getString("phone"));
        }

        if (currentUser.getString("city") != null) {
            etCity.setText(currentUser.getString("city"));
        }

        if (currentUser.getString("country") != null) {
            etPhoneNumber.setText(currentUser.getString("country"));
        }


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

    /**
     * Listener that handles selections from suggestions from the AutoCompleteTextView that
     * displays Place suggestions.
     * Gets the place id of the selected item and issues a request to the Places Geo Data API
     * to retrieve more details about the place.
     *
     * @see com.google.android.gms.location.places.GeoDataApi#getPlaceById(com.google.android.gms.common.api.GoogleApiClient,
     * String...)
     */
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The invitationsAdapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data API to retrieve a Place object with additional
             details about the place.
              */
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

            Toast.makeText(getApplicationContext(), "Clicked: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            etCity.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));

            Log.i(TAG, "Place details received: " + place.getName());

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));

    }

    /**
     * Called when the Activity could not connect to Google Play services and the auto manager
     * could resolve the error automatically.
     * In this case the API is not available and notify the user.
     *
     * @param connectionResult can be inspected to determine the cause of the failure
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(context,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }

    // unbind and disconnect client when fragment is destroyed
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
        unbinder.unbind();
    }
}
