package com.clarussapps.lovepoem;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FavoriteActivity extends ListActivity{
	TextView tvHeading;
	
	ArrayList<Quote> fav; 
	public static String INTENT_STRING_QUOTE="quote";
	public static String INTENT_STRING_QUOTE_ID="quote_id";
	Typeface tf;
	CAFav adapter;
	CAFavDelete adapterDelete;
	ListView lv;
	DatabaseHelper db;
	
	Button btnEditDone;
	boolean IN_EDIT_MODE = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fav);
		
		tvHeading = (TextView)findViewById(R.id.fav_heading);
		btnEditDone = (Button)findViewById(R.id.fav_btn_editdone);
		Typeface face=Typeface.createFromAsset(getAssets(), "Kefa.ttc");
		tvHeading.setTypeface(face);
		
		db=MyApplication.getDataBase();
		
		tf = Typeface.createFromAsset(getBaseContext().getAssets(),
				"Gabriola.ttf");
		lv=getListView();
	    fav=db.getAllFavQuote();
	    adapter=new CAFav(this, R.layout.row_mainlist, fav);
	    
	    lv.setAdapter(adapter);
	    
	    lv.setOnItemClickListener(new OnItemClickListener() {

	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		if(IN_EDIT_MODE == false){
	    			Intent intent=new Intent(FavoriteActivity.this,FavouriteInnerActivity.class);
	    			Quote quote=((Quote)arg0.getAdapter().getItem(arg2));
	    			Log.e("item id", "quote id:"+quote.getQuoteId());

	    			intent.putExtra(INTENT_STRING_QUOTE, quote.getQuote());
	    			intent.putExtra(INTENT_STRING_QUOTE_ID, quote.getQuoteId());
	    			startActivity(intent);
	    		}
	    	}
	    });
	    
	    btnEditDone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(IN_EDIT_MODE){
					IN_EDIT_MODE = false;
					btnEditDone.setText("EDIT");
					lv.setAdapter(adapter);
					
				}else{
					IN_EDIT_MODE = true;
					btnEditDone.setText("DONE");
					lv.setAdapter(adapterDelete);
				}
			}
		});
	    RelativeLayout layout = (RelativeLayout) findViewById(R.id.admoblayout);
		AdView adView = new AdView(this, AdSize.BANNER, MainActivity.MY_BANNER_UNIT_ID);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		//request.setTesting(true);
		adView.loadAd(request);
	}
	@Override
	protected void onResume() {
		fav=db.getAllFavQuote();
	    adapter=new CAFav(this, R.layout.row_mainlist, fav);
	    adapterDelete = new CAFavDelete(this, R.layout.row_del_fav, fav);
	    adapter.notifyDataSetChanged();
	    lv.setAdapter(adapter);
	    btnEditDone.setText("EDIT");
		IN_EDIT_MODE = false;
		super.onResume();
	}
}
