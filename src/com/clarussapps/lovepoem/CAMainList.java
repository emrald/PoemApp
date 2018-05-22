package com.clarussapps.lovepoem;

import java.util.ArrayList;

import com.clarussapps.lovepoem.Quote;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CAMainList extends ArrayAdapter<Quote>{
	public LayoutInflater inflater;
	public CAMainList(Context context, int textViewResourceId,
			ArrayList<Quote> objects) {
		super(context, textViewResourceId, objects);
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	public static class ViewHolder
	{	
		TextView txtViewQuote;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		if(convertView==null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.row_mainlist, null);
			
			holder.txtViewQuote= (TextView) convertView.findViewById(R.id.row_tv);
			
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder)convertView.getTag();
		}
		Quote quote=getItem(position);
		holder.txtViewQuote.setText(Html.fromHtml(quote.getQuote()));
	//	holder.txtViewQuote.setText(quote.getQuote());
		
		return convertView;
	}
}
