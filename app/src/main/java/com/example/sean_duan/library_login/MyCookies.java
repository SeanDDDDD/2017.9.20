package com.example.sean_duan.library_login;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

/**
 * Created by sean-duan on 2017/5/14.
 */

public class MyCookies {
    private Headers headers;
    private String cookie = null;
    private List<String> cookies = new ArrayList<>();
    private String oldCookie= null;
    public MyCookies(Headers headers,String cookie){
        this.headers = headers;
        this.oldCookie = cookie;
    }
    public String getcookie(){
        cookies = headers.values("Set-Cookie");
        if(cookies.size()!=0){
            String session = cookies.get(0);
            cookie= session.substring(0,session.indexOf(";"));
            return cookie;
        }
        else
            return oldCookie;
    }
}
