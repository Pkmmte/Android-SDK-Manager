package com.pk.androidsdk.activity;

import java.util.List;

import android.app.ActionBar;
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
import com.pk.androidsdk.fragment.FragmentDisplay;
import com.pk.androidsdk.fragment.FragmentFetch;

public class ActivityMain extends FragmentActivity
{
	// Action Bar
	ActionBar actionBar;
	
	// Fragments
	FragmentManager fragmentManager;
	FragmentFetch fragFetch;
	FragmentDisplay fragDisplay;
	
	// Navigation Drawer
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	//private List<Integer> drawerMenuList;
	//private DrawerFilterAdapter drawerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		actionBar = getActionBar();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.filter_drawer);
		initializeNavigationDrawer();
		
		fragmentManager = getSupportFragmentManager();
		fragFetch = new FragmentFetch();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.contentFragment, fragFetch);
		transaction.commit();
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
				return true;
			default:
				return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
		}
	}
	
	private void initializeNavigationDrawer()
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
	}
}