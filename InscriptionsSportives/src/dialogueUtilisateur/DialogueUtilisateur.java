package dialogueUtilisateur;

import commandLineMenus.*;

public class DialogueUtilisateur 
{
	public void start()
	{
		inscriptionMenu().start();
	}
	
	private Menu inscriptionMenu()
	{
		Menu inscriptionMenu = new Menu("Inscription sportive");
		inscriptionMenu.add(Inscription());
		inscriptionMenu.addQuit("2");
		return inscriptionMenu;
	}
	
	private Menu Inscription()
	{
		Menu Inscription = new Menu("Inscription", "1");
		Inscription.add(competition());
		Inscription.add(candidat());
		Inscription.addBack("3");
		return Inscription;
	}
	
	private Menu competition()
	{
		Menu competition = new Menu("Creer une competition", "1");
		competition.addBack("2");
		return competition;
	}
	
	private Menu candidat()
	{
		Menu candidat = new Menu("Inscription d'un candidat", "2");
		candidat.add(menuEquipe());
		candidat.add(menuPersonne());
		candidat.addBack("3");
		return candidat;
	}
	
	private Menu menuEquipe()
	{
		Menu equipe = new Menu("Inscription d'une equipe", "1");
		equipe.addBack("1");
		return equipe;
	}
	
	private Menu menuPersonne()
	{
		Menu Personne = new Menu("Inscription d'une personne", "2");
		Personne.addBack("1");
		return Personne;
	}
	
	public static void main(String[] args)
	{
		DialogueUtilisateur console = new DialogueUtilisateur();
		console.start();
	}
}
