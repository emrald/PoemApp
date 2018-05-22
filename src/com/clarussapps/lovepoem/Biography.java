package com.clarussapps.lovepoem;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.clarussapps.lovepoem.HScrollview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Biography extends Activity {
	static HScrollview hScrollview;
	ImageButton iBtnShare;
	AssetManager am;
	String[] wishcard;
	static ProgressDialog pd;
	static Bitmap d;
	static Context context;

	Button btnBrowse,btnFavourite,btnMore;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.biopage);
		context = this;
		hScrollview = (HScrollview) findViewById(R.id.hscrollview);
		iBtnShare=(ImageButton)findViewById(R.id.main_ib_share);
		am=this.getAssets();
		pd=ProgressDialog.show(Biography.this, "Loading...", "Please wait");

		//Tab Buttons
		/*btnBrowse = (Button)findViewById(R.id.btnBrowse);
		btnFavourite = (Button)findViewById(R.id.btnFavourite);
		btnMore = (Button)findViewById(R.id.btnMore);
*/
		new Thread(){
			@Override
			public void run(){
				try {
					wishcard = am.list("card");

					for(String name:wishcard){
						System.out.println(name);
						InputStream is=am.open("card/"+name);
						d = decodeUri("card/"+name);
						/*d=Drawable.createFromStream(is, null);*/
						is.close();
						synchronized (mHandler) {
							Log.d("Thread", "before 4");
							mHandler.sendEmptyMessage(1);
							mHandler.wait();
						}
					}
					mHandler.sendEmptyMessage(0);
				} catch (IOException e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(0);
				}catch (Exception e) {
					e.printStackTrace();
					mHandler.sendEmptyMessage(0);
				}
			}
		}.start();

		iBtnShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int current=hScrollview.getCurrentScreen();
				Log.e("Current screen", "Screen:"+current);
				if(!pd.isShowing())pd=ProgressDialog.show(Biography.this, "Loading...", "Please wait");

				new Thread(){
					@Override
					public void run(){
						try {

							String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ValentinesQuotes/";

							File dir = new File(file_path);
							dir.mkdirs();
							File file = new File(dir, "ValentinesQuotes.png");
							FileOutputStream fOut = new FileOutputStream(file);

							/*BitmapFactory.Options options = new BitmapFactory.Options();
							options.inSampleSize = 8;

							BufferedInputStream buf = new BufferedInputStream(am.open("card/"+wishcard[hScrollview.getCurrentScreen()]));
							Bitmap bitmap = BitmapFactory.decodeStream(buf,null,options);
							 */
							Bitmap bitmap = decodeUri("card/"+wishcard[hScrollview.getCurrentScreen()]);

							bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
							fOut.flush();
							fOut.close();
							if(bitmap != null && !bitmap.isRecycled()){
								bitmap.recycle();
							}

							bitmap=null;


							Intent shareIntent = new Intent(Intent.ACTION_SEND);
							shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
							shareIntent.setType("image/png");
							shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

							shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Valentines Quotes");
							shareIntent.putExtra(Intent.EXTRA_TEXT, "Valentines Quotes \nhttps://play.google.com/store/apps/details?id=com.clarusapps.valentinesquotes");
							shareIntent.putExtra(Intent.EXTRA_TITLE, "Valentines Quotes \nhttps://play.google.com/store/apps/details?id=com.clarusapps.valentinesquotes");
							mHandler.sendEmptyMessage(0);
							startActivity(Intent.createChooser(shareIntent, "Share Via Valentines Quotes"));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							mHandler.sendEmptyMessage(0);
						}catch (Exception e) {
							e.printStackTrace();
							mHandler.sendEmptyMessage(0);
						}catch (OutOfMemoryError e) {
							Toast.makeText(getApplicationContext(), "Low Space\nPlease Free some space on your SD Card", Toast.LENGTH_SHORT).show();
						}
					}
				}.start();

			}
		});


		//ECard Button Click Event
		/*btnBrowse.setTypeface(MainActivity.tf);
		btnBrowse.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Biography.this, MainActivity.class);
				finish();
				startActivity(i);
			}
		});


		//Favourit Button Click Event
		btnFavourite.setTypeface(MainActivity.tf);
		btnFavourite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Biography.this, FavoriteActivity.class);
				finish();
				startActivity(i);
			}
		});

		//More Button Click Event
		btnMore.setTypeface(MainActivity.tf);
		btnMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Biography.this, MoreActivity.class);
				finish();
				startActivity(i);
			}
		});*/
	}
	public static Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			if(msg.what==0){
				if(pd != null && pd.isShowing()){
					pd.dismiss();
				}
			}else if(msg.what == 1){
				ImageView iv=new ImageView(context);
				iv.setImageBitmap(d);
				hScrollview.addView(iv);
				d=null;
				System.gc();
			}
			synchronized (mHandler) {
				Log.d("loadFacilityViewThread", "notify");
				mHandler.notifyAll();
			}
		}
	};
	/*private Bitmap getBitmapFromAsset(String strName)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		AssetManager assetManager = getAssets();
		InputStream istr = null;
		try {
			istr = assetManager.open(strName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(istr,null,options);
		return bitmap;
	}*/

	private Bitmap decodeUri(String strName) throws FileNotFoundException {

		AssetManager assetManager = getAssets();

		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		try {
			BitmapFactory.decodeStream(assetManager.open(strName), null, o);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// The new size we want to scale to
		final int REQUIRED_SIZE = 140;

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE
					|| height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		// Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		try {
			return BitmapFactory.decodeStream(assetManager.open(strName), null, o2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	@Override
	public void onBackPressed() 
	{
		System.exit(0);
	}
}
