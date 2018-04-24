package fr.upem.captcha;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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

public class Main {

	public static void main(String[] args) throws IOException {
		
		JFrame frame = new JFrame("Captcha"); // Création de la fenêtre principale
		GridLayout layout = Ui.createLayout();  // Création d'un layout de type Grille avec 4 lignes et 3 colonnes
		
		frame.setLayout(layout);  // affection du layout dans la fenêtre.
		frame.setSize(1024, 768); // définition de la taille
		frame.setResizable(false);  // On définit la fenêtre comme non redimentionnable
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Lorsque l'on ferme la fenêtre on quitte le programme.
		 
		JButton okButton = Ui.createOkButton();

		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob01.jpg")); //ajouter des composants à la fenêtre
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob02.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob03.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob04.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob05.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob06.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob07.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob08.jpg"));
		frame.add(Ui.createLabelImage("/fr/upem/captcha/images/puppybaggel/pob09.jpg"));
		
		frame.add(new JTextArea("Cliquez n'importe où ... juste pour tester l'interface !"));
		
		frame.add(okButton);
		
		frame.setVisible(true);
		

	}

}