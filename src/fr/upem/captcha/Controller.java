/* * * * * * * * * * * * * * * * * *
 * CAPTCHA                         *
 * par Daphné Rose et Flavie Lucas *
 *                                 *
 * Class Controller                *
 * * * * * * * * * * * * * * * * * */

package fr.upem.captcha;

import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import fr.upem.captcha.images.Image;
import fr.upem.captcha.images.Category;
import fr.upem.captcha.ui.Ui;

public class Controller {

	private boolean success;
	private int difficulty;
	private Categories cat;
	private List<Category> categoriesList;
	private Category rightCat;
	private List<Image> imgList;
	private JFrame frame;
	private JButton okButton;
	
	public Controller() throws Exception {
		super();
		success = false;
		difficulty = 2; // There's only 1 category for level 1, so difficulty is set up to 2
		cat = new Categories();	
		imgList = new ArrayList<Image>();	
	}
	
	public boolean getSuccess() {
		return this.success;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	/* createImageList()
	 * returns void
	 * load imgList with every pictures that will be displayed in the captcha 
	 */
	private void createImageList() throws Exception {
		List<Image> right4; // A list of 4 right pictures
		List<Image> wrong5; // A list of 5 wrong pictures
		imgList.clear();
		right4 = rightCat.select(4); // 4 pictures are selected from the right category
		imgList.addAll(right4); // pictures are added to the list
		wrong5 = Category.selectRandom(5, categoriesList, rightCat, difficulty);
		// 5 random wrong pictures are selected, given the categoriesList, the rightCat and difficulty 
		imgList.addAll(wrong5); // pictures are added to the list
		Collections.shuffle(imgList); // the list is shuffled so the 4 right pictures are randomly disposed in the captcha
	}
	
	private void display() throws Exception {
		frame = new JFrame("Captcha"); // Création de la fenêtre principale
		GridLayout layout = Ui.createLayout5();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(1024, 768); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.

		okButton = Ui.createOkButton(this);
		
		this.setFrame();
	}
	
	public void setFrame() throws IOException {
		frame.add(new JLabel(""));
		frame.add(new JLabel("<html>Veuillez choisir les images correspondant au mot \"" + rightCat.getCategory() + "\"</html>"), 1);
		frame.add(new JLabel(""));
		
		if (imgList.size() == 9) {
			for (int i = 0; i < 9; ++i) {
				frame.add(Ui.createLabelImage(imgList.get(i)));
			}
		}
		else {
			System.out.println("Les images n'ont pas été complètement chargées");
		}
		
		frame.add(new JLabel(""));
		
		frame.add(okButton);	
		
		frame.setVisible(true);
	}
	
	public void updateDisplay() throws Exception {
		frame.getContentPane().removeAll();
		this.setFrame();
	}
	
	public void harder() {
		if (difficulty < 4)
			++difficulty;
	}
	
	public void load() throws Exception {
		categoriesList = cat.getDifficulty(difficulty); // Load a list of categories that fits the given difficulty
		Collections.shuffle(categoriesList);
		rightCat = cat.getRandomCat(categoriesList); // Choose a category among given ones that will be the right one
		createImageList();
	}

	public boolean verify(ArrayList<fr.upem.captcha.images.Image> list) throws Exception {
		// On ne met pas de requireNonNull ici car la liste peut être vide
		int count = 0;
		for (int i = 0; i < list.size(); ++i) {
			if (!list.get(i).getCategoryClass().isPhotoCorrect(rightCat)) // Vérifie que la classe de chaque Image correspond à celle de rightcat
				return false;
			++count;
		}
		if (count >= 4) // Vérifie qu'il y a au moins bonnes images 
			return true;
		else
			return false;
	}
	
	public void check(ArrayList<fr.upem.captcha.images.Image> list) throws Exception {
		success = verify(list);
	}
	
	public void start() throws InterruptedException {		
			try {
				load();
				display();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
}
