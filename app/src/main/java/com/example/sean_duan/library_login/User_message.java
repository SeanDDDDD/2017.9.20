package com.example.sean_duan.library_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class User_message extends AppCompatActivity {
    private List<MyMenu> myMenus = new ArrayList<>();
    private  String cookie = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        Intent intent = getIntent();
        if(cookie==null){
            cookie = intent.getStringExtra("usercookie");
        }
        Toast.makeText(this, "用户cookie为"+cookie, Toast.LENGTH_SHORT).show();
        initmyMenus();
        RecyclerView recyclerview = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutmanager);
        MyAdapters myadapters = new MyAdapters(myMenus,this,cookie);
        recyclerview.setAdapter(myadapters);
    }

    private void initmyMenus() {
        myMenus.add(new MyMenu("证件信息",R.drawable.timg));
        myMenus.add(new MyMenu("当前借阅",R.drawable.timg));
        myMenus.add(new MyMenu("借阅历史",R.drawable.timg));
        myMenus.add(new MyMenu("荐购历史",R.drawable.timg));
        myMenus.add(new MyMenu("预约信息",R.drawable.timg));
        myMenus.add(new MyMenu("委托信息",R.drawable.timg));
        myMenus.add(new MyMenu("我的书架",R.drawable.timg));
        myMenus.add(new MyMenu("书刊遗失",R.drawable.timg));
        myMenus.add(new MyMenu("读者挂失",R.drawable.timg));
        myMenus.add(new MyMenu("账目清单",R.drawable.timg));
        myMenus.add(new MyMenu("违章缴款",R.drawable.timg));
        myMenus.add(new MyMenu("我的书评",R.drawable.timg));
        myMenus.add(new MyMenu("检索历史",R.drawable.timg));

    }
}
