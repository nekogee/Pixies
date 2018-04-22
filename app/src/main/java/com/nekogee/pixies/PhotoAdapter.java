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
    private Context mContext;

    private onRecyclerViewItemClick mOnRvItemClick;
   /* static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView photoImage;
        TextView photoName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            photoImage = view.findViewById(R.id.photo_image);
            photoName = view.findViewById(R.id.photo_name);
        }
    }*/

    public PhotoAdapter(List<Photo> PhotoList,onRecyclerViewItemClick onRvItemClick) {
        mPhotoList = PhotoList;
        this.mOnRvItemClick = onRvItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        if(mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_item,parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        Photo photo = mPhotoList.get(position);
        holder.photoName.setText(photo.getName());
        if(photo.getImagePath() == null) {
            Glide.with(mContext).load(photo.getImageId()).into(holder.photoImage);
           // holder.photoImage.setImageResource(photo.getImageId());
        } else  {
            Glide.with(mContext).load(photo.getImagePath()).into(holder.photoImage);
           // holder.photoImage.setImageBitmap(BitmapFactory.decodeFile(photo.getImagePath()));
        }
        //
       // holder.photoImage=photo.getImageId();
        //使用glide加载图片
        //holder.setData(position);
    }

    public interface onRecyclerViewItemClick {
        void onItemClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        ImageView photoImage;
        TextView photoName;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            photoImage = view.findViewById(R.id.photo_image);
            photoName = view.findViewById(R.id.photo_name);
            itemView.setOnClickListener(this);
        }

        /*public void setData(int position) {
            textView.setText("第" + position + "行");
        }*/

        @Override
        public void onClick(View view) {
            if (mOnRvItemClick != null)
                mOnRvItemClick.onItemClick(view, getAdapterPosition());
        }
    }
    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }
}
