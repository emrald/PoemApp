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

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FavouriteInnerActivity extends Activity{
	Button btnBack,btnSMS,btnSharefb,btnSharetw;
	TextView textQuote;
	String quote;
	int quote_id;
	Typeface tf;

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
		setContentView(R.layout.activity_favinner);

		btnBack = (Button)findViewById(R.id.favinner_btn_back);
		textQuote = (TextView) findViewById(R.id.favinner_tv_quote);
		btnSMS = (Button)findViewById(R.id.favinner_btn_sendSMS);
		btnSharefb = (Button)findViewById(R.id.favinner_btn_shar);
		btnSharetw = (Button)findViewById(R.id.favinner_btn_shar1);
		try {
			quote=getIntent().getExtras().getString(FavoriteActivity.INTENT_STRING_QUOTE);
			quote_id=getIntent().getExtras().getInt(FavoriteActivity.INTENT_STRING_QUOTE_ID);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(FavouriteInnerActivity.this, "Quote not found.",Toast.LENGTH_SHORT).show();
			finish();
		}

		tf = Typeface.createFromAsset(getBaseContext().getAssets(),
				"Gabriola.ttf");

		textQuote.setTypeface(tf);
		textQuote.setTextSize(25);
		textQuote.setText(Html.fromHtml(quote));

		mTwitter = new TwitterApp(FavouriteInnerActivity.this, twitter_consumer_key,twitter_secret_key);
		mProgress       = new ProgressDialog(this);
		mFacebook       = new Facebook(APP_ID);
		mPermissions = new String[] {"publish_stream", "read_stream"};

		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		btnSMS.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				// Add data to the intent, the receiving app will decide what to do with it.
				intent.putExtra(Intent.EXTRA_TEXT, textQuote.getText().toString().trim());
			//	intent.putExtra(Intent.EXTRA_TEXT, quote);
				startActivity(Intent.createChooser(intent, "Share Poem"));

			}
		});
		btnSharetw.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(FavouriteInnerActivity.this);
				dialog.setContentView(R.layout.choose_layout);
				dialog.setTitle("Share");
				dialog.setCancelable(true);

				//Buttons on dialog
				ImageButton fbButton = (ImageButton) dialog.findViewById(R.id.btn_facebook);
				ImageButton tweetButton = (ImageButton) dialog.findViewById(R.id.btn_tweet);

				//Click event for FB button on dialog
				fbButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						SessionStore.restore(mFacebook, FavouriteInnerActivity.this);   

						onFacebookClick();
						dialog.dismiss();

					}
				});

				//Click event for Tweet button on dialog
				tweetButton.setOnClickListener(new OnClickListener() {
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

				//Show dialog
				dialog.show();
			}

		});
		btnSharetw.setOnClickListener(new View.OnClickListener() {
			final Dialog dialog = new Dialog(FavouriteInnerActivity.this);
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

		btnSharefb.setOnClickListener(new View.OnClickListener() {
			final Dialog dialog = new Dialog(FavouriteInnerActivity.this);
			@Override
			public void onClick(View v) {

				SessionStore.restore(mFacebook, FavouriteInnerActivity.this);   

				onFacebookClick();
				dialog.dismiss();
			}

		});
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.admoblayout);
		AdView adView = new AdView(this, AdSize.BANNER, MainActivity.MY_BANNER_UNIT_ID);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		//request.setTesting(true);
		adView.loadAd(request);
	}
	private void onFacebookClick() {
		mFacebook.authorize(this, mPermissions, -1, new FbLoginDialogListener());
	}

	private final class FbLoginDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			SessionStore.save(mFacebook, FavouriteInnerActivity.this);

			getFbName();
			postToFacebook(quote.trim());
		}

		@Override
		public void onFacebookError(FacebookError error) {
			Toast.makeText(FavouriteInnerActivity.this, "Facebook connection failed", Toast.LENGTH_SHORT).show();

			// mFacebookBtn.setChecked(false);
		}

		@Override
		public void onError(DialogError error) {
			Toast.makeText(FavouriteInnerActivity.this, "Facebook connection failed", Toast.LENGTH_SHORT).show();

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
				SessionStore.clear(FavouriteInnerActivity.this);

				int what = 1;

				try {
					mFacebook.logout(FavouriteInnerActivity.this);

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

				SessionStore.saveName(username, FavouriteInnerActivity.this);

				//    mFacebookBtn.setText("  Facebook (" + username + ")");

				Toast.makeText(FavouriteInnerActivity.this, "Connected to Facebook as " + username, Toast.LENGTH_SHORT).show();


			} else {
				Toast.makeText(FavouriteInnerActivity.this, "Connected to Facebook", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Handler mmHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 1) {
				Toast.makeText(FavouriteInnerActivity.this, "Facebook logout failed", Toast.LENGTH_SHORT).show();
			} else {
				//mFacebookBtn.setChecked(false);
				//  mFacebookBtn.setText("  Facebook (Not connected)");
				//  mFacebookBtn.setTextColor(Color.GRAY);

				Toast.makeText(FavouriteInnerActivity.this, "Disconnected from Facebook", Toast.LENGTH_SHORT).show();
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
		params.putString("description","Expression of love is very imperative, and of course, you need nothing less than perfect for the occasion. What about heart-touching and mesmerizing love quotes that can steal anyone’s heart easily. Claruss App has recently introduced an app that flaunts impressive quotes on love and can penetrate in deep in anyone’s heart.  You can send your girlfriend, wife or anybody else you love amazing love quotes or love story from this app, and let them know how much you love them."); 
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

					Toast.makeText(FavouriteInnerActivity.this, "Posted to Facebook", Toast.LENGTH_SHORT).show();
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
		final Dialog dialog = new Dialog(FavouriteInnerActivity.this);
		dialog.setContentView(R.layout.dialog);

		Button btnPost = (Button) dialog.findViewById(R.id.btnpost);
		final TextView et = (TextView) dialog.findViewById(R.id.twittext);
		et.setText("Love quote app");
		btnPost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					mTwitter.updateStatus(quote.trim()+"\nhttps://play.google.com/store/apps/details?id=com.clarussapps.lovepoem");
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
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}

//package com.clarussapps.lovepoem;


/*import org.json.JSONObject;
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

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FavouriteInnerActivity extends Activity{
	Button btnBack,btnSMS,btnShare;
	TextView textQuote;
	String quote;
	int quote_id;
	Typeface tf;
	
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
		setContentView(R.layout.activity_favinner);
		
		btnBack = (Button)findViewById(R.id.favinner_btn_back);
		textQuote = (TextView) findViewById(R.id.favinner_tv_quote);
		btnSMS = (Button)findViewById(R.id.favinner_btn_sendSMS);
		btnShare = (Button)findViewById(R.id.favinner_btn_shar);
		try {
			quote=getIntent().getExtras().getString(FavoriteActivity.INTENT_STRING_QUOTE);
			quote_id=getIntent().getExtras().getInt(FavoriteActivity.INTENT_STRING_QUOTE_ID);
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(FavouriteInnerActivity.this, "Quote not found.",Toast.LENGTH_SHORT).show();
			finish();
		}
		
		tf = Typeface.createFromAsset(getBaseContext().getAssets(),
				"Gabriola.ttf");
		
		textQuote.setTypeface(tf);
		textQuote.setTextSize(25);
		textQuote.setText(quote);
		
		mTwitter = new TwitterApp(FavouriteInnerActivity.this, twitter_consumer_key,twitter_secret_key);
		mProgress       = new ProgressDialog(this);
		mFacebook       = new Facebook(APP_ID);
		mPermissions = new String[] {"publish_stream", "read_stream"};
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		btnSMS.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(android.content.Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				// Add data to the intent, the receiving app will decide what to do with it.
				intent.putExtra(Intent.EXTRA_TEXT, quote);
				startActivity(Intent.createChooser(intent, "Share Quote"));
				
			}
		});
		btnShare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(FavouriteInnerActivity.this);
				dialog.setContentView(R.layout.choose_layout);
				dialog.setTitle("Share");
				dialog.setCancelable(true);

				//Buttons on dialog
				ImageButton fbButton = (ImageButton) dialog.findViewById(R.id.btn_facebook);
				ImageButton tweetButton = (ImageButton) dialog.findViewById(R.id.btn_tweet);

				//Click event for FB button on dialog
				fbButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						SessionStore.restore(mFacebook, FavouriteInnerActivity.this);   

						onFacebookClick();
						dialog.dismiss();

					}
				});

				//Click event for Tweet button on dialog
				tweetButton.setOnClickListener(new OnClickListener() {
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

				//Show dialog
				dialog.show();
			}
				
		});
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.admoblayout);
		AdView adView = new AdView(this, AdSize.BANNER, MainActivity.MY_BANNER_UNIT_ID);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		//request.setTesting(true);
		adView.loadAd(request);
	}
	private void onFacebookClick() {
		mFacebook.authorize(this, mPermissions, -1, new FbLoginDialogListener());
	}

	private final class FbLoginDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			SessionStore.save(mFacebook, FavouriteInnerActivity.this);

			getFbName();
			postToFacebook(quote.trim());
		}

		@Override
		public void onFacebookError(FacebookError error) {
			Toast.makeText(FavouriteInnerActivity.this, "Facebook connection failed", Toast.LENGTH_SHORT).show();

			// mFacebookBtn.setChecked(false);
		}

		@Override
		public void onError(DialogError error) {
			Toast.makeText(FavouriteInnerActivity.this, "Facebook connection failed", Toast.LENGTH_SHORT).show();

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
				SessionStore.clear(FavouriteInnerActivity.this);

				int what = 1;

				try {
					mFacebook.logout(FavouriteInnerActivity.this);

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

				SessionStore.saveName(username, FavouriteInnerActivity.this);

				//    mFacebookBtn.setText("  Facebook (" + username + ")");

				Toast.makeText(FavouriteInnerActivity.this, "Connected to Facebook as " + username, Toast.LENGTH_SHORT).show();


			} else {
				Toast.makeText(FavouriteInnerActivity.this, "Connected to Facebook", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Handler mmHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 1) {
				Toast.makeText(FavouriteInnerActivity.this, "Facebook logout failed", Toast.LENGTH_SHORT).show();
			} else {
				//mFacebookBtn.setChecked(false);
				//  mFacebookBtn.setText("  Facebook (Not connected)");
				//  mFacebookBtn.setTextColor(Color.GRAY);

				Toast.makeText(FavouriteInnerActivity.this, "Disconnected from Facebook", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private void postToFacebook(String review) {
		mProgress.setMessage("Posting ...");
		mProgress.show();

		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);

		Bundle params = new Bundle();
		params.putString("message", review);
		params.putString("name", "Love SMS,Poem and story");
		params.putString("caption", "Love SMS, Poem and story");
		params.putString("link", "http://www.amazon.com/gp/mas/dl/android/com.clarussapps.lovesms");
		params.putString("description","Expression of love is very imperative, and of course, you need nothing less than perfect for the occasion. What about heart-touching and mesmerizing love quotes that can steal anyone’s heart easily. Claruss App has recently introduced an app that flaunts impressive quotes on love and can penetrate in deep in anyone’s heart.  You can send your girlfriend, wife or anybody else you love amazing love quotes or love story from this app, and let them know how much you love them."); 
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

					Toast.makeText(FavouriteInnerActivity.this, "Posted to Facebook", Toast.LENGTH_SHORT).show();
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
		final Dialog dialog = new Dialog(FavouriteInnerActivity.this);
		dialog.setContentView(R.layout.dialog);

		Button btnPost = (Button) dialog.findViewById(R.id.btnpost);
		final TextView et = (TextView) dialog.findViewById(R.id.twittext);
		et.setText("Love quote app");
		btnPost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					mTwitter.updateStatus(quote.trim()+"\nhttp://www.amazon.com/gp/mas/dl/android/com.clarussapps.lovesms");
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
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}
*/