package com.example.sean_duan.library_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final  int SUCCESS = 1;
    private static final  int FAIL = 2;
    private static final  int SUCCESS_two = 3 ;
    private Button login;
    private EditText username;
    private EditText usecode;
    private EditText numbercode;
    private ImageButton imageView;
    private String cookie;
    private OkHttpClient mokhttpclient;
    private TextView zaoduanlian;
    private String line=null;
    private Set<String> set = new HashSet<>();
    private TextView wanduanklian;
    private SharedPreferences.Editor editor = null;
//    List<String> cookies=null;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1 :
                    byte[] pictures = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(pictures,0,pictures.length);
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2 :
                    Toast.makeText(MainActivity.this,"网络故障",Toast.LENGTH_SHORT);
                    break;
//                case 3:
//                    String  m = msg.obj.toString().trim();
//                    zaoduanlian.setText(m);
//                    break;
//                case 4:
//                    String n = msg.obj.toString().trim();
//                    wanduanklian.setText(n);
//                    break;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpicture();//获得图形验证码并且获得cookie
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                login();//登录账号
              //  login1();
            }
        });
    }



    public void init(){
        login = (Button) findViewById(R.id.button_login);
        imageView = (ImageButton) findViewById(R.id.imageButton);
        username = (EditText) findViewById(R.id.editText_username);
        usecode = (EditText) findViewById(R.id.editText_usercode);
        numbercode = (EditText) findViewById(R.id.editText_random);
        editor = getSharedPreferences("newusers",MODE_PRIVATE).edit();
        mokhttpclient =  new OkHttpClient.Builder().cookieJar(new CookieJar() {
            private  final HashMap<HttpUrl,List<Cookie>> cookieStore =
                    new HashMap<>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url,cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
//                return cookies;
                return cookies != null? cookies:new ArrayList<Cookie>();
            }
        }).build();
    }
      public void getpicture(){
        String url = "http://opac.lib.jhun.edu.cn:8080/reader/captcha.php";

        //创建一个请求
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call  = mokhttpclient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("aaaaaaaaaaaaaaaaaaaaaa","我失败啦啦啦啦啦");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    byte[] byte_image = response.body().bytes();
                    //异步改变UI 改变验证码图片
                    Message message = handler.obtainMessage();
                    message.obj = byte_image;
                    message.what = SUCCESS;
                    Log.i("info_handler","haandler");
                    handler.sendMessage(message);
                    //获得session

                    Headers headers = response.headers();
                    MyCookies myCookies = new MyCookies(headers,cookie);
                    cookie = myCookies.getcookie();
//                    Log.d("info_headers","header"+headers);
//                    List<String> cookies = headers.values("Set-Cookie");
//                    String session = cookies.get(0);
//                    Log.d("infor_cookiesw","onResponse-size"+cookies);
//
//                    cookie= session.substring(0,session.indexOf(";"));
                    Log.i("info_s","session is  :"+cookie);


                }

            }
        });

    }
    public void login1(){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("xuehao",username.getText().toString().trim()).build();
        Request request = new Request.Builder()
                .url("http://1.jdwzshou.sinaapp.com/jdt/wx/tice/13/test.php")
                .post(requestBody)
                .build();
        Call call3 = client.newCall(request);
        call3.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("error!!!","访问失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("访问成功","开始获得信息");
                String htmlheader = response.headers().toString();
                String htmlbody = response.body().string();
                Log.i(htmlheader,htmlbody);
                Jsoup_one startjsoup = new Jsoup_one(htmlbody);
                startjsoup.getMessage();
//                Map<String,String> message = startjsoup.getzaowan();
                Map<String,String> message = null;
                message.put("zao","222");
                message.put("wan","2222");
                Message message1 = handler.obtainMessage();
                Message message2 = handler.obtainMessage();
                Log.i("早锻炼=====",message.get("zao"));
                Log.i("晚锻炼=====",message.get("wan"));
                message2.obj = message.get("wan");
                message1.obj = message.get("zao");
                message1.what=SUCCESS_two;
                message2.what=4;
                handler.sendMessage(message1);
                handler.sendMessage(message2);
//
//                zaoduanlian.setText(message.get("zaoduanlian"));
//                wanduanklian.setText(message.get("wanduanlian"));
                //写一个存储类
                //写一个Jsoup工具类
            }
        });
    }
    public void login(){
        Log.i("info_login","知道了session  ："+cookie);
        Log.e("username",username.getText().toString().trim());
        FormBody body = new FormBody.Builder()

                .add("number","162208100073")
                .add("passwd","162208100073")
                //.add("number",username.getText().toString().trim())
                //.add("passwd",usecode.getText().toString().trim())
                .add("captcha",numbercode.getText().toString().trim())
                .add("select","cert_no")
                .add("returnUrl","").build();
        //.add("username",username.getText().toString()).build();
        final Request request = new Request.Builder()
                 .addHeader("cookie",cookie)
                .url("http://opac.lib.jhun.edu.cn:8080/reader/redr_verify.php")
                .post(body)
                .build();
        Call call2 = mokhttpclient.newCall(request);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info_call2fail",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //写一个存储的工具类，三参为body 上下文 学号
                Headers headers = response.headers();
                MyCookies myCookies = new MyCookies(headers,cookie);
                cookie = myCookies.getcookie();
                Log.e("usercooike",cookie);
                Log.v("Heaers:",headers.toString());
                MyWriter mywriter = new MyWriter(response.body().string(),MainActivity.this,"162208100073");
//                if(response.isSuccessful()){
//                    Log.i("info_call2fail",response.body().toString());
//                }
//                Headers headers = response.headers();
//                Log.i("info_response.headers",headers+"");
//                Log.i("body=====",response.body().string());
        //下面是使用sharePereference***************************************
//                BufferedReader x = new BufferedReader(response.body().charStream());
//                line = x.readLine();
//
//
//                while(line!=null){
//                    Log.e("22","+++++++");
//                    set.add(line);
//                    set.add("/n");
//                    line=x.readLine();
//                }
// **************************************************************
//                editor.putString("messages",response.body().string());
//                editor.apply();
// **************************************************************
//                x.close();

// **************************************************************
//                FileOutputStream out = null;
//                BufferedWriter writer = null;
//                out = openFileOutput("two", Context.MODE_PRIVATE);
//                writer = new BufferedWriter(new OutputStreamWriter(out));
//                writer.write(response.body().string());
//                writer.close();
// **************************************************************
//                FileInputStream in = null ;
//                BufferedReader read = null;
//                in = openFileInput("two");
//                read = new BufferedReader(new InputStreamReader(in));
//                 line = read.readLine();
//                while(line!=null){
//                    Log.e("feifei",line);
//                    line = read.readLine();
//                }
//                read.close();
// **************************************************************
                Myreader myreader = new Myreader("162208100073",MainActivity.this);

//                String buffer = myreader.gethtml();
//                Log.e("sdaih",buffer);
            }
        });
        Toast.makeText(this, "恭喜"+"162208100073"+"登陆成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,User_message.class);
        intent.putExtra("usercookie",cookie);
        startActivity(intent);
    }

}
