package com.clarussapps.lovepoem;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TabMyActivity extends TabActivity{

	static TabHost tabHost;
	private static final String NOTIFICATION_TITLE = "contentTitle";
	private static final String NOTIFICATION_CONTENT = "contentText";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		setTabs();

	}

	private void setTabs()
	{
		addTab("Browse", R.drawable.tab_browse, QuoteGroupActivity.class);
		addTab("Favourite", R.drawable.tab_fav, FavoriteActivity.class);
		addTab("More", R.drawable.tab_more, MoreActivity.class);
	}
	@SuppressLint("NewApi")
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
	//	tabHost.getTabWidget().setDividerDrawable(R.drawable.shape);
		tabHost.addTab(spec);
		//if(Build.VERSION.SDK_INT >= 11)
		  //  tabHost.getTabWidget().setShowDividers(TabWidget.SHOW_DIVIDER_MIDDLE);
	//	tabHost.getTabWidget().setBackgroundColor(Color.WHITE);
		
	}
	
	public static void showNotification(Context context, Bundle msgExtras) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.notification_icon;
		String title = stripHtmlTags(msgExtras.getString(NOTIFICATION_TITLE));
		title = android.text.Html.fromHtml(title).toString();
		String content = stripHtmlTags(msgExtras
				.getString(NOTIFICATION_CONTENT));
		content = android.text.Html.fromHtml(content).toString();

		Notification notification = new Notification(icon, title,
				System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
		notification.sound = Uri.parse("android.resource://" +context.getApplicationContext().getPackageName()+ "/" + R.raw.notification_sound);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
		PendingIntent activity = PendingIntent.getActivity(context, 0, intent,0);
		notification.setLatestEventInfo(context, title, content, activity);
		notification.number += 1;
		notificationManager.notify(0, notification);

	}

	static String stripHtmlTags(String html) {
		return Html.fromHtml(html.replaceAll("\\<[^>]*>", "")).toString();
	}
}
