package swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

public class Fenetre extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JPanel inscriptions = new JPanel();
	private String[] OuiNon = {"oui", "non"};
    private JComboBox combo = new JComboBox(OuiNon);
	private JTable MenuCompetitions;
	  
	Object[][] valeurPersonnes = {
		      {"Moi", "lui", "vous"},
		      {"Rohan","malhi","malecri"}
		    };

	String titrePersonnes[] = {"Nom", "Prenom", "Mail"};
	JTable MenuPersonnes = new JTable(valeurPersonnes, titrePersonnes);
	
	
	    
    Object[][] valeurEquipes = {
    	      {"Cysboy"},
    	      {"Rohan"}
    	    };

    String titreEquipes[] = {"Nom"};
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
        Object[][] valeurCompetitions = {
        	      {"Cysboy", "12-12-2019", OuiNon[0]},
        	      {"Rohan", "12-02-2019", OuiNon[0]}
        	    };
      	String titreCompetitions [] = {"Nom", "Date de Cloture", "En equipes ?"};
      	ZModel modelCompetitions = new ZModel(valeurCompetitions, titreCompetitions);
      	this.MenuCompetitions = new JTable(modelCompetitions);
        this.AjouterCompetition.addActionListener(new EcouteurBoutonAjouterCompetitions());
    	this.Retour.addActionListener(new EcouteurBoutonRetour());
    	this.boutonsCompetition.add(AjouterCompetition);
    	this.boutonsCompetition.add(SupprimerCompetition);
    	this.boutonsCompetition.add(Retour);
        this.getContentPane().add(new JScrollPane(MenuCompetitions), BorderLayout.CENTER);
        this.getContentPane().add(boutonsCompetition, BorderLayout.SOUTH);
        this.MenuCompetitions.getColumn("En equipes ?").setCellEditor(new DefaultCellEditor(combo));
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
    
    JPanel boutonsAjouterCompetition = new JPanel();
    JButton ValiderCompetition = new JButton("Valider");
    JButton RetourCompetition = new JButton("Retour");
    
    public void AjouterCompetitions(){
        this.getContentPane().removeAll();
        SaisiNomCompetition.setPreferredSize(new Dimension(150, 20));
        SaisiDateCloture.setPreferredSize(new Dimension(150, 20));
        this.boutonsAjouterCompetition.add(NomCompetition);
        this.boutonsAjouterCompetition.add(SaisiNomCompetition);
        this.boutonsAjouterCompetition.add(DateCloture);
        this.boutonsAjouterCompetition.add(SaisiDateCloture);
        this.boutonsAjouterCompetition.add(EnEquipe);
        this.boutonsAjouterCompetition.add(combo);
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
        	((ZModel)MenuCompetitions.getModel()).addRow(new Object[]{SaisiNomCompetition, SaisiDateCloture, combo});
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
    
    class ZModel extends AbstractTableModel{
        private Object[][] data;
        private String[] title;

        //Constructeur
        public ZModel(Object[][] data, String[] title){
          this.data = data;
          this.title = title;
        }

        //Retourne le titre de la colonne à l'indice spécifié
        public String getColumnName(int col) {
          return this.title[col];
        }

        //Retourne le nombre de colonnes
        public int getColumnCount() {
          return this.title.length;
        }

        //Retourne le nombre de lignes
        public int getRowCount() {
          return this.data.length;
        }

        //Retourne la valeur à l'emplacement spécifié
        public Object getValueAt(int row, int col) {
          return this.data[row][col];
        }

        //Définit la valeur à l'emplacement spécifié
        public void setValueAt(Object value, int row, int col) {
          //On interdit la modification sur certaines colonnes !
          if(!this.getColumnName(col).equals("Age") && !this.getColumnName(col).equals("Suppression"))
            this.data[row][col] = value;
        }

        //Retourne la classe de la donnée de la colonne
        public Class getColumnClass(int col){
          //On retourne le type de la cellule à la colonne demandée
          //On se moque de la ligne puisque les types de données sont les mêmes quelle que soit la ligne
          //On choisit donc la première ligne
          return this.data[0][col].getClass();
        }

        public void addRow(Object[] data){
            int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
             
            Object temp[][] = this.data;
            this.data = new Object[nbRow+1][nbCol];
             
            for(Object[] value : temp)
               this.data[indice++] = value;
             
                
            this.data[indice] = data;
            temp = null;
            //Cette méthode permet d'avertir le tableau que les données
            //ont été modifiées, ce qui permet une mise à jour complète du tableau
            this.fireTableDataChanged();
         }
        
        public boolean isCellEditable(int row, int col){
          return true;
        }
      }

}
