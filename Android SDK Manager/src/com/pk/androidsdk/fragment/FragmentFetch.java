package com.pk.androidsdk.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pk.androidsdk.R;

public class FragmentFetch extends Fragment
{
	// Views
	private Button btnFetch;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_fetch, container, false);
		
		initViews(view);
		
		return view;
	}
	
	private void initViews(View v)
	{
		btnFetch = (Button) v.findViewById(R.id.btnFetch);
	}
}