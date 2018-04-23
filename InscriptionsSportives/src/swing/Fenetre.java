package swing;

import java.awt.event.*;

import javax.swing.*;

public class Fenetre
{	
	JFrame fenetre = new JFrame();
	JPanel menuInscri = new JPanel();
	JPanel menuCompet = new JPanel();
	JPanel menuCandidat = new JPanel();
	JTable gestionCompet = new JTable();
	JTable gestionEquipe = new JTable();
	JTable gestionPersonne = new JTable();
	
	public Fenetre()
	{
		fenetre.setTitle("Inscriptions Sportives");
		fenetre.setSize(800, 600);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setResizable(false);
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
		fenetre.add(menuInscri);
		
		menuInscri.setLayout(null);
		JButton menuInscriptions = new JButton("Menu inscriptions");
		menuInscriptions.setBounds(100, 200, 200, 50);
		menuInscri.add(menuInscriptions);
		
		menuInscriptions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setContentPane(menuCompet);
				fenetre.repaint();       
				fenetre.revalidate();
			}
		});
		
		JButton quitter = new JButton("Quitter");
		quitter.setBounds(100, 280, 200, 50);
		menuInscri.add(quitter);
		
		quitter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		menuCompet.setLayout(null);
		JButton menuCompetitions = new JButton("Menu compétitions");
		menuCompetitions.setBounds(100, 180, 200, 50);
		menuCompet.add(menuCompetitions);
		
		menuCompetitions.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setContentPane(gestionCompet);
				fenetre.repaint();       
				fenetre.revalidate();
			}
		});
		
		JButton menuCandidats = new JButton("Menu candidats");
		menuCandidats.setBounds(100, 260, 200, 50);
		menuCompet.add(menuCandidats);
		
		menuCandidats.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setContentPane(menuCandidat);
				fenetre.repaint();       
				fenetre.revalidate();
			}
		});
		
		JButton retourInscri = new JButton("Retour");
		retourInscri.setBounds(100, 340, 200, 50);
		menuCompet.add(retourInscri);
		
		retourInscri.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setContentPane(menuInscri);
				fenetre.repaint();       
				fenetre.revalidate();
			}
		});
		
		menuCandidat.setLayout(null);
		JButton menuEquipe = new JButton("Menu equipes");
		menuEquipe.setBounds(100, 180, 200, 50);
		menuCandidat.add(menuEquipe);
		
		menuCandidats.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setContentPane(gestionEquipe);
				fenetre.repaint();       
				fenetre.revalidate();
			}
		});
		
		JButton menuPersonne = new JButton("Menu personnes");
		menuPersonne.setBounds(100, 260, 200, 50);
		menuCandidat.add(menuPersonne);
		
		menuCandidats.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setContentPane(gestionPersonne);
				fenetre.repaint();       
				fenetre.revalidate();
			}
		});
		
		JButton retourCompet = new JButton("Retour");
		retourCompet.setBounds(100, 340, 200, 50);
		menuCandidat.add(retourCompet);
		
		retourCompet.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fenetre.setContentPane(menuCompet);
				fenetre.repaint();       
				fenetre.revalidate();
			}
		});
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run() 
			{
				new Fenetre();
			}	
		});
	}
}
