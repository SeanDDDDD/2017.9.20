package com.example.sean_duan.library_login;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by sean-duan on 2017/5/13.
 */

public class Myreader {
    private String usercode;
    private Context context;
    private String  line = null;
    private FileInputStream in = null;
    private BufferedReader reader = null;
    StringBuffer buffer =new StringBuffer();
    public Myreader(String usercode,Context context){
        this.usercode = usercode;
        this.context = context;
    }
    public String gethtml(){
        try {
            in = context.openFileInput(usercode);
            reader = new BufferedReader(new InputStreamReader(in));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            line = reader.readLine();

        while(line!=null){
                buffer.append(line);




        }

            reader.close();

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(buffer!=null){
            return buffer.toString();
        }
        else{
            Log.e("youyouyou","shibaibaishiabi");
            return null;
        }
    }
}
