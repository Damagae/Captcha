/* * * * * * * * * * * * * * * * * *
 * CAPTCHA                         *
 * par Daphné Rose et Flavie Lucas *
 *                                 *
 * Class Category                  *
 * * * * * * * * * * * * * * * * * */

package fr.upem.captcha.images;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.upem.captcha.images.Image;

public abstract class Category implements Images {
	private List<Image> list;
	private String categoryUrl;
	
	public Category() {
		super();
		list = new ArrayList<Image>();
		String path = Images.class.getResource("Images.class").getPath();
		File dir = new File(path);
		this.categoryUrl = dir.getParent() + toPathFormat(this.getClass().getPackageName());
		browsesRepositories();
	}

	private String toPathFormat(String packageName) {
		Objects.requireNonNull(packageName, "packageName ne peut pas être nul");
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
		return list.stream().filter(img -> isClass(img)).collect(Collectors.toList());
	}
	
	public List<Image> getAllPhotos() {
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
		Objects.requireNonNull(imgCollection, "imgCollection ne peut pas être nul");	
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
	
	private void addToList(String subCategory, String filepath) {
		Objects.requireNonNull(filepath, "filepath ne peut pas être nul");
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
		Objects.requireNonNull(nbr, "nbr ne peut pas être nul");
		Objects.requireNonNull(list, "list ne peut pas être nul");
		Objects.requireNonNull(rightCategory, "rightCategory ne peut pas être nul");
		Objects.requireNonNull(level, "level ne peut pas être nul");
		
		List<Image> allPhotos = new ArrayList<Image>();
		List<Image> selectedPhotos = new ArrayList<Image>();
		for (int i = 0; i < list.size(); ++i) {
			/* On vérifie que que la catégorie chargée n'est pas la bonne
			 * Puis, selon la difficulté, on vérifie si la catégorie chargée est plus ou moins apparentée
			 * avec la bonne catégorie
			 * Plus le niveau augmente, plus les catégories ont un package similaire
			 * Si la N-ième catégorie parente de rightCat apparaît dans le package de la catégories chargée
			 * Alors on peut utiliser cette catégorie
			 */
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
		String pack = this.getClass().getPackageName();
		for (int i = 0; i < N; ++i) {
			pack = pack.substring(0, pack.lastIndexOf("."));
		}
		pack = pack.substring(pack.lastIndexOf("."), pack.length());
		pack = pack.replace(".", "");
		return pack;
	}

	private void browsesRepositories() {
        browsesRepositories(new File(this.categoryUrl), "", 0, 10);
    }
	
    private void browsesRepositories(File dir, String subCategory, int depth, int maxDepth) {
    	Objects.requireNonNull(dir, "dir ne peut pas être nul");
		Objects.requireNonNull(subCategory, "subCategory ne peut pas être nul");
		Objects.requireNonNull(depth, "depth ne peut pas être nul");
		Objects.requireNonNull(maxDepth, "maxDepth ne peut pas être nul");
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
    
    private boolean matchesCategory(String path) {
    	Objects.requireNonNull(path, "path ne peut pas être nul");
    	return path.contains(this.getClass().getSimpleName().toLowerCase());
    }
    
    private boolean isClass(Image img) {
    	return this.getClass().getSimpleName().toLowerCase().equals(img.getCategory());
    }

}
