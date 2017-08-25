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
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
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

        // attach the adapter to the RecyclerView
        rvChats.setAdapter(adapter);
        // Set layout manager to position the items
        rvChats.setLayoutManager(new LinearLayoutManager(getContext()));

        // add line divider decorator
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecorator(rvChats.getContext(), DividerItemDecorator.VERTICAL_LIST);
        rvChats.addItemDecoration(itemDecoration);

        return v;
    }

    public void populateChatList() {
        //TODO implement method
    }

}
