package com.pk.androidsdk.fragment;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pk.androidsdk.R;
import com.pk.androidsdk.model.SDKPackage;

public class FragmentFetch extends Fragment
{
	// Views
	private Button btnFetch;
	
	//
	private List<SDKPackage> packageList;
	private Map<String, String> licenses;
	
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
	
	private class FetchAsyncTask extends AsyncTask<String, Void, String>
	{
		private Context context;
		private List<SDKPackage> mList;
		
		public FetchAsyncTask(Context context)
		{
			this.context = context;
			this.mList = null;
		}
		
		@Override
		protected String doInBackground(String... urls)
		{
			String result = "";
			String[] manifests = { "addon.xml", "repository-7.xml" };
			
			for (String manifest : manifests)
			{
				try
				{
					URL url = new URL(urls[0] + manifest);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					try
					{
						InputStream in = new BufferedInputStream(conn.getInputStream());
						DocumentBuilderFactory bfactory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = bfactory.newDocumentBuilder();
						Document doc = builder.parse(in);
						
						result += "Fetched " + manifest + "\n";
						NodeList nodes = doc.getElementsByTagName("*");
						for (int i = 0; i < nodes.getLength(); i++)
						{
							Node child = nodes.item(i);
							
							if (child.getNodeName().equals("sdk:add-on") || child.getNodeName().equals("sdk:platform") || child.getNodeName().equals("sdk:extra"))
							{
								mList.add(new SDKPackage(child, mList, getActivity()));
							}
							else if (child.getNodeName().equals("sdk:license"))
							{
								NamedNodeMap attrs = child.getAttributes();
								if (attrs.getNamedItem("id") != null)
									licenses.put(attrs.getNamedItem("id").getTextContent(), child.getTextContent());
							}
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						result += "Exception " + e.toString() + " on " + manifest + "\n";
					}
					finally
					{
						conn.disconnect();
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
					result += "Exception " + e.toString() + " on " + manifest + "\n";
				}
			}
			
			return result;
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			
		}
	}
}