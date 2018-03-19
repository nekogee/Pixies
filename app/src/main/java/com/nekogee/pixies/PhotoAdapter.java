package com.nekogee.pixies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hui jie on 2018/3/15.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> mPhotoList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImage;
        TextView photoName;

        public ViewHolder(View view) {
            super(view);
            photoImage = (ImageView) view.findViewById(R.id.photo_image);
            photoName = (TextView) view.findViewById(R.id.photo_name);
        }
    }

    public PhotoAdapter(List<Photo> PhotoList) {
        mPhotoList = PhotoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Photo photo = mPhotoList.get(position);
        holder.photoImage.setImageResource(photo.getImageId());
        holder.photoName.setText(photo.getName());
    }
    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }
}
