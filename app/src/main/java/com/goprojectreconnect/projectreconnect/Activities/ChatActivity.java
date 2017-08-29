package com.goprojectreconnect.projectreconnect.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.goprojectreconnect.projectreconnect.Adapters.ChatAdapter;
import com.goprojectreconnect.projectreconnect.Models.Message;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    static final String TAG = ChatActivity.class.getSimpleName();
    EditText etMessage;
    ImageButton btSend;
    private ParseUser currentUser;
    private String reconnectionId;

    // recyclerview variables
    RecyclerView rvChat;
    ArrayList<Message> mMessages;
    ChatAdapter mAdapter;
    boolean mFirstLoad;
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // get reconnection id from intent
        reconnectionId = getIntent().getStringExtra("id");

        // instantiate user and message posting
        currentUser = ReConnectApplication.getCurrentUser();
        setupMessagePosting();
    }

    public void setupMessagePosting() {
        // Find the text field and button
        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (ImageButton) findViewById(R.id.btSend);
        rvChat = (RecyclerView) findViewById(R.id.rvChat);
        mMessages = new ArrayList<>();
        mFirstLoad = true;

        mAdapter = new ChatAdapter(this, mMessages);
        rvChat.setAdapter(mAdapter);

        // associate the LayoutManager with the RecylcerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
        linearLayoutManager.setReverseLayout(true);
        rvChat.setLayoutManager(linearLayoutManager);


        // When send button is clicked, create message object on Parse
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etMessage.getText().toString();
                Message message = new Message();
                message.setReconnectionId(reconnectionId);
                message.setSender(currentUser);
                message.setBody(data);
                message.saveInBackground();

                // update UI
                mMessages.add(0, message);
                mAdapter.notifyItemInserted(0);
                rvChat.smoothScrollToPosition(0);

                // clear editText
                etMessage.setText(null);
            }
        });

        // populate messages
        refreshMessages();
    }

    public void refreshMessages() {

        // Construct query to execute. Only get messages from this reconnection
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.whereEqualTo("reconnection_id", reconnectionId);
        // include sender pointer
        query.include("sender");

        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");

        // Execute query to fetch all messages from Parse asynchronously
        query.findInBackground(new FindCallback<Message>() {
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
    }


}
