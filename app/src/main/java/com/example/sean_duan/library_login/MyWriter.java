package com.example.sean_duan.library_login;

import android.content.Context;
import android.provider.OpenableColumns;

import java.io.*;

/**
 * Created by sean-duan on 2017/5/13.
 */

public class MyWriter {

    private FileOutputStream out1 = null;
    private BufferedWriter writer = null;
    public MyWriter(String s, Context context,String username){

        try {
            out1 = context.openFileOutput(username,Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out1));
            try {
                writer.write(s);
                writer.close();
                out1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }




}
