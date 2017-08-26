package com.goprojectreconnect.projectreconnect.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goprojectreconnect.projectreconnect.Adapters.AddReConnectionAdapter;
import com.goprojectreconnect.projectreconnect.Models.FacebookFriend;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.goprojectreconnect.projectreconnect.ReConnectApplication.getFacebookFriends;


public class MainAddReConnectionFragment extends Fragment {

    Context context;
    ArrayList<ParseUser> suggestions;
    AddReConnectionAdapter mAdapter;
    RecyclerView rvSuggestions;
    ParseUser currentUser;

    public MainAddReConnectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_add_reconnection, container, false);
        context = getActivity();

        // instantiate recycler view, adapter and arraylist
        suggestions = new ArrayList<>();
        mAdapter = new AddReConnectionAdapter(context, suggestions);
        rvSuggestions = (RecyclerView) view.findViewById(R.id.rvRecommendations);

        // attach the adapter and layout manager
        rvSuggestions.setAdapter(mAdapter);
        rvSuggestions.setLayoutManager(new LinearLayoutManager(getContext()));

        // instantiate current user
        currentUser = ReConnectApplication.getCurrentUser();
        populateList();

        return view;
    }

    public void populateList() {
        //TODO implement actual populatelist method that only
        ArrayList<FacebookFriend> friends = ReConnectApplication.getFacebookFriends();
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();

        // get arraylist with facebook friends ids
        ArrayList<Long> facebookFriendsIds = getFacebookFriendsIds();


        // iterate through

        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    suggestions.addAll(users);
                    mAdapter.notifyDataSetChanged();
                    rvSuggestions.smoothScrollToPosition(0);

                } else {
                    e.printStackTrace();
                }
            }
        });

    }

    // get facebook friends ids
    public ArrayList<Long> getFacebookFriendsIds() {
        ArrayList<FacebookFriend> facebookFriends = getFacebookFriends();
        ArrayList<Long> facebookFriendsIds = new ArrayList<>();

        // iterate through array to get facebook friends
        for (FacebookFriend facebookFriend : facebookFriends) {
            facebookFriendsIds.add(facebookFriend.getFbUserID());
        }

        return facebookFriendsIds;
    }

}
