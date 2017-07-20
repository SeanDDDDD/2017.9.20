package com.example.jhlibrary;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {


    private Button bt1, bt2;
    private Handler handler;
    private String result = "";
    private String number;
    private String captcha;
    private String password;
    private String select = "cert_no";
    private String returnUrl = "";
    private EditText et1, et2, et3;
    private CheckBox cb1, cb2;
    private ImageView iv;
    static public Intent intent;

    private SharedPreferences mSettings = null;
    public static OkHttpClient client =  new OkHttpClient.Builder().cookieJar(new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }).build();

    public static final String PREFS_NAME = "prefsname"; // 偏好设置名称
    public static final String REMEMBER_USERID_KEY = "remember"; // 记住用户名
    public static final String USERID_KEY = "userid"; // 用户名标记
    private static final String DEFAULT_USERNAME = "142208100007"; // 默认用户名


    // 保存用户名
    private void saveUserName(String userid) {
        SharedPreferences.Editor editor;
        editor = mSettings.edit();// 获取编辑器
        editor.putString(USERID_KEY, userid);
        editor.commit(); // 保存数据
        // editor.clear();//清除数据
    }

    // 设置是否保存的用户名
    private void saveRemember(boolean remember) {
        SharedPreferences.Editor editor;
        editor = mSettings.edit();// 获取编辑器
        editor.putBoolean(REMEMBER_USERID_KEY, remember);
        editor.commit();
    }

    // 获取保存的用户名
    private String getUserName() {
        return mSettings.getString(DEFAULT_USERNAME, DEFAULT_USERNAME);
    }


    // 获取是否保存的用户名
    private boolean getRemember() {
        return mSettings.getBoolean(REMEMBER_USERID_KEY, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = "";
        initView(); // 找id
        Getauthcode(); // 获取验证码
        initEvents(); // 监听

    }

    // 获取id
    private void initView() {
        bt1 = (Button) findViewById(R.id.register_id);
        bt2 = (Button) findViewById(R.id.change_id);
        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);
        cb1 = (CheckBox) findViewById(R.id.visable_id);
        cb2 = (CheckBox) findViewById(R.id.remember_id);
        iv = (ImageView) findViewById(R.id.imageView1);

        mSettings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); // 模式为私有
        cb2.setChecked(getRemember()); // 勾选记住用户名
        et1.setText(getUserName()); // 设置用户名


    }

    // 监听
    private void initEvents() {
        // 访问请求
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                number = et1.getText().toString();
                password = et2.getText().toString();
                captcha = et3.getText().toString();

                // 保存用户名

                if (cb2.isChecked()) {
                    saveRemember(true);
                    saveUserName(et1.getText().toString());
                } else {
                    saveRemember(false);
                    saveUserName("");
                }

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        login();
                    }
                }).start();

            }
        });

        // 显示密码
        cb1.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO 自动生成的方法存根
                if (cb1.isChecked()) {
                    // 设置文本课件
                    et2.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {
                    // 设置文本隐藏
                    et2.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                }
            }
        });

        // 刷新
        bt2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Getauthcode();
            }
        });
    }

    // 获取验证码的方法实现
    public void Getauthcode() {
        new Thread(new Runnable() {
            public void run() {
                final Bitmap bitmap = getPicture("http://opac.lib.jhun.edu.cn:8080/reader/captcha.php");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                iv.post(new Runnable() {

                    public void run() {

                        iv.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    // 将图片存在bitmap里
    public Bitmap getPicture(String path) {
        Bitmap bm = null;

        try {

            bm = null;
            Request request = new Request.Builder().url(path).build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) { //普通同步请求

                InputStream is = response.body().byteStream();// 读取实体中内容


                bm = BitmapFactory.decodeStream(is);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bm;
    }

    // 登陆
    public void login() {

        try {
            String target = "http://opac.lib.jhun.edu.cn:8080/reader/redr_verify.php";


                    RequestBody requestBody = new FormBody.Builder()

                    .add("number", number)
                    .add("passwd", password)
                    .add("captcha", captcha)
                    .add("select", select)
                    .add("returnUrl", returnUrl)
                    .build();
            Request request = new Request.Builder()

                    .url(target)
                    .post(requestBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    result = response.body().string();

                    //	Log.e("sss", result);

                    if (result.indexOf("证件信息") != -1) {
                        Intent in = new Intent(MainActivity.this, Catelog.class);
                        Bundle b = new Bundle();
                        b.putString("我是key", result);
                        // 此处使用putExtras，接受方就响应的使用getExtra
                        in.putExtras(b);
                        startActivity(in);
                    } else {
                        result = "";
                    }
                    Message msg = new Message();
                    msg.obj = result;
                    handler1.sendMessage(msg);
                }
            });

        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }

    Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            result = msg.obj.toString();
            if (result.indexOf("证件信息") != -1) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "登陆成功", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "登陆失败", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    };
}
