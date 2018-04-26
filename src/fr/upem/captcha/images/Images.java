package fr.upem.captcha.images;

import java.net.URL;
import java.util.List;
import java.util.Map;

public interface Images {
	public List<Image> getPhotos();
	public List<Image> getRandomPhotos();
	public Image getRandomPhoto();
	public boolean isPhotoCorrect(ImagesCollection imgCollection);
}
