package fr.upem.captcha.images;

import java.io.File;

public class Image {
	final private String url;
	final private String category;

	public Image(String url, String category) {
		super();
		this.url = url;
		this.category = category;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getCategory() {
		return this.category;
	}

	@Override
	public String toString() {
		return "Image [url=" + url + ", category=" + category + "]";
	}
	
	
}
