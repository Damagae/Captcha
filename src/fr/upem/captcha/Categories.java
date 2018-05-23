package fr.upem.captcha;

import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Categories {
	private Map<Integer, List<Category>> list;

	public Categories() throws Exception {
		super();
		String path = Images.class.getResource("Images.class").getPath();
		File dir = new File(path);
		path = dir.getParent(); // path is the current path of Images.class
		this.list = new HashMap<>();
		browsesPackages(new File(path), 1, 10, "fr.upem.captcha.images");
	}
	
	/* browsesPackages
	 * prend un dossier inital, une profondeur, une profondeur maximale et le package dans lequel on se trouve
	 * Tant que le fonction trouve un fichier .class, elle l'instancie et rajoute l'instance à list
	 * A la fin, list contient chaque catégorie d'images du projet
	 */
	public void browsesPackages(File dir, int depth, int maxDepth, String classPackage) throws Exception {
        if (depth > maxDepth) {
            return;
        }
        if (dir.isDirectory()) { // Si dir est un dossier
        	if (depth > 1)
        	{
        		String className = capitalize(dir.getName()); 
        		if (new File(dir.getPath() + "/" + className + ".class").isFile()) // Si le dossier a le même nom qu'un fichier .class
        		{
        			Class<?> clazz = Class.forName(classPackage + "." + className); // On instancie la classe
            		Category element = (Category) clazz.newInstance(); // On la cast en ImagesCollection (dont elle hérite forcément)
            		addToList(depth-1, element); // On la rajoute à la liste
        		}
        		
        	}
        	File[] subDirs = dir.listFiles(); // On crée un tableau de dossiers
        	for (int i = 0; i < subDirs.length; ++i){  // Pour chaque dossier
        		File subDir = subDirs[i];
        		browsesPackages(subDir, depth+1, maxDepth, classPackage + "." + subDir.getName()); // On effectue la recherche dans le package concerné
        	}
        } 
    }
	
	public void addToList(int difficulty, Category element) {
		List<Category> l;
		if (this.list.containsKey(difficulty)) { // Vérifie si la clé difficulty est déjà présente
			l = this.list.get(difficulty); // Récupère le tableau correspondant à la clé
		}
		else {
			l = new ArrayList<Category>(); // Sinon crée une nouvelle list
		}
		l.add(element);
		this.list.put(difficulty, l); // Ajoute la list à la liste avec la clé correspondant à la difficulté
	}
	/* getDifficulty
	 * retourne un liste d'ImagesCollection qui correspondent à la difficulté et aux difficultés supérieures de celles données
	 */
	public List<Category> getDifficulty(int difficulty) {
		List<Category> l = new ArrayList<Category>();
		while(this.list.containsKey(difficulty)) {
			l.addAll(this.list.get(difficulty));
			++difficulty;
		}
		return l;
	}
    
    private String capitalize(String str) { // Fonction pratique pour mettre une majuscule au début d'un mot
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
    
    public Category getRandomCat(List<Category> list) { // retourne une ImagesCollection parmis toutes celles données
    	List<Category> randList = list;
		Collections.shuffle(randList);
		return randList.get(0);
	}
	
	

}
