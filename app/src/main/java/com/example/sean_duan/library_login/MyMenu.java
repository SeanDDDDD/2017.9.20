package com.example.sean_duan.library_login;

/**
 * Created by sean-duan on 2017/5/14.
 */

public class MyMenu {
    private String name ;
    private int imageId;
    public MyMenu(String name,int imageId){
        this.name = name ;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }

}
