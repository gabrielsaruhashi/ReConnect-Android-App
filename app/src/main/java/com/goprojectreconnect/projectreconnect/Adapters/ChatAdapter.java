package com.goprojectreconnect.projectreconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.Models.Message;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseUser;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by gabrielsaruhashi on 8/29/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<Message> mMessages;
    private Context mContext;
    private ParseUser currentUser;

    public ChatAdapter(Context context, List<Message> messages) {
        mMessages = messages;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_message, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);

        currentUser = ReConnectApplication.getCurrentUser();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = mMessages.get(position);
        final boolean isMe = message.getSender() != null &&
                message.getSender().getObjectId().equals(currentUser.getObjectId());

        if (isMe) {
            // make other body and image invisible, set my text to visible
            holder.otherBody.setVisibility(View.GONE);
            holder.imageOther.setVisibility(View.GONE);
            holder.myBody.setVisibility(View.VISIBLE);
            holder.myBody.setText(message.getBody());
        } else {
            holder.otherBody.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.VISIBLE);
            holder.myBody.setVisibility(View.GONE);
            holder.otherBody.setText(message.getBody());

        }
        Glide.with(mContext)
                .load(message.getSender().getString("profile_image_url"))
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(holder.imageOther);
    }


    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageOther;
        TextView myBody;
        TextView otherBody;

        public ViewHolder(View itemView) {
            super(itemView);
            imageOther = (ImageView)itemView.findViewById(R.id.ivProfileOther);
            myBody = (TextView)itemView.findViewById(R.id.tvMyBody);
            otherBody = (TextView)itemView.findViewById(R.id.tvOtherBody);
        }
    }
}