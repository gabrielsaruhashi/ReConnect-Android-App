package com.goprojectreconnect.projectreconnect.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.goprojectreconnect.projectreconnect.Activities.MainActivity;
import com.goprojectreconnect.projectreconnect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpVideo extends Fragment {
    Unbinder unbinder;
    Context context;
    @BindView(R.id.btComplete) Button btComplete;

    public SignUpVideo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment & bind butterknife views, and context
        View view = inflater.inflate(R.layout.fragment_su_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getActivity();


        // instantiate new youtubePlayerFragment
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        // initialize player fragment with API key
        youTubePlayerFragment.initialize(getResources().getString(R.string.google_api_key),
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean wasRestored) {
                        // play video
                        //TODO replace dummy youtubevideo
                        youTubePlayer.loadVideo("5xVh-7ywKpE");
                        youTubePlayer.play();
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        Log.e("SignUpVideo", "Youtube Failure");
                    }
                });

        // make fragment transaction to display youtube player
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        // setup listener for btComplete
        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch main activity
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });

        return view;
    }

    // unbind Butterknife view upon destruction
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
