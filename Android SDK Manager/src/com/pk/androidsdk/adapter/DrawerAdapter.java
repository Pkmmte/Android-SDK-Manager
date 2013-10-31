package com.pk.androidsdk.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pk.androidsdk.R;
import com.pk.androidsdk.model.FilterItem;

public class DrawerAdapter extends BaseAdapter
{
	// Essential resources
	private Context context;
	private List<FilterItem> listItem;
	private Resources res;
	
	// Flag Constants
	public static final int TYPE_HEADER = 0;
	public static final int TYPE_BUTTON = 1;
	public static final int TYPE_CHECKBOX = 2;
	public static final int TYPE_RADIO = 3;
	
	public DrawerAdapter(Context context, List<FilterItem> listItem)
	{
		this.context = context;
		this.listItem = listItem;
		this.res = context.getResources();
	}
	
	public int getCount()
	{
		return listItem.size();
	}
	
	public FilterItem getItem(int position)
	{
		return listItem.get(position);
	}
	
	public long getItemId(int position)
	{
		return position;
	}
	
	public boolean isEnabled(int position)
	{
		if(listItem.get(position).getType() == TYPE_HEADER)
			return false;
		else
			return true;
	}
	
	public View getView(int position, View convertView, ViewGroup viewGroup)
	{
		ViewHolder holder;
		if (convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.filter_drawer_row, null);
			
			holder = new ViewHolder();
			holder.txtHeader = (TextView) convertView.findViewById(R.id.txtHeader);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			holder.divider = convertView.findViewById(R.id.divider);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		FilterItem entry = listItem.get(position);
		switch (entry.getType())
		{
			case TYPE_HEADER:
				// Set visibility
				holder.txtHeader.setVisibility(View.VISIBLE);
				holder.txtTitle.setVisibility(View.GONE);
				holder.imgIcon.setVisibility(View.GONE);
				holder.divider.setVisibility(View.VISIBLE);
				
				holder.txtHeader.setText(entry.getTitle());
				holder.divider.setBackgroundColor(context.getResources().getColor(R.color.holo_blue_light));
				holder.divider.getLayoutParams().height = 2 * (int)(context.getResources().getDisplayMetrics().densityDpi / 160f);
				
				break;
			case TYPE_BUTTON:
				// Set visibility
				holder.txtHeader.setVisibility(View.GONE);
				holder.txtTitle.setVisibility(View.VISIBLE);
				holder.imgIcon.setVisibility(View.VISIBLE);
				holder.divider.setVisibility(View.GONE);
				
				holder.txtTitle.setText(entry.getTitle());
				
				break;
			case TYPE_CHECKBOX:
				// Set visibility
				holder.txtHeader.setVisibility(View.GONE);
				holder.txtTitle.setVisibility(View.VISIBLE);
				holder.imgIcon.setVisibility(View.VISIBLE);
				
				holder.txtTitle.setText(entry.getTitle());
				holder.imgIcon.setImageResource(entry.getValue() ? R.drawable.btn_check_on : R.drawable.btn_check_off);
				holder.divider.setVisibility(entry.getTitle().equals(res.getString(R.string.obsolete)) ? View.GONE : View.VISIBLE);
				
				break;
			case TYPE_RADIO:
				// Set visibility
				holder.txtHeader.setVisibility(View.GONE);
				holder.txtTitle.setVisibility(View.VISIBLE);
				holder.imgIcon.setVisibility(View.VISIBLE);

				holder.txtTitle.setText(entry.getTitle());
				holder.imgIcon.setImageResource(entry.getValue() ? R.drawable.btn_radio_on : R.drawable.btn_radio_off);
				holder.divider.setVisibility(entry.getTitle().equals(res.getString(R.string.repository)) ? View.GONE : View.VISIBLE);
				
				break;
			default:
				break;
		}
		
		return convertView;
	}
	
	private class ViewHolder
	{
		// Header
		public TextView txtHeader;
		public ImageView imgIcon;
		public TextView txtTitle;
		public View divider;
	}
}
