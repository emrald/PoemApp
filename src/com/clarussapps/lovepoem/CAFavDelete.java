package com.clarussapps.lovepoem;

import java.util.ArrayList;

import com.clarussapps.lovepoem.Quote;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class CAFavDelete extends ArrayAdapter<Quote>{
	public LayoutInflater inflater;
	ArrayList<Quote> arrQuote;
	boolean DeleteBtnStatus=false;
	DatabaseHelper db;
	public CAFavDelete(Context context, int textViewResourceId,ArrayList<Quote> arrayQuote) {
		super(context, textViewResourceId,arrayQuote);
		this.arrQuote=arrayQuote;
		this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		db = MyApplication.getDataBase();
	}
	
	public static class ViewHolder
	{	
		TextView txtViewQuote;
		ImageButton iBtnLeftDelete;
		Button btnDelete;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		if(convertView==null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.row_del_fav, null);
			holder.iBtnLeftDelete = (ImageButton)convertView.findViewById(R.id.row_del_del_left);
			holder.btnDelete = (Button)convertView.findViewById(R.id.row_del_btn_delete);
			holder.txtViewQuote= (TextView) convertView.findViewById(R.id.row_tv);
			
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder)convertView.getTag();
		}
		final Quote quote=getItem(position);
		//holder.txtViewQuote.setText(quote.getQuote());
		holder.txtViewQuote.setText(Html.fromHtml(quote.getQuote()));
		
		holder.iBtnLeftDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(DeleteBtnStatus){
					DeleteBtnStatus=false;
					holder.btnDelete.setVisibility(View.GONE);
					holder.iBtnLeftDelete.setImageResource(R.drawable.delete);
				}
				else{
					DeleteBtnStatus=true;
					holder.btnDelete.setVisibility(View.VISIBLE);
					holder.iBtnLeftDelete.setImageResource(R.drawable.delete2);
				}
			}
		});
		holder.btnDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.removeQuoteFromFav(quote.getQuoteId());
				remove(quote);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
}
