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

public class StoryActivity extends Activity{
	TextView heading,tvStory;
	Button btnNext,btnPrevious;
	DatabaseHelper db;
	ArrayList<Quote> storyArray;
	int currentSelected = 0 , lenArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);
		heading = (TextView)findViewById(R.id.story_heading);
		tvStory = (TextView)findViewById(R.id.story_tv_story);
		btnNext = (Button)findViewById(R.id.story_btn_next);
		btnPrevious = (Button)findViewById(R.id.story_btn_previous);
		
		Typeface face=Typeface.createFromAsset(getAssets(), "Kefa.ttc");
		
		heading.setTypeface(face);
		
		db = MyApplication.getDataBase();
		tvStory.setMovementMethod(ScrollingMovementMethod.getInstance());
//		storyArray = db.getStories();
		lenArray = storyArray.size();
		if(storyArray.isEmpty() == false){
			tvStory.setText(Html.fromHtml(storyArray.get(0).getQuote()));
			currentSelected = 0;
		}
		
		btnNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentSelected == (lenArray-1)){
					currentSelected = 0;
					Quote q= storyArray.get(currentSelected);
					tvStory.setText(Html.fromHtml(q.getQuote()));
				}else{
					currentSelected ++;
					Quote q= storyArray.get(currentSelected);
					tvStory.setText(Html.fromHtml(q.getQuote()));
				}
				
			}
		});
		btnPrevious.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(currentSelected == 0){
					currentSelected = (lenArray - 1);
					Quote q= storyArray.get(currentSelected);
					tvStory.setText(Html.fromHtml(q.getQuote()));
				}else{
					currentSelected --;
					Quote q= storyArray.get(currentSelected);
					tvStory.setText(Html.fromHtml(q.getQuote()));
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
