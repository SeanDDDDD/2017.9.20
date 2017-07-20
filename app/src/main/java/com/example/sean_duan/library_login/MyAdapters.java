package com.example.sean_duan.library_login;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sean-duan on 2017/5/14.
 */

public class MyAdapters extends RecyclerView.Adapter<MyAdapters.ViewHolder> {
    private List<MyMenu> menuList;
    private Context context;
    private OkHttpClient mokhttpClient = new OkHttpClient();
    private String cookie ;
    private  String url = null;
    private  Intent intent;
    private Document document;
    static  class ViewHolder extends  RecyclerView.ViewHolder{
        View menuview ;
        ImageView menuImage;
        TextView menuName;
        public ViewHolder(View itemView) {
            super(itemView);
            menuview = itemView;
            menuImage = (ImageView) itemView.findViewById(R.id.image_one);
            menuName = (TextView) itemView.findViewById(R.id.textView_one);
        }
    }
    public MyAdapters(List<MyMenu> myMenuList,Context context,String cookie){
        this.context = context ;
        this.menuList = myMenuList;
        this.cookie = cookie ;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                MyMenu menu = menuList.get(position);
                Toast.makeText(context, "你点击的是"+position, Toast.LENGTH_SHORT).show();
                listener(position);
            }
        });
        holder.menuName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positions = holder.getAdapterPosition();
                listener(positions);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyMenu myMenu = menuList.get(position);
        holder.menuImage.setImageResource(myMenu.getImageId());
        holder.menuName.setText(myMenu.getName());
    }

    @Override
    public int getItemCount() {
        return menuList.size();

    }
    public void listener(int position){
        switch (position){
            case 0:
                url = "http://opac.lib.jhun.edu.cn:8080/reader/redr_info.php";
                Request request = new Request.Builder()
                        .addHeader("cookie",cookie)
                        .url(url).build();
                Call call = mokhttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("你点击失败","访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        MyWriter mywriter = new MyWriter(response.body().string(),context,"redr_info");

                    }
                });
                break;
            case 1:
                url = "http://opac.lib.jhun.edu.cn:8080/reader/book_lst.php";
                Request request1 = new Request.Builder()
                        .addHeader("cookie",cookie)
                        .url(url).build();
                Call call1 = mokhttpClient.newCall(request1);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("你点击失败","访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //MyWriter mywriter = new MyWriter(response.body().string(),context,"book_lst");
                        Jsoup_one jsoup_one = new Jsoup_one(response,context);
                        jsoup_one.gethistory();


//                        document = Jsoup.parse(response.body().string());
//                    Elements elements = document.select(".whitetext");
//                    String s = elements.get(1).text();
//                        Log.e("shushushushushus",s);
                    }
                });
                break;
            case 2:
                url = "http://opac.lib.jhun.edu.cn:8080/reader/book_hist.php";
                Request request2 = new Request.Builder()
                        .addHeader("cookie",cookie)
                        .url(url).build();
                Call call2 = mokhttpClient.newCall(request2);
                call2.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("你点击失败","访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        MyWriter mywriter = new MyWriter(response.body().string(),context,"book_hist");

                    }
                });
                break;
            case 10:
                url = "http://opac.lib.jhun.edu.cn:8080/reader/fine_pec.php";
                Request request10 = new Request.Builder()
                        .addHeader("cookie",cookie)
                        .url(url).build();
                Call call10 = mokhttpClient.newCall(request10);
                call10.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("你点击失败","访问失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        MyWriter mywriter = new MyWriter(response.body().string(),context,"fine_pec");

                    }
                });
                break;

        }
    }
}
