package fr.upem.captcha;

import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.ImagesCollection;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categories {
	private Map<Integer, List<ImagesCollection>> list;

	public Categories() throws Exception {
		super();
		String path = Images.class.getResource("Images.class").getPath();
		File dir = new File(path);
		path = dir.getParent();
		this.list = new HashMap<>();
		browsesPackages(new File(path), 1, 10, "fr.upem.captcha.images");
	}
	
	public void browsesPackages(File dir, int depth, int maxDepth, String classPackage) throws Exception {
        if (depth > maxDepth) {
            return;
        }
        if (dir.isDirectory()) { // Si dir est un dossier
        	if (depth > 1)
        	{
        		String className = capitalize(dir.getName());
        		if (new File(dir.getPath() + "/" + className + ".class").isFile())
        		{
        			Class<?> clazz = Class.forName(classPackage + "." + className);
            		ImagesCollection element = (ImagesCollection) clazz.newInstance();
            		addToList(depth-1, element);
        		}
        		
        	}
        	File[] subDirs = dir.listFiles(); // On crée un tableau de Files
        	for (int i = 0; i < subDirs.length; ++i){  // Pour chaque File
        		File subDir = subDirs[i];
        		browsesPackages(subDir, depth+1, maxDepth, classPackage + "." + subDir.getName());
        	}
        } 
    }
	
	public void addToList(int difficulty, ImagesCollection element) {
		List<ImagesCollection> l;
		if (this.list.containsKey(difficulty)) {
			l = this.list.get(difficulty);
		}
		else {
			l = new ArrayList<ImagesCollection>();
		}
		l.add(element);
		this.list.put(difficulty, l);
	}
	
	public List<ImagesCollection> getDifficulty(int difficulty) {
		List<ImagesCollection> l = new ArrayList<ImagesCollection>();
		while(this.list.containsKey(difficulty)) {
			l.addAll(this.list.get(difficulty));
			++difficulty;
		}
		return l;
	}
    
    private String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
    
    public ImagesCollection getRandomCat(List<ImagesCollection> list) {
    	List<ImagesCollection> randList = list;
		Collections.shuffle(randList);
		return randList.get(0);
	}
	
	

}
