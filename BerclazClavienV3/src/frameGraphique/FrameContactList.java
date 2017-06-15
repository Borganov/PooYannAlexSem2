package frameGraphique;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import contact.Contact;

public class FrameContactList extends FrameGeneral{

	//Cr�ation de l'array list de contact
	private ArrayList<Contact> contactList = new ArrayList<Contact>();
	//Cr�ation de la listeContact	
	private JList listContact ;
	
	//Ajout du boutton nouveau contact
	JButton newContact = new JButton(new ImageIcon(getClass().getResource("/add.png")));
	
	//Font de la liste
	Font fontList = new Font("Verdana",Font.BOLD,16);
	//Font du titre
	Font titleFont = new Font("Verdanan", Font.BOLD, 24);
	
	//Ajout du titre de Contact liste
	JLabel titre = new JLabel("Liste de contacts");
	
	
	FrameContactList() throws ClassNotFoundException, IOException{
		//Importation des donn�es de BDD_Contact dans l'arrayList
		DownloadDataContact("./BDD_Contact",contactList);
		
		listContact = new JList(contactList.toArray());
		
		//Ajout de la liste de contact avec le scroll
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10,70,382,590);
		scroll.setViewportView(listContact);
		listContact.setLayoutOrientation(JList.VERTICAL);		
		add(scroll);
		listContact.setFont(fontList);
		listContact.setBorder(new EmptyBorder(10,10,10,10));
		
		//Ajout du titre
		add(titre);
		titre.setFont(titleFont);
		titre.setBounds(75,30,300,20);
		

		
		//ajout des listener
		Ecouteurs ecouteur = new Ecouteurs();
		listContact.addListSelectionListener(ecouteur);
		getBoutonHome().addActionListener(ecouteur);
		getBoutonOff().addActionListener(ecouteur);
		
		//Ajout boutton nouveau contact
		add(newContact);
		newContact.setBounds(320, 15, 50, 50);
		newContact.setContentAreaFilled(false);
		newContact.setBorderPainted(false);
		newContact.addActionListener(ecouteur);
		
		
	}
	
	FrameContactList(ArrayList<Contact> l) throws ClassNotFoundException, IOException{
		//Importation des donn�es de BDD_Contact dans l'arrayList
		contactList = l;
		
		listContact = new JList(contactList.toArray()) ;

		
		//Ajout de la liste de contact avec le scroll
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(10,70,382,590);
		scroll.setViewportView(listContact);
		listContact.setLayoutOrientation(JList.VERTICAL);		
		add(scroll);
		listContact.setFont(fontList);
		listContact.setBorder(new EmptyBorder(10,10,10,10));
		
		//Ajout du titre
		add(titre);
		titre.setFont(titleFont);
		titre.setBounds(75,30,300,20);
				

				
		//ajout des listener
		Ecouteurs ecouteur = new Ecouteurs();
		listContact.addListSelectionListener(ecouteur);
		getBoutonHome().addActionListener(ecouteur);
		getBoutonOff().addActionListener(ecouteur);
			
		//Ajout boutton nouveau contact
		add(newContact);
		newContact.setBounds(320, 15, 50, 50);
		newContact.setContentAreaFilled(false);
		newContact.setBorderPainted(false);
		newContact.addActionListener(ecouteur);
			
	}
	
	//M�thode de d�s�rialisation des fichiers pr�sent dans le fichier BDD_Contact
	private static void DownloadDataContact(String path, ArrayList<Contact> listcontact) throws IOException, ClassNotFoundException {
		File folder = new File(path);
		if(folder.isDirectory()==true){
			File[] list = folder.listFiles();
			
			for (int i = 0; i < list.length; i++) {
				
				FileInputStream in = new FileInputStream( "./BDD_Contact/" + list[i].getName());
				ObjectInputStream ois = new ObjectInputStream( in );
				listcontact.add( (Contact) ois.readObject());
				ois.close(); 	
			}
			
			for (int i = 0; i < list.length; i++) {
				
				list[i].delete();	
			}
		}
		
	}
	
	public class Ecouteurs implements ListSelectionListener, ActionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			int contactSelected = (e.getFirstIndex());
			//instantiation nouvelle fen�tre
			FrameContactModify frameContactModify = null;
			try {
				frameContactModify = new FrameContactModify(contactSelected, contactList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frameContactModify.setVisible(true);
			//fermeture de la f�netre actuelle
			dispose();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==getBoutonHome()){
				//S�rialisation des contacts pr�sents dans la liste
				try {
					UploadDataContact(contactList);
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
			
			if (e.getSource()==getBoutonOff()){
				//S�rialisation des contacts pr�sents dans la liste
				try {
					UploadDataContact(contactList);
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//fermeture de la f�netre actuelle
				
			}
			
			if (e.getSource()== newContact){
				//instantiation nouvelle fen�tre
				FrameContactAdd frameContactAdd = new FrameContactAdd(contactList);
				frameContactAdd.setVisible(true);
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
