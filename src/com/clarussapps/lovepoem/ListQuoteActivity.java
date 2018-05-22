package com.clarussapps.lovepoem;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class ListQuoteActivity extends Activity{
	Button btnBrowse;
	ListView lvMailList;
	public static int currentSelected = 0;
	static CAMainList adapter;
	static Context aContext;
	DatabaseHelper db;
	static ArrayList<Quote> quoteArray;
	
	static ProgressDialog pd;
	static MyUIHandler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listquote);
		
		btnBrowse = (Button)findViewById(R.id.list_btn_browse);
		lvMailList = (ListView)findViewById(R.id.list_lv_main);
		this.aContext=this;
		mHandler=new MyUIHandler();
		db=MyApplication.getDataBase();
		quoteArray=new ArrayList<Quote>();
		quoteArray.clear();
		
		lvMailList.setCacheColorHint(Color.TRANSPARENT);
		
		pd=ProgressDialog.show(this.getParent(), "", "Loading data");
	//	pd.dismiss();
		
		new Thread(){
			public void run(){
				quoteArray=db.getQuote();
				ListQuoteActivity.mHandler.sendEmptyMessage(1);
			}
		}.start();
		
		btnBrowse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*Intent in = new Intent(ListQuoteActivity.this,Mainscreen.class);
				startActivity(in);*/
				Intent BOJintent = new Intent(ListQuoteActivity.this, MainActivity.class);

                // Create the view using PlaylistGroup's LocalActivityManager
                View view = QuoteGroupActivity.group.getLocalActivityManager().startActivity("MainScreenActivity", BOJintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

                // Again, replace the view
                QuoteGroupActivity.group.replaceView(view);

                Intent in  = new Intent(ListQuoteActivity.this, MainActivity.class);
                startService(in);
			}
		});
		
		
		lvMailList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/*
				Log.e("Current selected", String.valueOf(currentSelected));
				Intent in = new Intent(ListQuoteActivity.this,Mainscreen.class);
				startActivity(in);*/
				currentSelected = arg2;
         		Intent BOJintent = new Intent(ListQuoteActivity.this, MainActivity.class);

                // Create the view using PlaylistGroup's LocalActivityManager
                View view = QuoteGroupActivity.group.getLocalActivityManager().startActivity("MainScreenActivity", BOJintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

                // Again, replace the view
                QuoteGroupActivity.group.replaceView(view);

                Intent in  = new Intent(ListQuoteActivity.this, MainActivity.class);
                startService(in);
			}
		});
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.admoblayout);
		AdView adView = new AdView(this, AdSize.BANNER, MainActivity.MY_BANNER_UNIT_ID);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		//request.setTesting(true);
		adView.loadAd(request);
		
	}
	class MyUIHandler extends Handler{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==1){
				Log.e("Total quote is", "quotes:"+quoteArray.size());
				ListQuoteActivity.adapter = new CAMainList(aContext, R.layout.row_mainlist, quoteArray);
				lvMailList.setAdapter(ListQuoteActivity.adapter);
				pd.dismiss();
			}

		}
	}
}
