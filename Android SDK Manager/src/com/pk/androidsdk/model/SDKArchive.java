package com.pk.androidsdk.model;

public class SDKArchive
{
	/** PROPERTIES **/
	private String Architecture;
	private String OS;
	
	private int Size;
	private String URL;
	private String Checksum;
	private String ChecksumType;
	
	/** CONSTRUCTORS **/
	public SDKArchive()
	{
		this.Architecture = "Unknown";
		this.OS = "Unknown";
		this.Size = 0;
		this.URL = "Unknown";
		this.Checksum = "Unknown";
		this.ChecksumType = "Unknown";
	}
	
	public SDKArchive(String Architecture, String OS)
	{
		this.Architecture = Architecture;
		this.OS = OS;
		this.Size = 0;
		this.URL = "Unknown";
		this.Checksum = "Unknown";
		this.ChecksumType = "Unknown";
	}
	
	public SDKArchive(String Architecture, String OS, int Size, String URL, String Checksum, String ChecksumType)
	{
		this.Architecture = Architecture;
		this.OS = OS;
		this.Size = Size;
		this.URL = URL;
		this.Checksum = Checksum;
		this.ChecksumType = ChecksumType;
	}
	
	/** SETTERS **/
	public void setArchitecture(String Architecture)
	{
		this.Architecture = Architecture;
	}
	
	public void setOperatingSystem(String OS)
	{
		this.OS = OS;
	}
	
	public void setSize(int Size)
	{
		this.Size = Size;
	}
	
	public void setURL(String URL)
	{
		this.URL = URL;
	}
	
	public void setChecksum(String Checksum)
	{
		this.Checksum = Checksum;
	}
	
	public void setChecksumType(String ChecksumType)
	{
		this.ChecksumType = ChecksumType;
	}
	
	/** GETTERS **/
	public String getArchitecture()
	{
		return this.Architecture;
	}
	
	public String getOperatingSystem()
	{
		return this.OS;
	}
	
	public int getSize()
	{
		return this.Size;
	}
	
	public String getURL()
	{
		return this.URL;
	}
	
	public String getChecksum()
	{
		return this.Checksum;
	}
	
	public String getChecksumType()
	{
		return this.ChecksumType;
	}
}