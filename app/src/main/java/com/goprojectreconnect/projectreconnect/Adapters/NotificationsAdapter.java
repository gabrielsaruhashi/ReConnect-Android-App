package com.goprojectreconnect.projectreconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.Models.Notification;
import com.goprojectreconnect.projectreconnect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by gabrielsaruhashi on 8/25/17.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Notification> notifications;

    public NotificationsAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // create the view using the item_feed layout
        View notificationsView = inflater.inflate(R.layout.item_notification, parent, false);

        // return a new holder instance
        NotificationsAdapter.ViewHolder viewHolder = new NotificationsAdapter.ViewHolder(notificationsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationsAdapter.ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        // populate views
        Glide.with(context)
                .load(notification.getNotificationImageUrl())
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.ivProfilePicture);

        holder.tvName.setText(notification.getRecipientName());
        holder.tvMessage.setText(notification.getMessage());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    // creates ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Automatically finds each field by the specified ID
        @BindView(R.id.ivProfilePicture)
        ImageView ivProfilePicture;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.btAccept)
        ImageView btAccept;

        @BindView(R.id.tvMessage)
        TextView tvMessage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btAccept.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // gets item position
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                // get the event at the position
                Notification notification = notifications.get(position);

                //TODO click listener to profile of user that added
                // do something

            }
        }
    }
}
