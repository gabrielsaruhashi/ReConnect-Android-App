package com.goprojectreconnect.projectreconnect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.goprojectreconnect.projectreconnect.R;
import com.parse.ParseFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by GabrielSaruhashi on 18/08/17.
 */

public class MemoryMapAdapter extends RecyclerView.Adapter<MemoryMapAdapter.ViewHolder> {

    ArrayList<ParseFile> pictures;
    ArrayList<String> uploaderPictures;
    Context context;

    public MemoryMapAdapter(ArrayList<ParseFile> pictures, ArrayList<String> uploaderPictures) {
        this.pictures = pictures;
        this.uploaderPictures = uploaderPictures;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get context and inflate view
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // create the view using the item_feed layout
        View view = inflater.inflate(R.layout.item_map_photo, parent, false);

        // return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParseFile picture = pictures.get(position);

        // populate views
        Glide.with(context)
                .load(picture.getUrl())
                .fitCenter()
                .into(holder.ivPicture);

        Glide.with(context)
                .load(uploaderPictures.get(position))
                .bitmapTransform(new CropCircleTransformation(context))
                .into(holder.ivUploaderPicture);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivPicture) ImageView ivPicture;
        @BindView(R.id.ivUploaderPicture) ImageView ivUploaderPicture;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
