package com.example.sean_duan.library_login;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by sean-duan on 2017/5/7.
 */

public class Jsoup_one {
    private  Response response;
    String html;
    private Context context;
    private   Document document;
    Map<String,String> message =null;
    private  ResponseBody responseBody ;
    public Jsoup_one(String html){
        this.html = html ;
    }
    public Jsoup_one(Response response, Context context){
        this.responseBody = responseBody;
        this.context = context;
        this.response =response;
    }
    public  void gethistory()  {
        try {
            document = Jsoup.parse(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fiaql","qingqiushibai");
        }
        Elements elements = document.select(".whitetext");
        String s = elements.get(1).text();
        Log.e("shushushushushus",s);
    }
    public void getMessage(){
        document = Jsoup.parse(html);
        Elements elements = document.select("[align=center]");//获得带align属性且值为center的元素集合
        Elements elements_tr = elements.select("tr");
        Element element_one = elements_tr.get(1);
        Element element_two = elements_tr.get(2);
        Element element_zaocao = element_one.select("td").get(1);
        Element element_duanlian = element_two.select("td").get(1);
        Log.i("align=====",element_duanlian.text().toString().trim());
        Log.i("align=====",element_zaocao.text().toString().trim());
//        Log.i("align=====",element_one.toString().trim());
//        Log.i("align=====",element_two.toString().trim());
//        Log.i("align=====",elements_tr.toString().trim());
//        Log.i("align=====",elements.toString().trim());

    }
    public Map<String,String> getzaowan(){
        document = Jsoup.parse(html);
        Elements elements = document.select("[align=center]");//获得带align属性且值为center的元素集合
        Elements elements_tr = elements.select("tr");
        Element element_one = elements_tr.get(1);
        Element element_two = elements_tr.get(2);
        Element element_zaocao = element_one.select("td").get(1);
        Element element_duanlian = element_two.select("td").get(1);
        message.put("zao",element_zaocao.text());
        message.put("wan",element_duanlian.text());
        return message ;
    }
}
