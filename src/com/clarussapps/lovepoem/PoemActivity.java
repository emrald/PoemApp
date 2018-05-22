package com.clarussapps.lovepoem;


import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PoemActivity extends Activity{
	TextView heading;
	TextView tvPeoms;
	Button btnNext,btnPrevious;
	DatabaseHelper db;
	ArrayList<Quote> peomsArray;
	int currentSelected = 0 , lenArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poem);
		heading = (TextView)findViewById(R.id.poem_heading);
		tvPeoms = (TextView)findViewById(R.id.poem_tv_poem);
		btnNext = (Button)findViewById(R.id.poem_btn_next);
		btnPrevious = (Button)findViewById(R.id.poem_btn_previous);
		
		Typeface face=Typeface.createFromAsset(getAssets(), "Kefa.ttc");
		
		heading.setTypeface(face);
		
		db = MyApplication.getDataBase();
		tvPeoms.setMovementMethod(ScrollingMovementMethod.getInstance());
	//	peomsArray = db.getPeoms();
		lenArray = peomsArray.size();
		if(peomsArray.isEmpty() == false){
			tvPeoms.setText(Html.fromHtml(peomsArray.get(0).getQuote()));
			currentSelected = 0;
		}
		
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentSelected == (lenArray-1)){
					currentSelected = 0;
					Quote q= peomsArray.get(currentSelected);
					tvPeoms.setText(Html.fromHtml(q.getQuote()));
				}else{
					currentSelected ++;
					Quote q= peomsArray.get(currentSelected);
					tvPeoms.setText(Html.fromHtml(q.getQuote()));
				}
			}
		});
		btnPrevious.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentSelected == 0){
					currentSelected = (lenArray - 1);
					Quote q= peomsArray.get(currentSelected);
					tvPeoms.setText(Html.fromHtml(q.getQuote()));
				}else{
					currentSelected --;
					Quote q= peomsArray.get(currentSelected);
					tvPeoms.setText(Html.fromHtml(q.getQuote()));
				}// TODO Auto-generated method stub
				
			}
		});
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.admoblayout);
		AdView adView = new AdView(this, AdSize.BANNER, MainActivity.MY_BANNER_UNIT_ID);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		//request.setTesting(true);
		adView.loadAd(request);
	}

}
