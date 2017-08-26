package com.goprojectreconnect.projectreconnect.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goprojectreconnect.projectreconnect.Adapters.NotificationsAdapter;
import com.goprojectreconnect.projectreconnect.Helpers.DividerItemDecorator;
import com.goprojectreconnect.projectreconnect.Models.Notification;
import com.goprojectreconnect.projectreconnect.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainHomeFragment extends Fragment {
    NotificationsAdapter adapter;
    ArrayList<Notification> notifications;
    RecyclerView rvNotifications;
    Context context;

    public MainHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_home, container, false);
        context = getActivity();

        // initialize list, adapter and recyclerview
        notifications = new ArrayList<>();
        adapter = new NotificationsAdapter(context, notifications);
        rvNotifications = (RecyclerView) v.findViewById(R.id.rvNotifications);

        // attach the adapter to the RecyclerView
        rvNotifications.setAdapter(adapter);
        // Set layout manager to position the items
        rvNotifications.setLayoutManager(new LinearLayoutManager(getContext()));

        // add line divider decorator
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecorator(rvNotifications.getContext(), DividerItemDecorator.VERTICAL_LIST);
        rvNotifications.addItemDecoration(itemDecoration);

        populateNotificationList();

        return v;
    }

    public void populateNotificationList() {
        //TODO implement this

        ParseQuery<Notification> query = ParseQuery.getQuery("Notification");

        query.findInBackground(new FindCallback<Notification>() {
            @Override
            public void done(List<Notification> currentNotifications, ParseException e) {
                if (e == null) {
                    notifications.addAll(currentNotifications);
                    adapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }




}
