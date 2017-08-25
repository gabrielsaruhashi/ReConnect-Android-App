package com.goprojectreconnect.projectreconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.Models.Chat;
import com.goprojectreconnect.projectreconnect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by gabrielsaruhashi on 8/25/17.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    ArrayList<Chat> chats;
    Context context;

    public ChatListAdapter(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get context and inflate view
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // create the view using the item_feed layout
        View chatListView = inflater.inflate(R.layout.item_chat, parent, false);

        // return a new holder instance
        ChatListAdapter.ViewHolder viewHolder = new ChatListAdapter.ViewHolder(chatListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // populate the views
        Chat chat = chats.get(position);

        Glide.with(context)
                .load(chat.getProfilePictureUrl())
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.ivProfilePicture);

        holder.tvName.setText(chat.getName());
        holder.tvLastMessage.setText(chat.getLastMessage());
        holder.tvLastMessageTime.setText(chat.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    // creates ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Automatically finds each field by the specified ID
        @BindView(R.id.ivProfilePicture)
        ImageView ivProfilePicture;
        @BindView(R.id.tvLastMessage)
        TextView tvLastMessage;
        @BindView(R.id.tvLastMessageTime)
        TextView tvLastMessageTime;
        @BindView(R.id.tvName)
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // gets item position
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                // get the event at the position
                Chat chat = chats.get(position);

                // do something

            }
        }
    }
}
