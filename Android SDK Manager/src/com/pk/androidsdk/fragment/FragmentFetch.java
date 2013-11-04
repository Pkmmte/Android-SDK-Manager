package com.pk.androidsdk.fragment;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pk.androidsdk.R;
import com.pk.androidsdk.model.SDKArchive;
import com.pk.androidsdk.model.SDKPackage;

public class FragmentFetch extends Fragment
{
	private String LOG = "FragmentFetch";
	
	// Views
	private Button btnFetch;
	private TextView txtResult;
	
	//
	private List<SDKPackage> packageList;
	private Map<String, String> licenses;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_fetch, container, false);
		
		initViews(view);
		btnFetch.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new FetchAsyncTask(getActivity()).execute("https://dl-ssl.google.com/android/repository/");
			}
		});
		
		return view;
	}
	
	private void initViews(View v)
	{
		btnFetch = (Button) v.findViewById(R.id.btnFetch);
		txtResult = (TextView) v.findViewById(R.id.txtResult);
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
			String[] manifests = { "addon.xml", "repository-8.xml" };
			licenses = new HashMap<String, String>();
			
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
						
						SDKPackage mPackage = null;
						result += "Fetched " + manifest + "\n";
						NodeList nodes = doc.getElementsByTagName("*");
						for (int i = 0; i < nodes.getLength(); i++)
						{
							Node child = nodes.item(i);
							
							if (child.getNodeName().equals("sdk:add-on") || child.getNodeName().equals("sdk:platform") || child.getNodeName().equals("sdk:extra"))
							{
								//mList.add(new SDKPackage(child, mList, getActivity()));
								child = child.getFirstChild();
								while(child != null)
								{
									if (child.getNodeName().equals("sdk:archives"))
									{
										mPackage = new SDKPackage();
										Node archiveChild = child.getFirstChild();
										List<SDKArchive> archives = new ArrayList<SDKArchive>();
										while (archiveChild != null)
										{
											if (archiveChild.getNodeName().equals("sdk:archive"))
											{
												SDKArchive archive = new SDKArchive();
												NamedNodeMap attrs = archiveChild.getAttributes();

								                Node archNode = attrs.getNamedItem("arch");
								                if (archNode != null)
								                        archive.setArchitecture(archNode.getTextContent());

								                Node osNode = attrs.getNamedItem("os");
								                if (osNode != null)
								                        archive.setOperatingSystem(osNode.getTextContent());

								                Node archChild = archiveChild.getFirstChild();
								                while (archChild != null)
								                {
								                        if (child.getNodeName().equals("sdk:url"))
								                        	archive.setURL(archChild.getTextContent());
								                        else if (child.getNodeName().equals("sdk:checksum"))
								                        	archive.setChecksum(archChild.getTextContent());
								                        else if (child.getNodeName().equals("sdk:size"))
								                        	archive.setSize(Integer.parseInt(archChild.getTextContent()));

										                Node checksumTypeNode = attrs.getNamedItem("type");
										                if (checksumTypeNode != null)
										                        archive.setChecksumType(checksumTypeNode.getTextContent());
								                        archChild = child.getNextSibling();
								                }
								                
								                // Add archive to list
												archives.add(archive);
											}
											archiveChild = archiveChild.getNextSibling();
										}
										packageList.add(mPackage);
			                        }
									
									Log.v(LOG, "Node Name: " + child.getNodeName());
									mPackage = null;
									child = child.getNextSibling();
								}
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
			txtResult.setText(result);
		}
	}
}