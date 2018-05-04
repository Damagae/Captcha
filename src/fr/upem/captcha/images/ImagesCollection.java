package fr.upem.captcha.images;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class ImagesCollection implements Images {
	protected List<Image> list;
	protected String category;
	public String categoryUrl;
	
	public ImagesCollection() {
		super();
		list = new ArrayList<Image>();
		String path = Images.class.getResource("Images.class").getPath();
		File dir = new File(path);
		this.categoryUrl = dir.getParent();
		System.out.println(this.categoryUrl);
	}

	public List<Image> getList() {
		return list;
	}

	public String getCategory() {
		return category;
	}

	public String getCategoryUrl() {
		return categoryUrl;
	}

	@Override
	public List<Image> getPhotos() {
		return list;
	}

	@Override
	public List<Image> getRandomPhotos() {
		List<Image> randList = list;
		Collections.shuffle(randList);
		return randList;
	}

	@Override
	public Image getRandomPhoto() {
		return getRandomPhotos().get(0);
	}

	@Override
	public boolean isPhotoCorrect(ImagesCollection imgCollection) {
		if (this.equals(imgCollection)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void addToList(String subCategory, String filepath) {
		Image img;
		if (subCategory != "") {
			img = new Image(filepath, subCategory);
		}
		else {
			img = new Image(filepath, subCategory);
		}
		System.out.println(img.getCategory());
		System.out.println(img.getUrl());
		list.add(img);
	}
	
	public void browsesRepositories() {
        browsesRepositories(new File(this.categoryUrl), "", 0, 10);
    }
	
    public void browsesRepositories(File dir, String subCategory, int depth, int maxDepth) {
        if (depth > maxDepth) {
            return;
        }
        if (dir.isDirectory()) { // Si dir est un dossier
           subCategory = dir.getName();
           File[] subDirs = dir.listFiles(); // On crée un tableau de Files
           for (int i = 0; i < subDirs.length; ++i){  // Pour chaque File
              File subDir = subDirs[i];
              browsesRepositories(subDir, subCategory, depth+1, maxDepth);
           }
        } else if (dir.isFile()) {
        	int i = dir.getAbsolutePath().lastIndexOf('.');
        	if (i > 0) {
        	    String extension = dir.getAbsolutePath().substring(i+1);
        	    if (extension.equals("jpg")) {
        	    	addToList(subCategory, dir.getAbsolutePath());
        	    }
        	}
        	
        }
    }

}
