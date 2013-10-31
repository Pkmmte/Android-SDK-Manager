package com.pk.androidsdk.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.pk.androidsdk.R;
import com.pk.androidsdk.adapter.DrawerAdapter;
import com.pk.androidsdk.fragment.FragmentDisplay;
import com.pk.androidsdk.fragment.FragmentFetch;
import com.pk.androidsdk.model.FilterItem;

public class ActivityMain extends FragmentActivity
{
	// Action Bar
	ActionBar actionBar;

	// Section Constants
	private int currentSection;
	private final int SECTION_FETCH = 0;
	private final int SECTION_DISPLAY = 1;
	
	// Fragments
	FragmentManager fragmentManager;
	FragmentFetch fragFetch;
	FragmentDisplay fragDisplay;
	
	// Navigation Drawer
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private List<FilterItem> drawerList;
	private DrawerAdapter drawerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getActionBar();
		initViews();
		initNavigationDrawer();
		
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
		
		drawerList = new ArrayList<FilterItem>();
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_BUTTON, "Refresh", true));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_HEADER, "Show"));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_CHECKBOX, "Updates/New", true));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_CHECKBOX, "Installed", true));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_CHECKBOX, "Obsolete", false));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_HEADER, "Sort"));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_RADIO, "API", true));
		drawerList.add(new FilterItem(DrawerAdapter.TYPE_RADIO, "Repository", false));
		
		drawerAdapter = new DrawerAdapter(ActivityMain.this, drawerList);
		mDrawerList.setAdapter(drawerAdapter);
	}
	
	
}