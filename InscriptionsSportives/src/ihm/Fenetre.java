package ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import hibernate.Passerelle;

public class Fenetre extends JFrame {

	private String supp = "Supprimer la ligne";
	private JPanel inscriptions = new JPanel();
	private JTable MenuCompetitions;
	private JTable MenuPersonnes;
	private JTable MenuEquipes;

	JButton Competitions = new JButton("Competitions");
	JButton Personnes = new JButton("Personnes");
	JButton Equipes = new JButton("Equipes");
	JButton Retour = new JButton("Retour");
	JButton Quitter = new JButton("Quitter");
  
	public Fenetre(){
		super("Inscriptions Sportives");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.Competitions.addActionListener(new EcouteurBoutonChangerMenuCompetitions());
		this.Personnes.addActionListener(new EcouteurBoutonChangerMenuPersonnes());
		this.Equipes.addActionListener(new EcouteurBoutonChangerMenuEquipes());
		this.Quitter.addActionListener(new EcouteurBoutonQuitter());
		this.inscriptions.add(this.Competitions);
		this.inscriptions.add(this.Personnes);
		this.inscriptions.add(this.Equipes);
		this.inscriptions.add(this.Quitter);
		this.getContentPane().add(inscriptions, BorderLayout.CENTER);
	}
 
	public class EcouteurBoutonChangerMenuCompetitions implements ActionListener{
		public void actionPerformed(ActionEvent clic) {
			Fenetre.this.createCompetition();
		}
	}
 
	JPanel boutons = new JPanel();
	JButton ajouter = new JButton("Ajouter une ligne");
	JButton retour = new JButton("Retour");
 
	private void createCompetition(){
		this.getContentPane().removeAll();
		Object[][] data = {};
		String  title[] = {"Nom", "Date de Cloture", "En equipes ?", "Candidats", "Suppression"};
		ZModel zModel = new ZModel(data, title); 
		this.MenuCompetitions = new JTable(zModel);
		this.MenuCompetitions.getColumn("Suppression").setCellEditor(new DeleteButtonEditor(new JCheckBox()));   
		ajouter.addActionListener(new AjouterCompetition());
    	retour.addActionListener(new EcouteurBoutonRetour());
    	this.boutons.add(ajouter);
    	this.boutons.add(retour);
    	this.getContentPane().add(new JScrollPane(MenuCompetitions), BorderLayout.CENTER);  
    	this.getContentPane().add(boutons, BorderLayout.SOUTH);
    	this.repaint();
    	this.revalidate();
	}     
  
	class AjouterCompetition implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Object[] donnee = new Object[] {"", "", "", "", supp};
			((ZModel)MenuCompetitions.getModel()).addRow(donnee);
		}
	}
 
	public class EcouteurBoutonChangerMenuPersonnes implements ActionListener{
		public void actionPerformed(ActionEvent clic) {
			Fenetre.this.createPersonnes();
		}
	}
  
	private void createPersonnes(){
		this.getContentPane().removeAll();
		Object[][] data = {};
		String  title[] = {"Nom", "Prenom", "Mail", "Equipes", "Competitions", "Suppression"};
		ZModel zModel = new ZModel(data, title); 
		this.MenuPersonnes = new JTable(zModel);     
		this.MenuPersonnes.getColumn("Suppression").setCellEditor(new DeleteButtonEditor(new JCheckBox()));
		ajouter.addActionListener(new AjouterPersonnes());
		retour.addActionListener(new EcouteurBoutonRetour());
		this.boutons.add(ajouter);
		this.boutons.add(retour);
		this.getContentPane().add(new JScrollPane(MenuPersonnes), BorderLayout.CENTER);   
		this.getContentPane().add(boutons, BorderLayout.SOUTH);
		this.repaint();
		this.revalidate();
	}     
  
	class AjouterPersonnes implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Object[] donnee = new Object[] {"", "", "", "", "", supp};
			((ZModel)MenuPersonnes.getModel()).addRow(donnee);
		}
	}
 
	public class EcouteurBoutonChangerMenuEquipes implements ActionListener{
		public void actionPerformed(ActionEvent clic) {
			Fenetre.this.createEquipes();
		}
	}
  
	private void createEquipes(){
		this.getContentPane().removeAll();
		Object[][] data = {};
		String  title[] = {"Nom", "Personnes", "Competitions", "Suppression"};
		ZModel zModel = new ZModel(data, title); 
		this.MenuEquipes = new JTable(zModel);     
		this.MenuEquipes.getColumn("Suppression").setCellEditor(new DeleteButtonEditor(new JCheckBox()));
		ajouter.addActionListener(new AjouterEquipes());
		retour.addActionListener(new EcouteurBoutonRetour());
		this.boutons.add(ajouter);
		this.boutons.add(retour);
    	this.getContentPane().add(new JScrollPane(MenuEquipes), BorderLayout.CENTER);   
    	this.getContentPane().add(boutons, BorderLayout.SOUTH);
    	this.repaint();
    	this.revalidate();
	}     
  
	class AjouterEquipes implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Object[] donnee = new Object[] {"", "", "", supp};
			((ZModel)MenuEquipes.getModel()).addRow(donnee);
		}
	}
 
	public void Retour(){
		this.getContentPane().removeAll();
		this.Competitions.addActionListener(new EcouteurBoutonChangerMenuCompetitions());
		this.Personnes.addActionListener(new EcouteurBoutonChangerMenuPersonnes());
		this.Equipes.addActionListener(new EcouteurBoutonChangerMenuEquipes());
		this.Quitter.addActionListener(new EcouteurBoutonQuitter());
     	this.inscriptions.add(this.Competitions);
     	this.inscriptions.add(this.Personnes);
     	this.inscriptions.add(this.Equipes);
     	this.inscriptions.add(this.Quitter);
     	this.getContentPane().add(inscriptions, BorderLayout.CENTER);
     	this.repaint();
     	this.revalidate();
	}
 
	public class EcouteurBoutonRetour implements ActionListener{
		public void actionPerformed(ActionEvent clic) {
			Fenetre.this.Retour();
		}
	}
 
	public class EcouteurBoutonQuitter implements ActionListener{
		public void actionPerformed(ActionEvent clic) {
			System.exit(0);
		}
	}
     
	public static void main(String[] args){
		try {
			Passerelle back = new Passerelle();
			back.open();
			Fenetre fen = new Fenetre();
			fen.setVisible(true);

		} catch (java.lang.RuntimeException e) {
			System.out.println("Erreur de connexion à la base de données, \nCode de l'erreur : " + e);
		}
	}
}
