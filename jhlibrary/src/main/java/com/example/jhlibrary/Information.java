package com.example.jhlibrary;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Information extends AppCompatActivity {

	private TextView tv1;
	private ListView listView;
	static private String result;

	public static List<HashMap<String, String>> information = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		initView();
		result = getIntent().getExtras().getString("我是key");
		hh();

	}

	private void initView() {
		tv1 = (TextView) findViewById(R.id.tvinfo);
		listView = (ListView) this.findViewById(R.id.listV);
	}

	public void hh() {
		Message msg = new Message();
		// msg.what = 1;
		msg.obj = result;
		mhandler.sendMessage(msg);
	}

	Handler mhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			result = msg.obj.toString();
			information.clear();
			information = analysis.analysisInformation(result);

			ListAdapter mAdapter = new SimpleAdapter(Information.this,
					information, R.layout.item, new String[] { "Information" },
					new int[] { R.id.t1 });
			listView.setAdapter(mAdapter);
		}
	};
}