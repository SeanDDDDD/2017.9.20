package com.example.jhlibrary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import okhttp3.Request;
import okhttp3.Response;

import static com.example.jhlibrary.MainActivity.client;

public class Catelog extends Activity {
	private ListView lv1;
	private String url;
	 String s ="";			//每次清空数据 以免数据混乱发生异常
	 Button bt;


	static private String result;
	public static List<HashMap<String, String>> catelog = new ArrayList<HashMap<String, String>>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.catelog_list);
		result = getIntent().getExtras().getString("我是key");
		initView();
		hh();
		initEvent();
	}

	private void initView() {
		lv1 = (ListView) this.findViewById(R.id.catelog_lv);
		bt = (Button) this.findViewById(R.id.searchbook_bt);
	}

	private void initEvent(){
        ListClickListener l = new ListClickListener();
        lv1.setOnItemClickListener(l);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(Catelog.this, SecActivity.class);
				startActivity(in);

			}
		});
	}

	class ListClickListener  implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {

            // arg1.setVisibility(View.GONE);
            String text = lv1.getItemAtPosition(position)+"";
         //   Log.i("tag",text);
            catelog.get(position);
            s = "";
            int i = 0;
            while( text.charAt(i) !=',')
            {

            	s = s+text.charAt(i);
            	i++;
            }
            StringBuilder bf = new StringBuilder(s);
            bf.delete(0,5);
            s = bf.toString();
        //    Log.i("taaaaaa",s);

			new Thread(new Runnable() {
				@Override
				public void run() {
		            try {
						Request request = new Request.Builder().url(s).build();
						Response response = client.newCall(request).execute();

						if (response.isSuccessful()) {
							s = response.body().string();
							Intent in = new Intent(Catelog.this, Information.class);
							Bundle b = new Bundle();
							b.putString("我是key", s);
							// 此处使用putExtras，接受方就响应的使用getExtra
							in.putExtras(b);

							startActivity(in);

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

         }

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
			catelog.clear();
			catelog = analysis.analysisCatelog(result);

			ListAdapter mAdapter = new SimpleAdapter(Catelog.this, catelog,
					R.layout.catelog_itm, new String[] { "Catelog" },
					new int[] { R.id.catelog_tv1 });
			lv1.setAdapter(mAdapter);
		}
	};


}
