/* * * * * * * * * * * * * * * * * *
 * CAPTCHA                         *
 * par Daphné Rose et Flavie Lucas *
 *                                 *
 * Class Categories                *
 * * * * * * * * * * * * * * * * * */

package fr.upem.captcha;

import fr.upem.captcha.images.Images;
import fr.upem.captcha.images.Category;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Categories {
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
	private void browsesPackages(File dir, int depth, int maxDepth, String classPackage) throws Exception {
		Objects.requireNonNull(dir, "dir ne peut pas être nul");
		Objects.requireNonNull(depth, "depth ne peut pas être nul");
		Objects.requireNonNull(maxDepth, "maxDepth ne peut pas être nul");
		Objects.requireNonNull(classPackage, "classPackage ne peut pas être nul");
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
            		Category element = (Category) clazz.newInstance(); // On la cast en Category (dont elle hérite forcément)
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
	
	private void addToList(int difficulty, Category element) {
		Objects.requireNonNull(difficulty, "difficulty ne peut pas être nul");
		Objects.requireNonNull(element, "element ne peut pas être nul");
		List<Category> l;
		if (this.list.containsKey(difficulty)) { // Vérifie si la clé difficulty est déjà présente
			l = this.list.get(difficulty); // Récupère le tableau correspondant à la clé
		}
		else {
			l = new ArrayList<Category>(); // Sinon crée une nouvelle list
		}
		l.add(element);
		this.list.put(difficulty, l); // Ajoute l à la liste avec la clé correspondant à la difficulté
	}
	
	/* getDifficulty
	 * renvoie une liste de Category qui correspondent à la difficulté et aux difficultés supérieures de celle donnée
	 */
	List<Category> getDifficulty(int difficulty) {
		Objects.requireNonNull(difficulty, "difficulty ne peut pas être nul");
		List<Category> l = new ArrayList<Category>();
		while(this.list.containsKey(difficulty)) {
			l.addAll(this.list.get(difficulty));
			++difficulty;
		}
		return l;
	}
    
    private String capitalize(String str) { // Fonction pratique pour mettre une majuscule au début d'un mot
    	Objects.requireNonNull(str, "str ne peut pas être nul");
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
    
    Category getRandomCat(List<Category> list) { // retourne une Category parmi toutes celles données
    	Objects.requireNonNull(list, "list ne peut pas être nul");
    	List<Category> randList = list;
		Collections.shuffle(randList);
		return randList.get(0);
	}
	
	

}
