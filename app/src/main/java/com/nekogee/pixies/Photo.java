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
    private String url = null;
    public Photo(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }
    public Photo(String name,String imagePath,String url){
        this.name = name;
        this.imagePath = imagePath;
        this.url = url;
    }

    public Photo(String name,int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public Photo(String name,int imageId,String url){
        this.name = name;
        this.imageId = imageId;
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    public String getUrl() {return url;}
}
