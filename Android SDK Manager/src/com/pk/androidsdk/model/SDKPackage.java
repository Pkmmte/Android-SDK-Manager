package com.pk.androidsdk.model;

import java.util.ArrayList;
import java.util.List;

public class SDKPackage
{
	/** PROPERTIES **/
	private int TYPE;
	private int API;
	
	private int Revision;
	private int RevisionMajor;
	private int RevisionMinor;
	private int RevisionMicro;
	
	private String License;
	private List<SDKArchive> Archives;
	
	/** CONSTRUCTORS **/
	public SDKPackage()
	{
		this.TYPE = -1;
		this.API = -1;
		this.Revision = -1;
		this.RevisionMajor = -1;
		this.RevisionMinor = -1;
		this.RevisionMicro = -1;
		this.License = "Unknown";
		this.Archives = new ArrayList<SDKArchive>();
	}
	
	/** SETTERS **/
	public void setType(int TYPE)
	{
		this.TYPE = TYPE;
	}
	
	public void setAPI(int API)
	{
		this.API = API;
	}
	
	public void setRevision(int Revision)
	{
		this.Revision = Revision;
	}
	
	public void setRevisionMajor(int RevisionMajor)
	{
		this.RevisionMajor = RevisionMajor;
	}
	
	public void setRevisionMinor(int RevisionMinor)
	{
		this.RevisionMinor = RevisionMinor;
	}
	
	public void setRevisionMicro(int RevisionMicro)
	{
		this.RevisionMicro = RevisionMicro;
	}
	
	public void setLicense(String License)
	{
		this.License = License;
	}
	
	public void setArchives(List<SDKArchive> Archives)
	{
		this.Archives = Archives;
	}
	
	/** GETTERS **/
	public int getType()
	{
		return this.TYPE;
	}
	
	public int getAPI()
	{
		return this.API;
	}
	
	public int getRevision()
	{
		return this.Revision;
	}
	
	public int getRevisionMajor()
	{
		return this.RevisionMajor;
	}
	
	public int getRevisionMinor()
	{
		return this.RevisionMinor;
	}
	
	public int getRevisionMicro()
	{
		return this.RevisionMicro;
	}
	
	public String getLicense()
	{
		return this.License;
	}
	
	public List<SDKArchive> getArchives()
	{
		return this.Archives;
	}
}