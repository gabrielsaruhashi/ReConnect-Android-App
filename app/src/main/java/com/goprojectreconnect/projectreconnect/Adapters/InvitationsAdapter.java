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
import com.goprojectreconnect.projectreconnect.Models.Reconnection;
import com.goprojectreconnect.projectreconnect.R;
import com.goprojectreconnect.projectreconnect.ReConnectApplication;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by gabrielsaruhashi on 8/25/17.
 */

public class InvitationsAdapter extends RecyclerView.Adapter<InvitationsAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Notification> notifications;
    private ParseUser currentUser;

    public InvitationsAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public InvitationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // create the view using the item_feed layout
        View notificationsView = inflater.inflate(R.layout.item_invitation, parent, false);

        // return a new holder instance
        InvitationsAdapter.ViewHolder viewHolder = new InvitationsAdapter.ViewHolder(notificationsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InvitationsAdapter.ViewHolder holder, int position) {
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
        @BindView(R.id.btCancel)
        ImageView btCancel;

        @BindView(R.id.tvMessage)
        TextView tvMessage;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btAccept.setOnClickListener(this);
            btCancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // gets item position
            final int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                // get the event at the position & get current user
                final Notification notification = notifications.get(position);
                currentUser = ReConnectApplication.getCurrentUser();

                switch (v.getId()) {
                    case R.id.btAccept:
                        //TODO define logic to see if currentuser is refugee or hostfriend
                        // create new ReConnection object
                        Reconnection newReconnection = new Reconnection(currentUser, notification.getRecipient());
                        newReconnection.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    // delete request from database and remove from arraylist
                                    notification.deleteInBackground();
                                    notifications.remove(notification);
                                    notifyItemRemoved(position);
                                }
                            }
                        });
                    case R.id.btCancel:
                        // delete request from database and remove from arraylist
                        notification.deleteInBackground();
                        notifications.remove(notification);
                        notifyItemRemoved(position);
                        break;
                }

                //TODO click listener to profile of user that added
                // do something

            }
        }
    }
}
