package com.nekogee.pixies;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.net.URI;

/**
 * Created by hui jie on 2018/3/15.
 */

public class Photo {
    private String name;
    private String imagePath = null;
    private int imageId = 0;
    public Photo(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }
    public Photo(String name,int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public String getName() {
        return name;
    }
    public String getImagePath() {
        return imagePath;
    }
    public int getImageId() {
        return imageId;
    }
}
