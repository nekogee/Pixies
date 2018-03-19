package com.nekogee.pixies;

/**
 * Created by hui jie on 2018/3/15.
 */

public class Photo {
    private String name;
    private int imageId;
    public Photo(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }
    public String getName() {
        return name;
    }
    public int getImageId() {
        return imageId;
    }
}
