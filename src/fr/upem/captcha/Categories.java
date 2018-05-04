package fr.upem.captcha;

import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.ImagesCollection;
import fr.upem.captcha.images.brown.Brown;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class Categories {
	private Map<Integer, ImagesCollection> list;

	public Categories() throws Exception {
		super();
		String path = Images.class.getResource("Images.class").getPath();
		File dir = new File(path);
		path = dir.getParent();
		browsesPackages(new File(path), 1, 10);
	}
	
	public void browsesPackages(File dir, int depth, int maxDepth) throws Exception {
        if (depth > maxDepth) {
            return;
        }
        if (dir.isDirectory()) { // Si dir est un dossier
        	if (depth > 1)
        	{
        		this.list.put(depth, (ImagesCollection) Class.forName(dir.getPath() + "/" + dir.getName().substring(0, 1).toUpperCase() + dir.getName().substring(1)).newInstance() + ".class");
        	}
        	File[] subDirs = dir.listFiles(); // On crée un tableau de Files
        	for (int i = 0; i < subDirs.length; ++i){  // Pour chaque File
        		File subDir = subDirs[i];
        		browsesPackages(subDir, depth+1, maxDepth);
        	}
        } 
    }
	
	public Map<Integer, ImagesCollection> getDifficulty(int difficulty) {
		Map<Integer, ImagesCollection> listDif = null;
		if (this.list.containsKey(difficulty)) {
			listDif.put(difficulty, this.list.get(difficulty));
		}
		listDif.putAll(this.list);
		return listDif;
	}
    
    public Map<Integer, ImagesCollection> getCategories(int difficulty) throws Exception {
    	File dir = new File(System.getProperty("user.dir") + "/src/fr/upem/captcha/images");
    	Map<Integer, ImagesCollection> categories = null;    	
    	browsesPackages(dir, 1, 10);
    	System.out.println(categories);
    	Set<Integer> keys = categories.keySet();
    	System.out.println(keys);
    	//for (int i = 0; i < categories.size(); ++i)
    		//categories.remove(i);
    	return categories;
    }
	
	

}
