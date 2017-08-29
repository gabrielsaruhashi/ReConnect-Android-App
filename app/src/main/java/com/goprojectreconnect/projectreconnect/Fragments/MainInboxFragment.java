package com.goprojectreconnect.projectreconnect.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goprojectreconnect.projectreconnect.Adapters.ChatListAdapter;
import com.goprojectreconnect.projectreconnect.Helpers.DividerItemDecorator;
import com.goprojectreconnect.projectreconnect.Models.Chat;
import com.goprojectreconnect.projectreconnect.Models.Reconnection;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainInboxFragment extends Fragment {
    ParseUser currentUser;
    Context context;
    ArrayList<Chat> chats;
    ChatListAdapter adapter;
    RecyclerView rvChats;

    public MainInboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        currentUser = ReConnectApplication.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main_inbox, container, false);
        // initialize the list of chats
        chats = new ArrayList<>();
        // construct the adater from the data source
        adapter = new ChatListAdapter(chats);
        // initialize recycler view
        rvChats = (RecyclerView) v.findViewById(R.id.rvChats);

        // attach the invitationsAdapter to the RecyclerView
        rvChats.setAdapter(adapter);
        // Set layout manager to position the items
        rvChats.setLayoutManager(new LinearLayoutManager(getContext()));

        // add line divider decorator
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecorator(rvChats.getContext(), DividerItemDecorator.VERTICAL_LIST);
        rvChats.addItemDecoration(itemDecoration);

        populateChatList();

        return v;
    }

    public void populateChatList() {

        // each reconnection item is a chat
        ParseQuery<Reconnection> query = ParseQuery.getQuery("Reconnection");
        //TODO different queries for different types of users
        query.whereEqualTo("refugee", currentUser);
        query.include("host");
        query.include("refugee");
        query.findInBackground(new FindCallback<Reconnection>() {
            @Override
            public void done(List<Reconnection> reconnections, ParseException e) {
                if (e == null) {
                    for (Reconnection reconnection : reconnections) {
                        Chat chat = Chat.fromReconnectionObject(reconnection);
                        chats.add(chat);
                    }
                    adapter.notifyDataSetChanged();

                } else {
                    e.printStackTrace();
                }
            }
        });

        adapter.notifyDataSetChanged();
    }

}
