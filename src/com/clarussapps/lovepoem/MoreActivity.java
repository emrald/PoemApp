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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoreActivity extends Activity{
	Button btn_rateApp,btn_twitter,btn_mail,btn_facebook;
	ImageView iv_rateApp,iv_twitter,iv_mail,iv_facebook;
	
	ImageView ivSpiderQuote,ivVivekanand,ivFlashLight,ivFileManager,ivTaskManager,ivLoveLiveWallpaper,ivChanakya,ivAristotle,ivBuddha,ivAbraham,ivGandhi,ivGita,ivJesus;
	Button btnSpiderQuote,btnVivekanand,btnFlashLight,btnFileManager,btnTaskManager,btnLoveLiveWallpaper,btnChanakya,btnAristotle,btnBuddha,btnAbraham,btnGandhi,btnGita,btnJesus;
	
	private TwitterApp mTwitter;
	private static final String twitter_consumer_key = "8Q7tu5OoUnKxwEJPT3g";// "cNjYnzVzmyPe3ToNn8lSrQ";
	private static final String twitter_secret_key = "YdrD8VJjOfNT5wUvSX4ewZuwOotGsx7fNtx46xyk";// "qgzVsQdxSZQ1mzKkDZD0WlLxTqYkFOshAUqq9Pblw";
	//Facebook
	public static final String APP_ID = "456328747744179";
	private Facebook mFacebook;
	private String[] mPermissions;
	private ProgressDialog mProgress;
	private Handler mRunOnUi = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		
		btn_rateApp=(Button)findViewById(R.id.more_btn_rateapp);
        btn_twitter=(Button)findViewById(R.id.more_btn_btntwitter);
        btn_mail=(Button)findViewById(R.id.more_btn_btnmail);
		btn_facebook=(Button)findViewById(R.id.more_btn_btnfacebook);
		iv_rateApp=(ImageView)findViewById(R.id.more_iv_rate_this_app);
        iv_twitter=(ImageView)findViewById(R.id.more_iv_twitter);
        iv_mail=(ImageView)findViewById(R.id.more_iv_mail);
		iv_facebook=(ImageView)findViewById(R.id.more_iv_facebook);
		
		
		ivSpiderQuote = (ImageView)findViewById(R.id.more_iv_spiderquote);
		ivVivekanand = (ImageView)findViewById(R.id.more_iv_vivekanand);
		ivFlashLight = (ImageView)findViewById(R.id.more_iv_flashlight);
		ivFileManager = (ImageView)findViewById(R.id.more_iv_filemanager);
		ivTaskManager = (ImageView)findViewById(R.id.more_iv_taskmanager);
		ivLoveLiveWallpaper = (ImageView)findViewById(R.id.more_iv_lovelivewallpaper);
		ivChanakya = (ImageView)findViewById(R.id.more_iv_chanakya);
		ivAristotle = (ImageView)findViewById(R.id.more_iv_aristotle);
		ivBuddha = (ImageView)findViewById(R.id.more_iv_buddha);
		ivAbraham = (ImageView)findViewById(R.id.more_iv_abrahamlincoln);
		ivGandhi = (ImageView)findViewById(R.id.more_iv_gandhi);
		ivGita = (ImageView)findViewById(R.id.more_iv_bhagavadgita);
		ivJesus = (ImageView)findViewById(R.id.more_iv_jesuschrist);
		
		btnSpiderQuote = (Button)findViewById(R.id.more_btn_spiderquote);
		btnVivekanand = (Button)findViewById(R.id.more_btn_vivekanand);
		btnFlashLight = (Button)findViewById(R.id.more_btn_flashlight);
		btnFileManager = (Button)findViewById(R.id.more_btn_filemanager);
		btnTaskManager = (Button)findViewById(R.id.more_btn_taskmanager);
		btnLoveLiveWallpaper = (Button)findViewById(R.id.more_btn_lovelivewallpaper);
		btnChanakya = (Button)findViewById(R.id.more_btn_chanakya);
		btnAristotle = (Button)findViewById(R.id.more_btn_aristotle);
		btnBuddha = (Button)findViewById(R.id.more_btn_buddha);
		btnAbraham = (Button)findViewById(R.id.more_btn_abrahamlincoln);
		btnGandhi = (Button)findViewById(R.id.more_btn_gandhi);
		btnGita = (Button)findViewById(R.id.more_btn_bhagavadgita);
		btnJesus = (Button)findViewById(R.id.more_btn_jesuschrist);
		
		
		btnSpiderQuote.setOnClickListener(spiderQuoteAppClickListner);
		btnVivekanand.setOnClickListener(vivekanandAppClickListner);
		btnFlashLight.setOnClickListener(flashLightAppClickListner);
		btnFileManager.setOnClickListener(filemanagerAppClickListner);
		btnTaskManager.setOnClickListener(taskmanagerAppClickListner);
		btnLoveLiveWallpaper.setOnClickListener(loveLiveWallpaperAppClickListner);
		btnChanakya.setOnClickListener(chanakyaAppClickListner);
		btnAristotle.setOnClickListener(aristotleAppClickListner);
		btnBuddha.setOnClickListener(buddhaAppClickListner);
		btnAbraham.setOnClickListener(abrahamAppClickListner);
		btnGandhi.setOnClickListener(gandhiAppClickListner);
		btnGita.setOnClickListener(bhagvadgitaAppClickListner);
		btnJesus.setOnClickListener(jesusAppClickListner);
		
		ivSpiderQuote.setOnClickListener(spiderQuoteAppClickListner);
		ivVivekanand.setOnClickListener(vivekanandAppClickListner);
		ivFlashLight.setOnClickListener(flashLightAppClickListner);
		ivFileManager.setOnClickListener(filemanagerAppClickListner);
		ivTaskManager.setOnClickListener(taskmanagerAppClickListner);
		ivLoveLiveWallpaper.setOnClickListener(loveLiveWallpaperAppClickListner);
		ivChanakya.setOnClickListener(chanakyaAppClickListner);
		ivAristotle.setOnClickListener(aristotleAppClickListner);
		ivBuddha.setOnClickListener(buddhaAppClickListner);
		ivAbraham.setOnClickListener(abrahamAppClickListner);
		ivGandhi.setOnClickListener(gandhiAppClickListner);
		ivGita.setOnClickListener(bhagvadgitaAppClickListner);
		ivJesus.setOnClickListener(jesusAppClickListner);
		
		mTwitter = new TwitterApp(this, twitter_consumer_key,
				twitter_secret_key);
		mProgress       = new ProgressDialog(this);
		mFacebook       = new Facebook(APP_ID);
		mPermissions = new String[] {"publish_stream", "read_stream"};

		btn_mail.setOnClickListener(mailClickListner);
		btn_rateApp.setOnClickListener(rateAppClickListner);
		btn_twitter.setOnClickListener(twitterClickListner);
		btn_facebook.setOnClickListener(facebookClickListner);
		iv_facebook.setOnClickListener(facebookClickListner);
		iv_mail.setOnClickListener(mailClickListner);
		iv_rateApp.setOnClickListener(rateAppClickListner);
		iv_twitter.setOnClickListener(twitterClickListner);
		
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.admoblayout);
		AdView adView = new AdView(this, AdSize.BANNER, MainActivity.MY_BANNER_UNIT_ID);
		layout.addView(adView);
		AdRequest request = new AdRequest();
		//request.setTesting(true);
		adView.loadAd(request);
	}
	private OnClickListener facebookClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			SessionStore.restore(mFacebook, MoreActivity.this);   

			onFacebookClick();
		}
	};
	private OnClickListener twitterClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mTwitter.setListener(mTwLoginDialogListener);
			if (mTwitter.hasAccessToken()) {
				showTwittDialog();
			} else {
				mTwitter.authorize();
			}
		}
	};
	private OnClickListener mailClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			final Intent emailIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			emailIntent.setType("plain/text");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Love Poem android app");
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
					"This is Love Poem android app download from this URL.\nhttps://play.google.com/store/apps/details?id=com.clarussapps.lovepoem");
			MoreActivity.this.startActivity(Intent.createChooser(
					emailIntent, "Send mail..."));
		}
	};
	private OnClickListener rateAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarussapps.lovepoem"));
			startActivity(browserIntent);
		}
	};
	private TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

		public void onError(String value) {
			showToast("Login Failed");
			mTwitter.resetAccessToken();
		}

		public void onComplete(String value) {
			showTwittDialog();
		}
	};
	private OnClickListener spiderQuoteAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarus.spiderquotes"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener vivekanandAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.SwamiVivekananda"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener flashLightAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.flashlightapp"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener filemanagerAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarus.fillemanager"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener taskmanagerAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.taskmanagerfree"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener loveLiveWallpaperAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.valentinelivewallpaperpink"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener chanakyaAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.chanakya"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener aristotleAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.aristotle"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener buddhaAppClickListner = new OnClickListener() {	
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.gautambuddha"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener abrahamAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.abrahamlincon"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener gandhiAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.Gandhiji"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener bhagvadgitaAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.bhagavadgita"));
			startActivity(browserIntent);
		}
	};
	private OnClickListener jesusAppClickListner = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.clarusapps.jesuschristlite"));
			startActivity(browserIntent);
		}
	};
	private void showTwittDialog() {
		final Dialog dialog = new Dialog(MoreActivity.this);
		dialog.setContentView(R.layout.dialog);

		Button btnPost = (Button) dialog.findViewById(R.id.btnpost);
		final TextView et = (TextView) dialog.findViewById(R.id.twittext);
		et.setText("Love Poem app");
		btnPost.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					mTwitter.updateStatus("https://play.google.com/store/apps/details?id=com.clarussapps.lovepoem");
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
	private void onFacebookClick() {
		mFacebook.authorize(this, mPermissions, -1, new FbLoginDialogListener());
	}

	private final class FbLoginDialogListener implements DialogListener {
		@Override
		public void onComplete(Bundle values) {
			SessionStore.save(mFacebook, MoreActivity.this);

			getFbName();
			postToFacebook("");
		}

		@Override
		public void onFacebookError(FacebookError error) {
			Toast.makeText(MoreActivity.this, "Facebook connection failed", Toast.LENGTH_SHORT).show();

			// mFacebookBtn.setChecked(false);
		}

		@Override
		public void onError(DialogError error) {
			Toast.makeText(MoreActivity.this, "Facebook connection failed", Toast.LENGTH_SHORT).show();

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
				SessionStore.clear(MoreActivity.this);

				int what = 1;

				try {
					mFacebook.logout(MoreActivity.this);

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

				SessionStore.saveName(username, MoreActivity.this);

				//    mFacebookBtn.setText("  Facebook (" + username + ")");

				Toast.makeText(MoreActivity.this, "Connected to Facebook as " + username, Toast.LENGTH_SHORT).show();


			} else {
				Toast.makeText(MoreActivity.this, "Connected to Facebook", Toast.LENGTH_SHORT).show();
			}
		}
	};
	private Handler mmHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 1) {
				Toast.makeText(MoreActivity.this, "Facebook logout failed", Toast.LENGTH_SHORT).show();
			} else {
				//mFacebookBtn.setChecked(false);
				//  mFacebookBtn.setText("  Facebook (Not connected)");
				//  mFacebookBtn.setTextColor(Color.GRAY);

				Toast.makeText(MoreActivity.this, "Disconnected from Facebook", Toast.LENGTH_SHORT).show();
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
		params.putString("caption", "Love SMS,Poem and Story");
		params.putString("link", "https://play.google.com/store/apps/details?id=com.clarussapps.lovepoem");

		mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener());
	}
	private final class WallPostListener extends BaseRequestListener {
		@Override
		public void onComplete(final String response) {
			mRunOnUi.post(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();

					Toast.makeText(MoreActivity.this, "Posted to Facebook", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}
