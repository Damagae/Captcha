package fr.upem.captcha;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import fr.upem.captcha.images.Image;
import fr.upem.captcha.images.ImagesCollection;
import fr.upem.captcha.ui.Ui;

public class Controller {

	private boolean success;
	private boolean action;
	private int difficulty;
	private Categories cat;
	private List<ImagesCollection> categoriesList;
	private ImagesCollection rightCat;
	private List<Image> imgList;
	private JFrame frame;
	private JButton okButton;
	
	public Controller() throws Exception {
		super();
		action = false;
		success = false;
		difficulty = 2;
		cat = new Categories();	
		imgList = new ArrayList<Image>();	
	}
	
	public boolean getSuccess() {
		return this.success;
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public void createImageList() throws Exception {
		List<Image> right4;
		List<Image> wrong5;
		imgList.clear();
		right4 = rightCat.select(4);
		imgList.addAll(right4);
		wrong5 = ImagesCollection.selectRandom(5, categoriesList, rightCat, difficulty);
		while (wrong5.size() < 5) {
			System.out.println("Et ça repart");
			categoriesList = cat.getDifficulty(difficulty);
			wrong5 = ImagesCollection.selectRandom(5, categoriesList, rightCat, difficulty);
		}
		imgList.addAll(wrong5);
		Collections.shuffle(imgList);
	}
	
	public void display() throws Exception  {
		frame = new JFrame("Captcha"); // Création de la fenêtre principale
		GridLayout layout = Ui.createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(1024, 768); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.

		okButton = Ui.createOkButton(this);
		
		//if (imgList.size() == 9) {
			for (int i = 0; i < 9; ++i) {
				frame.add(Ui.createLabelImage(imgList.get(i)));
			}
		//}
		frame.add(new JTextArea("Please select every pictures matching \"" + rightCat.getCategory() + "\""));
		
		frame.add(okButton);
		
		frame.setVisible(true);
	}
	
	public void updateDisplay() throws Exception {
		frame.getContentPane().removeAll();
		
			for (int i = 0; i < 9; ++i) {
				frame.add(Ui.createLabelImage(imgList.get(i)));
			}
		
		frame.add(new JTextArea("Please select every pictures matching \"" + rightCat.getCategory() + "\""));
		
		frame.add(okButton);	
		
		frame.setVisible(true);
	}
	
	public void harder() {
		if (difficulty + 1 < 5)
			++difficulty;
	}
	
	public void load() throws Exception {
		categoriesList = cat.getDifficulty(difficulty);
		rightCat = cat.getRandomCat(categoriesList);
		createImageList();
	}

	public boolean verify(ArrayList<fr.upem.captcha.images.Image> list) throws Exception {
		int count = 0;
		for (int i = 0; i < list.size(); ++i) {
			if (!list.get(i).getICClass().isPhotoCorrect(rightCat))
				return false;
			++count;
		}
		if (count == 4)
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
