package frameGraphique;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import contact.Contact;

public class FrameContactModify extends FrameGeneral{
	
	
	JLabel civiliteL = new JLabel("Civilit� :");
	JLabel firstnameL = new JLabel("Pr�nom :");
	JLabel lastnameL = new JLabel("Nom :");
	JLabel phoneL = new JLabel("N� de t�l�phone :");
	JLabel title = new JLabel("Modifier un contact");
	
	JTextField firstnameF = new JTextField();
	JTextField lastnameF = new JTextField();
	JTextField phoneF = new JTextField();
	
	String[] civiliteList = {"Monsieur", "Madame"};
	JComboBox<String> civiliteC = new JComboBox<String>(civiliteList);
	
	
	JButton save = new JButton(new ImageIcon(getClass().getResource("/save.png")));
	JButton delete = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
	
	private ArrayList<Contact> list;
	private int ind;
	
	FrameContactModify(int i, ArrayList<Contact> l) throws IOException{
		list = l;
		ind = i;
		Font titleFont = new Font("Verdanan", Font.BOLD, 24);
		Font subtitleFont= new Font("Verdana",Font.BOLD,13);
		Font contentFont = new Font("Verdana",Font.BOLD,12);
		
		
		//Ajout du titre de la fen�tre
		add(title);
		title.setBounds(75,30,300,20);
		title.setFont(titleFont);
		
		//Ajout label Civilit�
		add(civiliteL);
		civiliteL.setBounds(25	,245, 150, 30); //axe x, axe y, largeur, hauteur
		civiliteL.setFont(subtitleFont);
		
		//Ajout label pr�nom
		add(firstnameL);
		firstnameL.setBounds(25	,275, 150, 30); //axe x, axe y, largeur, hauteur
		firstnameL.setFont(subtitleFont);
		
		//Ajout label nom
		add(lastnameL);
		lastnameL.setBounds(25 ,305, 150, 30); //axe x, axe y, largeur, hauteur
		lastnameL.setFont(subtitleFont);
		
		//Ajout label phone
		add(phoneL);
		phoneL.setBounds(25	,335, 350, 30); //axe x, axe y, largeur, hauteur
		phoneL.setFont(subtitleFont);
		
		
		ImageIcon photoProfil = new ImageIcon("./ImagesGallerie/1.jpg");
		JButton photoProfilBoutton = new JButton(photoProfil);
		add(photoProfilBoutton);
		photoProfilBoutton.setBounds(25,75,150,150);

		
		//Ajout field pr�nom
		add(firstnameF);
		firstnameF.setBounds(170,275, 200, 30); //axe x, axe y, largeur, hauteur
		firstnameF.setText(list.get(ind).getFirstName());
		firstnameF.setFont(contentFont);
		//Ajout field nom
		add(lastnameF);
		lastnameF.setBounds(170,305, 200, 30); //axe x, axe y, largeur, hauteur
		lastnameF.setText(list.get(ind).getLastName());
		lastnameF.setFont(contentFont);
		//Ajout field t�l�phone
		add(phoneF);
		phoneF.setBounds(170,335, 200, 30); //axe x, axe y, largeur, hauteur
		phoneF.setText(list.get(ind).getPhoneNumber());
		phoneF.setFont(contentFont);
		
		//Ajout ComboBox Civilit�
		add(civiliteC);
		civiliteC.setBounds(170,245, 200, 27); //axe x, axe y, largeur, hauteur
		civiliteC.setSelectedItem(list.get(ind).getCivilite());
		civiliteC.setFont(contentFont);
		
		//Ajout boutton sauvegarder
		add(save);
		save.setBounds(100, 580, 80, 80);
		save.setContentAreaFilled(false);
		save.setBorderPainted(false);
		Ecouteurs ecouteur = new Ecouteurs();
		save.addActionListener(ecouteur);
		
		//Ajout boutton supprimer
		add(delete);
		delete.setBounds(220, 580, 80, 80);
		delete.setContentAreaFilled(false);
		delete.setBorderPainted(false);
		delete.addActionListener(ecouteur);
		
	}
	
	public class Ecouteurs implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//si clique sur gallerie
			if (e.getSource()==save){
				
				list.get(ind).setCivilite((String)civiliteC.getSelectedItem());;
				list.get(ind).setFirstName(firstnameF.getText());
				list.get(ind).setLastName(lastnameF.getText());
				list.get(ind).setPhoneNumber(phoneF.getText());
				
				FrameContactList interfaceContacts = null;
				try {
					interfaceContacts = new FrameContactList(list);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				interfaceContacts.setVisible(true);
				//fermeture fen�tre actuelle
				dispose();
				
				
				
			}
			
			//Boutton supprimer contact
			if (e.getSource()==delete){
				
				list.remove(ind);
				
				FrameContactList interfaceContacts = null;
				try {
					interfaceContacts = new FrameContactList(list);
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				interfaceContacts.setVisible(true);
				//fermeture fen�tre actuelle
				dispose();
				
			}
			
			//Boutton home du frame interfaceContactModify
			if (e.getSource()==getBoutonHome()){
				//S�rialisation des contacts pr�sents dans la liste
				try {
					UploadDataContact(list);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//instantiation nouvelle fen�tre
				FramePrincipal menuPrincipal = new FramePrincipal();
				menuPrincipal.setVisible(true);
				//fermeture de la f�netre actuelle
				dispose();
			}
			
		}
		
	}
	
	//M�thode de s�rialisation des contacts
		private static void UploadDataContact(ArrayList<Contact> listcontact) throws IOException {
			
			for (int i = 0; i < listcontact.size(); i++) {
				 FileOutputStream out = new FileOutputStream("./BDD_Contact/" + listcontact.get(i).getLastName()+listcontact.get(i).getFirstName()+".ser");
				 ObjectOutputStream oos = new ObjectOutputStream( out );
				 oos.writeObject(listcontact.get(i));
				 oos.close(); 
			}
			
				
		}
	
	
}