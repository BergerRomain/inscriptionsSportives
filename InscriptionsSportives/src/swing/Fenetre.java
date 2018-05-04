package swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

public class Fenetre extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JPanel inscriptions = new JPanel();
	
    Object[][] valeurCompetitions = {{"", "", "", ""}};
	String titreCompetitions [] = {"Nom", "Date de Cloture", "En equipes ?", "Candidats"};
	JTable MenuCompetitions = new JTable(new DefaultTableModel(valeurCompetitions, titreCompetitions));
	  
	Object[][] valeurPersonnes = {
		      {"Moi", "lui", "vous", "", ""},
		      {"Rohan","malhi","malecri", "", ""}
		    };

	String titrePersonnes[] = {"Nom", "Prenom", "Mail", "Equipes", "Competitions"};
	JTable MenuPersonnes = new JTable(valeurPersonnes, titrePersonnes);
	
    Object[][] valeurEquipes = {
    	      {"Cysboy", "", ""},
    	      {"Rohan", "", ""}
    	    };

    String titreEquipes[] = {"Nom", "Personnes", "Competitions"};
    JTable MenuEquipes = new JTable(valeurEquipes, titreEquipes);

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
    
    JPanel boutonsCompetition = new JPanel();
    JButton AjouterCompetition = new JButton("Ajouter");
    JButton SupprimerCompetition = new JButton("Supprimer");
     
    public void changerMenuCompetitions(){
        this.getContentPane().removeAll();
        this.AjouterCompetition.addActionListener(new EcouteurBoutonAjouterCompetitions());
    	this.Retour.addActionListener(new EcouteurBoutonRetour());
    	this.boutonsCompetition.add(AjouterCompetition);
    	this.boutonsCompetition.add(SupprimerCompetition);
    	this.boutonsCompetition.add(Retour);
        this.getContentPane().add(new JScrollPane(MenuCompetitions), BorderLayout.CENTER);
        this.getContentPane().add(boutonsCompetition, BorderLayout.SOUTH);
        this.repaint();
        this.revalidate();
    }
    
    public class EcouteurBoutonChangerMenuCompetitions implements ActionListener{
        public void actionPerformed(ActionEvent clic) {
            Fenetre.this.changerMenuCompetitions();
        }
    }
    
    JLabel NomCompetition = new JLabel("Nom : ");
    JTextField SaisiNomCompetition = new JTextField();
    JLabel DateCloture = new JLabel("Date de cloture : ");
    JTextField SaisiDateCloture = new JTextField();
    JLabel EnEquipe = new JLabel("En equipe : ");
    JTextField SaisiEnEquipe = new JTextField();
    
    JPanel boutonsAjouterCompetition = new JPanel();
    JButton ValiderCompetition = new JButton("Valider");
    JButton RetourCompetition = new JButton("Retour");
    
    public void AjouterCompetitions(){
        this.getContentPane().removeAll();
        SaisiNomCompetition.setPreferredSize(new Dimension(150, 20));
        SaisiDateCloture.setPreferredSize(new Dimension(150, 20));
        SaisiEnEquipe.setPreferredSize(new Dimension(150, 20));
        this.boutonsAjouterCompetition.add(NomCompetition);
        this.boutonsAjouterCompetition.add(SaisiNomCompetition);
        this.boutonsAjouterCompetition.add(DateCloture);
        this.boutonsAjouterCompetition.add(SaisiDateCloture);
        this.boutonsAjouterCompetition.add(EnEquipe);
        this.boutonsAjouterCompetition.add(SaisiEnEquipe);
        this.ValiderCompetition.addActionListener(new EcouteurBoutonValiderAjouterCompetitions());
        this.RetourCompetition.addActionListener(new EcouteurBoutonChangerMenuCompetitions());
        this.boutonsAjouterCompetition.add(ValiderCompetition);
        this.boutonsAjouterCompetition.add(RetourCompetition);
        this.getContentPane().add(boutonsAjouterCompetition, BorderLayout.CENTER);
        this.repaint();
        this.revalidate();
    }
    
    public class EcouteurBoutonAjouterCompetitions implements ActionListener{
        public void actionPerformed(ActionEvent clic) {
        	Fenetre.this.AjouterCompetitions();
        }
    }
    
    public class EcouteurBoutonValiderAjouterCompetitions implements ActionListener{
        public void actionPerformed(ActionEvent clic) {
        	Fenetre.this.changerMenuCompetitions();
        	Object[] donnees = new Object[]{SaisiNomCompetition, SaisiDateCloture, SaisiEnEquipe, ""};
        	SaisiNomCompetition.getText();
        	SaisiDateCloture.getText();
        	SaisiEnEquipe.getText();
        	DefaultTableModel model = (DefaultTableModel) MenuCompetitions.getModel();
        	model.addRow(donnees);
        }
    }
    
    JPanel boutonsPersonnes = new JPanel();
    JButton AjouterPersonnes = new JButton("Ajouter");
    JButton SupprimerPersonnes = new JButton("Supprimer");
    
    public void changerMenuPersonnes(){
        this.getContentPane().removeAll();
    	this.Retour.addActionListener(new EcouteurBoutonRetour());
    	this.boutonsPersonnes.add(AjouterPersonnes);
    	this.boutonsPersonnes.add(SupprimerPersonnes);
    	this.boutonsPersonnes.add(Retour);
        this.getContentPane().add(new JScrollPane(MenuPersonnes), BorderLayout.CENTER);
        this.getContentPane().add(boutonsPersonnes, BorderLayout.SOUTH);
        this.repaint();
        this.revalidate();
    }
     
    public class EcouteurBoutonChangerMenuPersonnes implements ActionListener{
        public void actionPerformed(ActionEvent clic) {
            Fenetre.this.changerMenuPersonnes();
        }
    }
    
    JPanel boutonsEquipes = new JPanel();
    JButton AjouterEquipes = new JButton("Ajouter");
    JButton SupprimerEquipes = new JButton("Supprimer");
    
    public void changerMenuEquipes(){
        this.getContentPane().removeAll();
    	this.Retour.addActionListener(new EcouteurBoutonRetour());
    	this.boutonsEquipes.add(AjouterEquipes);
    	this.boutonsEquipes.add(SupprimerEquipes);
    	this.boutonsEquipes.add(Retour);
        this.getContentPane().add(new JScrollPane(MenuEquipes), BorderLayout.CENTER);
        this.getContentPane().add(boutonsEquipes, BorderLayout.SOUTH);
        this.repaint();
        this.revalidate();
    }
     
    public class EcouteurBoutonChangerMenuEquipes implements ActionListener{
        public void actionPerformed(ActionEvent clic) {
            Fenetre.this.changerMenuEquipes();
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
        new Fenetre();
    }
}
