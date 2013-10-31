package com.pk.androidsdk.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.pk.androidsdk.R;
import com.pk.androidsdk.adapter.DrawerAdapter;
import com.pk.androidsdk.fragment.FragmentDisplay;
import com.pk.androidsdk.fragment.FragmentFetch;
import com.pk.androidsdk.model.FilterItem;

public class ActivityMain extends FragmentActivity
{
	// Action Bar
	ActionBar actionBar;
	
	// Views
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ImageView imgRefresh;

	// Section Constants
	private int currentSection;
	private final int SECTION_FETCH = 0;
	private final int SECTION_DISPLAY = 1;
	
	// Fragments
	FragmentManager fragmentManager;
	FragmentFetch fragFetch;
	FragmentDisplay fragDisplay;
	
	// Navigation Drawer
	private ActionBarDrawerToggle mDrawerToggle;
	private List<FilterItem> drawerList;
	private DrawerAdapter drawerAdapter;
	
	// TODO Remove this variable when using pull to refresh
	private boolean isRefreshing;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getActionBar();
		initViews();
		initNavigationDrawer();
		isRefreshing = false;
		
		fragmentManager = getSupportFragmentManager();
		fragFetch = new FragmentFetch();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.contentFragment, fragFetch);
		transaction.commit();
		currentSection = SECTION_FETCH;
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_filter:
				// Toggle Filter Drawer
				if(mDrawerLayout.isDrawerOpen(mDrawerList))
					mDrawerLayout.closeDrawer(mDrawerList);
				else
					mDrawerLayout.openDrawer(mDrawerList);
				
				return true;
			case R.id.action_settings:
				Intent settingsIntent = new Intent(ActivityMain.this, ActivitySettings.class);
				startActivity(settingsIntent);
				overridePendingTransition(R.anim.fslide_right_in, R.anim.fslide_left_out);
				return true;
			default:
				return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
		}
	}
	
	private void initViews()
	{
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.filter_drawer);
	}
	
	private void initNavigationDrawer()
	{
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer_indicator, R.string.drawer_open, R.string.drawer_close)
		{
			public void onDrawerClosed(View view)
			{
				actionBar.setTitle(getResources().getString(R.string.app_name));
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View drawerView)
			{
				actionBar.setTitle(getResources().getString(R.string.filter_packages));
				invalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		LayoutInflater inflater = getLayoutInflater();
		View header = inflater.inflate(R.layout.filter_drawer_header, mDrawerList, false);
		imgRefresh = (ImageView) header.findViewById(R.id.imgRefresh);
		mDrawerList.addHeaderView(header, null, true);
		
		Resources res = getResources();
		drawerList = new ArrayList<FilterItem>();
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_HEADER, res.getString(R.string.show)));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_CHECKBOX, res.getString(R.string.updates_new), true));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_CHECKBOX, res.getString(R.string.installed), true));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_CHECKBOX, res.getString(R.string.obsolete), false));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_HEADER, res.getString(R.string.sort)));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_RADIO, res.getString(R.string.api), true));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_RADIO, res.getString(R.string.repository), false));
		
		drawerAdapter = new DrawerAdapter(ActivityMain.this, drawerList);
		mDrawerList.setAdapter(drawerAdapter);
		mDrawerList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long index)
			{
				Toast.makeText(getApplicationContext(), "Selected : " + position, Toast.LENGTH_SHORT).show();
				if(position == 0)
				{
					if(currentSection == SECTION_DISPLAY)
					{
						if(!isRefreshing)
						{
							Animation rotation = AnimationUtils.loadAnimation(ActivityMain.this, R.anim.clockwise_refresh);
						    rotation.setRepeatCount(Animation.INFINITE);
						    imgRefresh.startAnimation(rotation);
						}
						else
							imgRefresh.clearAnimation();
					}
					else
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.fetch_first), Toast.LENGTH_LONG).show();
				}
				else
					handleFilterSelection(position);
			}
		});
	}
	
	private void handleFilterSelection(int position)
	{
		FilterItem filter = drawerList.get(position);
		
		switch(filter.getType())
		{
			case DrawerAdapter.TYPE_BUTTON:
				if(currentSection == SECTION_DISPLAY)
				{
					
				}
				else
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.fetch_first), Toast.LENGTH_LONG).show();
				
				break;
			case DrawerAdapter.TYPE_CHECKBOX:
				drawerList.remove(position);
				drawerList.add(position, new FilterItem(filter.getType(), filter.getTitle(), !filter.getValue()));
				drawerAdapter.notifyDataSetChanged();
				
				break;
			case DrawerAdapter.TYPE_RADIO:
				if(!filter.getValue())
				{
					drawerList.remove(position);
					drawerList.add(position, new FilterItem(filter.getType(), filter.getTitle(), !filter.getValue()));
					drawerAdapter.notifyDataSetChanged();
					
					if(drawerList.get(position - 1).getType() == DrawerAdapter.TYPE_RADIO)
					{
						FilterItem f = drawerList.get(position - 1);
						drawerList.remove(position - 1);
						drawerList.add(position - 1, new FilterItem(filter.getType(), f.getTitle(), !f.getValue()));
						drawerAdapter.notifyDataSetChanged();
					}
					else
					{
						FilterItem f = drawerList.get(position + 1);
						drawerList.remove(position + 1);
						drawerList.add(position + 1, new FilterItem(filter.getType(), f.getTitle(), !f.getValue()));
						drawerAdapter.notifyDataSetChanged();
					}
				}
				
				break;
		}
	}
}