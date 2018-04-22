package com.nekogee.pixies;

import java.net.URI;
import java.net.URL;

/**
 * Created by hui jie on 2018/4/22.
 */

public class PicBean {
    private int size;
    private String storeName;
    private String url;
    private String delete;
    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return size;
    }
    public void setStoreName(String storeName){
        this.storeName = storeName;
    }
    public String getStoreName(){
        return storeName;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
    public void setDelete(String delete){
        this.delete = delete;
    }
    public String getDelete(){
        return delete;
    }
}
