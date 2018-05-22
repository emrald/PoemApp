package com.clarussapps.lovepoem;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;

public class SplashScreen extends Activity {
	ImageView imgView;
	private static final int SPLASH_DISPLAY_TIME = 3000; 
	private static String SENDERID="648660927131";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent mainIntent = new Intent(SplashScreen.this,
						TabMyActivity.class);
				SplashScreen.this.startActivity(mainIntent);

				SplashScreen.this.finish();				
				 
				overridePendingTransition(R.anim.mainfadein,
						R.anim.splashfadeout);
			}
		}, SPLASH_DISPLAY_TIME);
    }
    /*protected void onStop() {  
     super.onStop();  
     ProgressDialog loadingDlg = new AlertDialog.Builder(this).create();
     if (loadingDlg != null) {  
      loadingDlg.dismiss();  
      loadingDlg = null;  
     }  
    }  */
}
