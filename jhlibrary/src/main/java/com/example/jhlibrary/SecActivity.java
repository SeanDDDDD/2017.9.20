package com.example.jhlibrary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;





import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecActivity extends AppCompatActivity{
	private ListView bookListView;
	private EditText bookNameSearch;
	private Button searchButton;

	private String searchName;
	private List<HashMap<String, String>> bookList = new ArrayList<HashMap<String,String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.two);

		initView();
		initEvents();
	}

	
	private void initView(){
		bookListView = (ListView)this.findViewById(R.id.myList);
		bookNameSearch = (EditText) this.findViewById(R.id.editText2_1);
		searchButton = (Button)this.findViewById(R.id.button2_1);
	}
	
	private void initEvents(){
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				searchName = bookNameSearch.getText().toString();
				startNewThread();
			}
		});
	}
	
	private void startNewThread() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					OkHttpClient client = new OkHttpClient();

					Request request = new Request.Builder()
					.url("http://opac.lib.jhun.edu.cn:8080/opac/openlink.php?"
									+ "strSearchType=title&strText="
									+ searchName)
					.build();
					Response response = client.newCall(request).execute();

					if (response.isSuccessful()) {

						String html = response.body().string();

						Log.e("kkkkkk", html);

						Message msg = new Message();
						msg.what = 1;
						msg.obj = html;
						mhandler.sendMessage(msg);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}

	Handler mhandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {
				String html = msg.obj.toString();
				bookList.clear();
				bookList = ThirdActivity.analysis(html);
					
				ListAdapter mAdapter = new SimpleAdapter(SecActivity.this,bookList,R.layout.list_cell,new String []{"bookName","bookNum"},new int []{R.id.bookNameView,R.id.bookNumView});
				bookListView.setAdapter(mAdapter);
			}

		}

	};
}
