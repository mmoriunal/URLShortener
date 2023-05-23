package com.shortener.URLShortener;

public class ShortUrl{
    
	private String full_url, short_url;

    // GET url
	public String getFull(){ return this.full_url; }
	public String getShort() { return this.short_url; }

    // SET url
	public void setFull(String full_url) { this.full_url = full_url; }
	public void setShort(String short_url) { this.short_url = short_url; }
}
