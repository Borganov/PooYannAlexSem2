package frameGraphique;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.plaf.synth.SynthSeparatorUI;

import contact.Question;
import frameGraphique.FrameGallerie.Ecouteurs;

public class FrameGame extends FrameGeneral {

	private Question [] questions = new Question[10];
	
	private JLabel titre = new JLabel("Quizz");
	
	private JTextArea question;
	private JTextArea score;
	
	private JLabel responseTrue = new JLabel("Bravo, prochaine question!");
	private JLabel responseFalse = new JLabel("Oulalalalala, suite!");
	

	private JCheckBox choice1;
	private JCheckBox choice2; 
	private JCheckBox choice3;
	
	int alea;
	int cptScore;

	JButton next = new JButton("Passer � la prochaine question");
	
	public FrameGame(int s){
		cptScore = s;
		Font titleFont = new Font("Verdanan", Font.BOLD, 24);
		Font subtitleFont= new Font("Verdana",Font.BOLD,13);
		Font contentFont = new Font("Verdana",Font.BOLD,12);
		
		//Instanciation des questions
		questions[0] = new Question("Quel est le code postal du village de Venth�ne qui est un petit village se situant sur les hauts de Sierre?", "3973", "3960", "1950", 1);
		questions[1] = new Question("Combien font 2 + 2 ?", "6", "0", "4", 3);
		questions[2] = new Question("Quel est le nom de l'acteur jouant Harry Potter ?", "Sean Connery", "Tommy Lee Jones", "Daniel Radcliffe", 3);
		questions[3] = new Question("Quel est la capital de la Suisse", "Bern", "Sion", "Paris", 1);
		questions[4] = new Question("Combien font 8 / 4 + 4", "8", "6", "5", 2);
		questions[5] = new Question("Quel est la combinaison chimique du sel", "NaCl", "H2O", "O2", 1);
		questions[6] = new Question("Combien de pull peut-on tricoter avec un bouton", "8", "21", "37", 2);
		questions[7] = new Question("Convertir 1 Byte en Bit", "8", "16", "Pas convertible", 1);
		questions[8] = new Question("O� se trouve le petit village de Tuktoyaktuk", "En Inde", "En Afrique", "Au Canada", 2);
		questions[9] = new Question("30+1*4", "34", "124", "1024", 1);
		
		alea = (int)(Math.random()*(0+10));
		
		question = new JTextArea(questions[alea].getQuestion());
		choice1 = new JCheckBox("    " + questions[alea].getResponse1());
		choice2 = new JCheckBox("    " + questions[alea].getResponse2());
		choice3 = new JCheckBox("    " + questions[alea].getResponse3());
		
		//Ajout du titre du jeux
		add(titre);
		titre.setBounds(170,30,200,40);
		titre.setFont(titleFont);
		
		//Ajout de la question
		add(question);
		question.setBounds(50,100,300,40);
		question.setLineWrap(true);
		
		score = new JTextArea("Votre score est de " + cptScore);
		add(score);
		score.setBounds(80,450,250,20);

		
		
		//ajout des checks box servant � la selection de la r�ponse
		add(choice1);
		choice1.setBounds(60, 180, 250, 20);
		add(choice2);
		choice2.setBounds(60, 240, 250, 20);
		add(choice3);
		choice3.setBounds(60, 300, 250, 20);
		
		Ecouteur ecouteur = new Ecouteur();
		getBoutonHome().addActionListener(ecouteur);
		getBoutonOff().addActionListener(ecouteur);
		choice1.addActionListener(ecouteur);
		choice2.addActionListener(ecouteur);
		choice3.addActionListener(ecouteur);
		next.addActionListener(ecouteur);
		
	}
	
	public class Ecouteur implements ActionListener{
		
		private int select;
		boolean reponseControl;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource()==choice1){
				select=1;
				}
			if (e.getSource()==choice2){
				select=2;
				}
			if (e.getSource()==choice3){
				select=3;
			}
			
			reponseControl = questions[alea].reponseControl(select);
			
			if(reponseControl==true){
				add(responseTrue);
				responseTrue.setBounds(60, 360, 250, 20);
				cptScore = cptScore+3;
				}
				else{
					add(responseFalse);
					responseFalse.setBounds(60, 360, 250, 20);
					cptScore = cptScore-1;
				}
			add(next);
			next.setBounds(60, 400, 250, 20);
			
			if(e.getSource()==next){
				FrameGame game = new FrameGame(cptScore);
				game.setVisible(true);
				dispose();
			}
			
			if (e.getSource()==getBoutonHome()){
				//instantiation nouvelle fen�tre
				FramePrincipal menuPrincipal = new FramePrincipal();
				menuPrincipal.setVisible(true);
				//fermeture de la f�netre actuelle
				dispose();
				}
			
			if (e.getSource()==getBoutonOff()){
				//�teindre le t�l�phone
				dispose();
				}
		}
	}
}

