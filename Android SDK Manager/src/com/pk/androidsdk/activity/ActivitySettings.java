package com.pk.androidsdk.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.pk.androidsdk.R;

public class ActivitySettings extends Activity
{
	ActionBar actionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				Exit();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Exit();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	private void Exit()
	{
		finish();
		overridePendingTransition(R.anim.fslide_left_in, R.anim.fslide_right_out);
	}
}
