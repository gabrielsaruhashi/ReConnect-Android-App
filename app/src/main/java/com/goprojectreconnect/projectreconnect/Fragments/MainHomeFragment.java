package com.goprojectreconnect.projectreconnect.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goprojectreconnect.projectreconnect.Activities.AddReconnectionActivity;
import com.goprojectreconnect.projectreconnect.Adapters.AddReConnectionAdapter;
import com.goprojectreconnect.projectreconnect.Adapters.NotificationsAdapter;
import com.goprojectreconnect.projectreconnect.Helpers.DividerItemDecorator;
import com.goprojectreconnect.projectreconnect.Models.FacebookFriend;
import com.goprojectreconnect.projectreconnect.Models.Notification;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static com.goprojectreconnect.projectreconnect.ReConnectApplication.getFacebookFriends;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {
    Context context;
    ParseUser currentUser;

    /* Invitations variables */
    NotificationsAdapter invitationsAdapter;
    ArrayList<Notification> invitations;
    RecyclerView rvInvitations;

    /* Suggestions adapter */
    ArrayList<ParseUser> suggestions;
    AddReConnectionAdapter suggestionsAdapter;
    RecyclerView rvSuggestions;
    LinearLayout llSuggestionsCTA;

    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_home, container, false);
        context = getActivity();

       /* Instantiate Invitations' recycler view, invitationsAdapter and arraylist */
        invitations = new ArrayList<>();
        invitationsAdapter = new NotificationsAdapter(context, invitations);
        rvInvitations = (RecyclerView) v.findViewById(R.id.rvNotifications);

        // attach the invitationsAdapter to the RecyclerView
        rvInvitations.setAdapter(invitationsAdapter);
        // Set layout manager to position the items
        rvInvitations.setLayoutManager(new LinearLayoutManager(getContext()));

        // add line divider decorator
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecorator(rvInvitations.getContext(), DividerItemDecorator.VERTICAL_LIST);
        rvInvitations.addItemDecoration(itemDecoration);

        /* Instantiate Suggestions' recycler view, invitationsAdapter and arraylist */
        suggestions = new ArrayList<>();
        suggestionsAdapter = new AddReConnectionAdapter(context, suggestions);
        rvSuggestions = (RecyclerView) v.findViewById(R.id.rvSuggestions);
        llSuggestionsCTA = (LinearLayout) v.findViewById(R.id.llSuggestionsCTA);

        // attach the invitationsAdapter and layout manager
        rvSuggestions.setAdapter(suggestionsAdapter);
        rvSuggestions.setLayoutManager(new LinearLayoutManager(getContext()));

        // instantiate current user
        currentUser = ReConnectApplication.getCurrentUser();

        // setup fragment's click listeners
        setupClickListeners();

        // populate lists
        populateSuggestions();
        populateNotificationList();

        return v;
    }

    public void setupClickListeners() {
        llSuggestionsCTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddReconnectionActivity.class);
                context.startActivity(i);

            }
        });
    }

    public void populateNotificationList() {
        //TODO implement this

        ParseQuery<Notification> query = ParseQuery.getQuery("Notification");

        query.findInBackground(new FindCallback<Notification>() {
            @Override
            public void done(List<Notification> currentNotifications, ParseException e) {
                if (e == null) {
                    invitations.addAll(currentNotifications);
                    invitationsAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void populateSuggestions() {
        //TODO implement actual populatelist method that only
        ArrayList<FacebookFriend> friends = getFacebookFriends();
        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();

        // get arraylist with facebook friends ids
        ArrayList<Long> facebookFriendsIds = getFacebookFriendsIds();


        // iterate through

        userQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null) {
                    suggestions.addAll(users);
                    suggestionsAdapter.notifyDataSetChanged();
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
