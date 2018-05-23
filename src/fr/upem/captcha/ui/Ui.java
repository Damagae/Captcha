package fr.upem.captcha.ui;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import fr.upem.captcha.Controller;

public class Ui {
	
	private static ArrayList<fr.upem.captcha.images.Image> selectedImages = new ArrayList<fr.upem.captcha.images.Image>();
	
	public static GridLayout createLayout(){
		return new GridLayout(4,4);
	}
	
	public static JButton createOkButton(Controller ctrl) {
		return new JButton(new AbstractAction("Vérifier") { //ajouter l'action du bouton
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() { 
				// faire des choses dans l'interface donc appeler cela dans la queue des évènements
					
					@Override
					public void run() { // c'est un runnable
						try {
							ctrl.check(selectedImages);
							selectedImages.clear();
							if (ctrl.getSuccess() == false) { // Mauvaise réponse
								JOptionPane.showMessageDialog(ctrl.getFrame(),
									    "Oh non ! Tu t'es trompé.",
									    "Résultat",
										JOptionPane.PLAIN_MESSAGE);
								ctrl.harder(); // On augmente la difficulté
								ctrl.load(); // On charge les nouvelles images
								ctrl.updateDisplay(); // On met à jour l'affichage
							}
							else {
								 int input = JOptionPane.showConfirmDialog(ctrl.getFrame(), 
										 	"Bravo ! Tu n'es pas un robot.",
											"Résultat",
											JOptionPane.DEFAULT_OPTION);
								 if (input == JOptionPane.OK_OPTION || input == JOptionPane.CLOSED_OPTION) {
									 ctrl.getFrame().dispose();
								 }
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
	
	public static JLabel createLabelImage(fr.upem.captcha.images.Image image) throws IOException {
		
		final URL url = Ui.class.getResource(image.getRelativeUrl()); //Aller chercher les images !! IMPORTANT 
		
		BufferedImage img = ImageIO.read(url); //lire l'image
		Image sImage = img.getScaledInstance(1024/4, 768/4, Image.SCALE_SMOOTH); //redimentionner l'image
		
		final JLabel label = new JLabel(new ImageIcon(sImage)); // créer le composant pour ajouter l'image dans la fenêtre
		
		label.addMouseListener(new MouseListener() { //Ajouter le listener d'évenement de souris
			private boolean isSelected = false;
			
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
		
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) { //ce qui nous intéresse c'est lorsqu'on clique sur une image, il y a donc des choses à faire ici
				EventQueue.invokeLater(new Runnable() { 
					
					@Override
					public void run() {
						if(!isSelected){
							label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
							isSelected = true;
							selectedImages.add(image);
							System.out.println(image.getCategory());
						}
						else {
							label.setBorder(BorderFactory.createEmptyBorder());
							isSelected = false;
							selectedImages.remove(image);
						}
						
					}
				});
				
			}
		});
		
		return label;
	}
}
