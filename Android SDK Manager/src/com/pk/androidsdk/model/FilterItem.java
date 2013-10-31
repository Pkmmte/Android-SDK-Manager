package com.pk.androidsdk.model;

public class FilterItem
{
	/** PROPERTIES **/
	private Integer Type;
	private String Title;
	private Boolean Value;
	
	/** CONSTRUCTORS **/
	public FilterItem()
	{
		this.Type = null;
		this.Title = null;
		this.Value = null;
	}
	
	public FilterItem(int Type, String Title)
	{
		this.Type = Type;
		this.Title = Title;
		this.Value = false;
	}
	
	public FilterItem(int Type, String Title, boolean Value)
	{
		this.Type = Type;
		this.Title = Title;
		this.Value = Value;
	}
	
	/** SETTERS **/
	public void setType(int Type)
	{
		this.Type = Type;
	}
	
	public void setTitle(String Title)
	{
		this.Title = Title;
	}
	
	public void setValue(boolean Value)
	{
		this.Value = Value;
	}
	
	/** GETTERS **/
	public Integer getType()
	{
		return Type;
	}
	
	public String getTitle()
	{
		return Title;
	}
	
	public Boolean getValue()
	{
		return Value;
	}
}