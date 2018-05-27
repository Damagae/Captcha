/* * * * * * * * * * * * * * * * * *
 * CAPTCHA                         *
 * par Daphné Rose et Flavie Lucas *
 *                                 *
 * Class Image                     *
 * * * * * * * * * * * * * * * * * */

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
	
	/*
	 * renvoie la classe héritée de Category dont est issue l'image
	 */
	public Category getCategoryClass() throws Exception {
		String classPackage = this.url.substring(this.url.lastIndexOf("fr/upem"), this.url.length()).replace("/", "."); // détermine le package
		classPackage = classPackage.substring(0, classPackage.lastIndexOf(".")); 
		classPackage = classPackage.substring(0, classPackage.lastIndexOf(".")); // Enlève les deux derniers mots contenus entre ".", soit l'extension .class et le nom de la classe
		Class<?> clazz = Class.forName(classPackage + "." + this.category.substring(0, 1).toUpperCase() + this.category.substring(1)); // Le dernier mot du package correpond au package parent : on instancie une classe à partir de ce nom
		return (Category) clazz.newInstance();
	}
	
	public String getRelativeUrl() {
		return this.url.substring(this.url.lastIndexOf("/fr/upem"), this.url.length());
	}
	
	public String getCategory() {
		return this.category;
	}

	@Override
	public String toString() {
		return "Image [url=" + url + ", category=" + category + "]";
	}
	
	
}
