package com.goprojectreconnect.projectreconnect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.Activities.AddReconnectionActivity;
import com.goprojectreconnect.projectreconnect.Activities.ProfileActivity;
import com.goprojectreconnect.projectreconnect.R;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class AddReConnectionAdapter extends RecyclerView.Adapter<AddReConnectionAdapter.ViewHolder> {
    ArrayList<ParseUser> suggestions;
    Context context;

    public AddReConnectionAdapter(Context context, ArrayList<ParseUser> suggestions) {
        this.context = context;
        this.suggestions = suggestions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //  inflate view
        LayoutInflater inflater = LayoutInflater.from(context);

        // create the view using the item_feed layout
        View view = inflater.inflate(R.layout.item_reconnection_recommendation, parent, false);

        // return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ParseUser user = suggestions.get(position);
        //TODO finish populating views
        holder.tvName.setText(user.getString("name"));
        holder.tvAbout.setText(user.getString("about_essay"));

        Glide.with(context)
                .load(user.getString("profile_image_url"))
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.ivProfilePicture);

    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.ivProfilePicture)
        ImageView ivProfilePicture;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvAbout)
        TextView tvAbout;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.btReConnect)
        Button btReConnect;
        @BindView(R.id.btSeeProfile)
        Button btSeeProfile;
        @BindView(R.id.tvLocation)
        TextView tvLocation;
        @BindView(R.id.tvSince)
        TextView tvSince;
        @BindView(R.id.tvMutual)
        TextView tvMutual;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            btReConnect.setOnClickListener(this);
            btSeeProfile.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // get adapter position
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                ParseUser suggestion = suggestions.get(position);

                switch (v.getId()) {
                    case R.id.btReConnect:
                        if(context instanceof AddReconnectionActivity){
                            ((AddReconnectionActivity) context).showInvitationDialog(suggestion);
                        }
                        break;
                    case R.id.btSeeProfile:
                        Intent i = new Intent(context, ProfileActivity.class);
                        i.putExtra("profile_owner", suggestion);
                        context.startActivity(i);

                        break;
                }

            }

        }
    }

}
