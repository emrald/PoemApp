package com.clarussapps.lovepoem;

import org.json.JSONObject;
import org.json.JSONTokener;

import com.clarussapps.lovepoem.Twitter.TwitterApp;
import com.clarussapps.lovepoem.Twitter.TwitterApp.TwDialogListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public static final String MY_BANNER_UNIT_ID ="a1519609999c356";

	TextView heading;
	Button btn_SendSMS, btn_Share_fb, btn_MakeFav,btnListView,btnPrevious,btnNext,btndummy1,btndummy2,btn_Share_tw;

	static Typeface tf;
	static Context aContext;

	int lenQuoteArray;
	TextView tvQuote;
	int quoteId;
	DatabaseHelper db;

	//twitter
	private TwitterApp mTwitter;
	private static final String twitter_consumer_key = "8Q7tu5OoUnKxwEJPT3g";// "cNjYnzVzmyPe3ToNn8lSrQ";
	private static final String twitter_secret_key = "YdrD8VJjOfNT5wUvSX4ewZuwOotGsx7fNtx46xyk";// "qgzVsQdxSZQ1mzKkDZD0WlLxTqYkFOshAUqq9Pblw";

	//Facebook
	public static final String APP_ID = "415648985168274";
	private Facebook mFacebook;
	private String[] mPermissions;
	private ProgressDialog mProgress;
	private Handler mRunOnUi = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		heading = (TextView)findViewById(R.id.heading);
		btnListView = (Button)findViewById(R.id.main_btn_gridlist);
		tvQuote = (TextView)findViewById(R.id.main_tv_quote);
		btnNext = (Button)findViewById(R.id.main_btn_next);
		btnPrevious = (Button)findViewById(R.id.main_btn_previous);
		btn_MakeFav = (Button)findViewById(R.id.main_btn_makeitfav);
		btn_SendSMS = (Button)findViewById(R.id.main_btn_sendsms);
		btn_Share_fb = (Button)findViewById(R.id.main_btn_share);
		btn_Share_tw = (Button)findViewById(R.id.main_btn_share1);
		btndummy1 = (Button)findViewById(R.id.dummy1);
		btndummy2 = (Button)findViewById(R.id.dummy2);
		Typeface face=Typeface.createFromAsset(getAssets(), "Kefa.ttc");
		heading.setTypeface(face);

		lenQuoteArray = ListQuoteActivity.quoteArray.size();
		Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
		tf = Typeface.createFromAsset(getAssets(),"Gabriola.ttf");
		tvQuote.setTypeface(tf);
	//	tvQuote.setText(q.getQuote());
		tvQuote.setText(Html.fromHtml(q.getQuote()));
		quoteId = q.getQuoteId();

		db = MyApplication.getDataBase();

		mTwitter = new TwitterApp(this.getParent(), twitter_consumer_key,
				twitter_secret_key);
		mProgress       = new ProgressDialog(this.getParent());
		mFacebook       = new Facebook(APP_ID);
		mPermissions = new String[] {"publish_stream", "read_stream"};

		btndummy1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ListQuoteActivity.currentSelected == (lenQuoteArray-1)){
					ListQuoteActivity.currentSelected = 0;
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}else{
					ListQuoteActivity.currentSelected ++;
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}

			}
		});
		btnNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ListQuoteActivity.currentSelected == (lenQuoteArray-1)){
					ListQuoteActivity.currentSelected = 0;
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}else{
					ListQuoteActivity.currentSelected ++;
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}

			}
		});
		btndummy2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ListQuoteActivity.currentSelected == 0){
					ListQuoteActivity.currentSelected = (lenQuoteArray - 1);
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}else{
					ListQuoteActivity.currentSelected --;
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}

			}
		});
		btnPrevious.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ListQuoteActivity.currentSelected == 0){
					ListQuoteActivity.currentSelected = (lenQuoteArray - 1);
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}else{
					ListQuoteActivity.currentSelected --;
					Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
				//	tvQuote.setText(q.getQuote());
					tvQuote.setText(Html.fromHtml(q.getQuote()));
					quoteId = q.getQuoteId();
				}

			}
		});


		btnListView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				QuoteGroupActivity.group.forthView();
			}
		});

		btn_MakeFav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					//int id=quoteArray.get(HScrollview.mCurrentScreen).getQuoteId();
					if(db.makeQuoteFav(quoteId)){
						Toast.makeText(MainActivity.this, "Added Quote to favourite", Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btn_SendSMS.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("text/plain");
			//	intent.setType();
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
			//	intent.putExtra(Intent.EXTRA_TEXT, q.getQuote());
				intent.putExtra(Intent.EXTRA_TEXT, tvQuote.getText().toString().trim());
			//	intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(q.getQuote()));
				startActivity(Intent.createChooser(intent, "Share Quote"));

			}
		});
		btn_Share_tw.setOnClickListener(new View.OnClickListener() {
				final Dialog dialog = new Dialog(MainActivity.this.getParent());
					@Override
					public void onClick(View v) {
						mTwitter.setListener(mTwLoginDialogListener);
						if (mTwitter.hasAccessToken()) {
							showTwittDialog();
						} else {
							mTwitter.authorize();
						}	
						dialog.dismiss();
					}
				});

		btn_Share_fb.setOnClickListener(new View.OnClickListener() {
				final Dialog dialog = new Dialog(MainActivity.this.getParent());
					@Override
					public void onClick(View v) {

						SessionStore.restore(mFacebook, MainActivity.this);   

						onFacebookClick();
						dialog.dismiss();
					}
					
				});
		
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.admoblayout);
		AdView adView = new AdView(this, AdSize.BANNER, MY_BANNER_UNIT_ID);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		//request.setTesting(true);
		adView.loadAd(request);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.getParent().finish();
	}
	@Override
	protected void onResume() {
		super.onResume();
		Log.e("MainScreen", String.valueOf(ListQuoteActivity.currentSelected));
		Quote q= ListQuoteActivity.quoteArray.get(ListQuoteActivity.currentSelected);
	//	tvQuote.setText(q.getQuote());
		tvQuote.setText(Html.fromHtml(q.getQuote()));
		quoteId = q.getQuoteId();
	}

	private void onFacebookClick() {
		mFacebook.authorize(this.getParent(), mPermissions, -1, new FbLoginDialogListener());
	}

	private final class FbLoginDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			SessionStore.save(mFacebook, MainActivity.this.getParent());

			getFbName();
			postToFacebook(tvQuote.getText().toString().trim());
		}

		@Override
		public void onFacebookError(FacebookError error) {
			Toast.makeText(MainActivity.this.getParent(), "Facebook connection failed", Toast.LENGTH_SHORT).show();

			// mFacebookBtn.setChecked(false);
		}

		@Override
		public void onError(DialogError error) {
			Toast.makeText(MainActivity.this.getParent(), "Facebook connection failed", Toast.LENGTH_SHORT).show();

			// mFacebookBtn.setChecked(false);
		}

		@Override
		public void onCancel() {
			// mFacebookBtn.setChecked(false);
		}


	}

	private void getFbName() {
		mProgress.setMessage("Finalizing ...");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				String name = "";
				int what = 1;

				try {
					String me = mFacebook.request("me");

					JSONObject jsonObj = (JSONObject) new JSONTokener(me).nextValue();
					name = jsonObj.getString("name");
					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
			}
		}.start();


	}

	private void fbLogout() {
		mProgress.setMessage("Disconnecting from Facebook");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(MainActivity.this.getParent());

				int what = 1;

				try {
					mFacebook.logout(MainActivity.this.getParent());

					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				mmHandler.sendMessage(mmHandler.obtainMessage(what));
			}
		}.start();
	}
	private Handler mFbHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 0) {
				String username = (String) msg.obj;
				username = (username.equals("")) ? "No Name" : username;

				SessionStore.saveName(username, MainActivity.this.getParent());

				//    mFacebookBtn.setText("  Facebook (" + username + ")");

				Toast.makeText(MainActivity.this, "Connected to Facebook as " + username, Toast.LENGTH_SHORT).show();


			} else {
				Toast.makeText(MainActivity.this, "Connected to Facebook", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Handler mmHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 1) {
				Toast.makeText(MainActivity.this.getParent(), "Facebook logout failed", Toast.LENGTH_SHORT).show();
			} else {
				//mFacebookBtn.setChecked(false);
				//  mFacebookBtn.setText("  Facebook (Not connected)");
				//  mFacebookBtn.setTextColor(Color.GRAY);

				Toast.makeText(MainActivity.this.getParent(), "Disconnected from Facebook", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private void postToFacebook(String review) {
		mProgress.setMessage("Posting ...");
		mProgress.show();

		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);

		Bundle params = new Bundle();
		params.putString("message", review);
		params.putString("name", "Love Poem");
		params.putString("caption", "Love Poem ");
		params.putString("link", "https://play.google.com/store/apps/details?id=com.clarussapps.lovepoem");
		params.putString("description","Inspirational quotes, Motivation quotes, love quote, Great quotes, Inspiration thoughts, Motivational Thoughts, Best Quotes, Free Quotes, Free thoughts,heart quotes"); 
		//params.putString("picture", "http://swamivivekananda.jnanajyoti.com/intro_swami_vivekananda.jpg");
		mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener());
	}
	private final class WallPostListener extends BaseRequestListener {
		@Override
		public void onComplete(final String response) {
			mRunOnUi.post(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();

					Toast.makeText(MainActivity.this.getParent(), "Posted to Facebook", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	//For twitter

	private TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

		public void onError(String value) {
			showToast("Login Failed");
			mTwitter.resetAccessToken();
		}

		public void onComplete(String value) {
			showTwittDialog();
		}
	};
	private void showTwittDialog() {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialog);

		Button btnPost = (Button) dialog.findViewById(R.id.btnpost);
		final TextView et = (TextView) dialog.findViewById(R.id.twittext);
		et.setText("Love Poem app");
		btnPost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					mTwitter.updateStatus(tvQuote.getText().toString().trim()+"\nhttps://play.google.com/store/apps/details?id=com.clarussapps.lovepoem");
					showToast("Posted Successfully");
				} catch (Exception e) {
					if (e.getMessage().toString().contains("duplicate")) {
						showToast("Posting Failed because of duplicate message...");
					}
					e.printStackTrace();
				}
				dialog.dismiss();
			}
		});
		dialog.show();

	}
	void showToast(String msg) {
		Toast.makeText(this.getParent(), msg, Toast.LENGTH_SHORT).show();
	}
}
