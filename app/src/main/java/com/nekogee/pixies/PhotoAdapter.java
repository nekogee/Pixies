package com.nekogee.pixies;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by hui jie on 2018/3/15.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {
    private List<Photo> mPhotoList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView photoImage;
        TextView photoName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            photoImage = view.findViewById(R.id.photo_image);
            photoName = view.findViewById(R.id.photo_name);
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
        holder.photoName.setText(photo.getName());
        if(photo.getImagePath() == null) {
            holder.photoImage.setImageResource(photo.getImageId());

        } else  {
            holder.photoImage.setImageBitmap(BitmapFactory.decodeFile(photo.getImagePath()));
        }
        //
       // holder.photoImage=photo.getImageId();
        //使用glide加载图片
       // Glide.with(context).load(list.get(position).getPic()).into(iViewHolder.adapterImageview);

    }
    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }
}
