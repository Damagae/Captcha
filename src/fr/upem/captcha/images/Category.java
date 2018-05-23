package fr.upem.captcha.images;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.upem.captcha.images.Image;

public abstract class Category implements Images {
	protected List<Image> list;
	public String categoryUrl;
	
	public Category() {
		super();
		list = new ArrayList<Image>();
		String path = Images.class.getResource("Images.class").getPath();
		File dir = new File(path);
		this.categoryUrl = dir.getParent() + toPathFormat(this.getClass().getPackageName());
		browsesRepositories();
	}

	public String toPathFormat(String packageName) {
		packageName = packageName.replace(".", "/");
		packageName =  packageName.substring(packageName.lastIndexOf("images/"), packageName.length());
		packageName = packageName.replace("images/", "/");
		return packageName;
	}

	public List<Image> getList() {
		return list;
	}

	public String getCategory() {
		return this.getClass().getSimpleName().toLowerCase();
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
	public boolean isPhotoCorrect(Category imgCollection) {
		if (this.categoryUrl.contains(imgCollection.getCategory())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<Image> select(int nbr) throws Exception {
		List<Image> list = new ArrayList<Image>();
		List<Image> listRand = getRandomPhotos();
		if (!listRand.isEmpty()) {
			for (int i = 0; i < nbr; ++i) {
				list.add(listRand.get(i));
			}
		}
		return list;
	}
	
	public void addToList(String subCategory, String filepath) {
		Image img;
		if (subCategory != "") {
			img = new Image(filepath, subCategory);
		}
		else {
			img = new Image(filepath, subCategory);
		}
		list.add(img);
	}
	
	public static List<Image> selectRandom(int nbr, List<Category> list, Category rightCategory, int level) throws ClassNotFoundException {
		List<Image> allPhotos = new ArrayList<Image>();
		List<Image> selectedPhotos = new ArrayList<Image>();
		for (int i = 0; i < list.size(); ++i) {
			System.out.println(level);
			if (list.get(i).getClass() != rightCategory.getClass()
				&& list.get(i).getClass().getPackageName().contains(rightCategory.getNParentClassName(5 - level)))
			{
				allPhotos.addAll(list.get(i).getPhotos());
			}
		}
		if (!allPhotos.isEmpty()) {
			Collections.shuffle(allPhotos);
			for (int j = 0; j < nbr; ++j) {
				selectedPhotos.add(allPhotos.get(j));
			}
		}
		return selectedPhotos;
	}
	
	private String getNParentClassName(int N) throws ClassNotFoundException {
		System.out.println(N);
		String pack = this.getClass().getPackageName();
		System.out.println(pack);
		for (int i = 0; i < N; ++i) {
			System.out.println("Hola");
			pack = pack.substring(0, pack.lastIndexOf("."));
		}
		pack = pack.substring(pack.lastIndexOf("."), pack.length());
		pack = pack.replace(".", "");
		System.out.println(pack);
		return pack;
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
        	    if (extension.equals("jpg") && matchesCategory(dir.getAbsolutePath())) {
        	    	addToList(subCategory, dir.getAbsolutePath());
        	    }
        	}
        	
        }
    }
    
    public boolean matchesCategory(String path) {
    	return path.contains(this.getClass().getSimpleName().toLowerCase());
    }

}
