package fr.upem.captcha.images;

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
	
	public ImagesCollection getICClass() throws Exception {
		String classPackage = this.url.substring(this.url.lastIndexOf("fr/upem"), this.url.length()).replace("/", ".");
		classPackage = classPackage.substring(0, classPackage.lastIndexOf("."));
		classPackage = classPackage.substring(0, classPackage.lastIndexOf("."));
		Class<?> clazz = Class.forName(classPackage + "." + this.category.substring(0, 1).toUpperCase() + this.category.substring(1));
		return (ImagesCollection) clazz.newInstance();
	}
	
	public String getRelativeUrl() {
		return this.url.substring(this.url.lastIndexOf("/fr/upem"), this.url.length());
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public boolean isClass(String clazz) throws Exception {
		System.out.println(this.getCategory() + " versus " + clazz);
		if (this.getCategory().equals(clazz))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "Image [url=" + url + ", category=" + category + "]";
	}
	
	
}
