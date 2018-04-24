package fr.upem.captcha;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import fr.upem.captcha.ui.Ui;
import fr.upem.captcha.images.brown.Brown;

public class Main {

	public static void main(String[] args) throws IOException {
		
		Brown brown = new Brown();
		brown.browsesRepositories();
		
		System.out.println(brown.getList());
		
		JFrame frame = new JFrame("Captcha"); // Création de la fenêtre principale
		GridLayout layout = Ui.createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(1024, 768); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		 
		JButton okButton = Ui.createOkButton();

		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/puppies/puppy01.jpg")); //ajouter des composants à la fenêtre
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/baggels/baggel01.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/puppies/puppy02.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/baggels/baggel02.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/puppies/puppy03.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/baggels/baggel03.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/puppies/puppy04.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/baggels/baggel04.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/brown/soft/round/puppies/puppy05.jpg"));
		
		frame.add(new JTextArea("Cliquez n'importe où ... juste pour tester l'interface !"));
		
		frame.add(okButton);
		
		frame.setVisible(true);
		

	}

}