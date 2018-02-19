package dialogueUtilisateur;

import static commandLineMenus.rendering.examples.util.InOut.getString;

import commandLineMenus.*;
import commandLineMenus.examples.employees.core.*;

public class DialogueUtilisateur 
{
	private ManageEmployees inscriptionSportive;
	
	public DialogueUtilisateur(ManageEmployees inscriptionSportive)
	{
		this.inscriptionSportive = inscriptionSportive;
	}
	
	public void start()
	{
		inscriptionMenu().start();
	}
	
	private Menu inscriptionMenu()
	{
		Menu inscriptionMenu = new Menu("Inscription sportive");
		inscriptionMenu.add(Inscription());
		inscriptionMenu.add(superUser(inscriptionSportive.getRoot()));
		inscriptionMenu.add(quitter());
		return inscriptionMenu;
	}
	
	private Menu superUser(Employee root)
	{
		Menu superUser = new Menu("Gerer le super utilisateur", "2");
		//superUser.add();
		//superUser.add();
		//superUser.add();
		//superUser.add();
		superUser.add(modifierMDP(root));
		superUser.addBack("6");
		return superUser;
	}
	
	private Option modifierMDP(final Employee root)
	{
		return new Option("Changer le mot de pasee", "5", () -> {root.setPassword(getString("Nouveau mot de passe : "));});
	}
	
	private Menu quitter()
	{
		Menu quitter = new Menu("Quitter", "3");
		quitter.add(quitterEtEnregistrer());
		quitter.add(quitterSansEnregistrer());
		quitter.addBack("3");
		return quitter;
	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter et enregistrer", "1", 
				() -> 
				{
					try
					{
						inscriptionSportive.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (ImpossibleToSaveException e)
					{
						System.out.println("Impossible d'effectuer la sauvegarde");
					}
				}
			);
	}
	
	private Option quitterSansEnregistrer()
	{
		return new Option("Quitter sans enregistrer", "2", Action.QUIT);
	}
	
	private Menu Inscription()
	{
		Menu Inscription = new Menu("Inscription", "1");
		Inscription.add(menuCompetition());
		Inscription.add(candidat());
		Inscription.addBack("3");
		return Inscription;
	}
	
	private Menu menuCompetition()
	{
		Menu menuCompetition = new Menu("Gerer les competitions", "1");
		//menuCompetition.add(afficherCompetition());
		//menuCompetition.add(ajouterCompetition());
		//menuCompetition.add(editerCompetition());
		menuCompetition.addBack("4");
		return menuCompetition;
	}
	
	private Menu candidat()
	{
		Menu candidat = new Menu("Gerer les candidats", "2");
		candidat.add(menuEquipe());
		candidat.add(menuPersonne());
		candidat.addBack("3");
		return candidat;
	}
	
	private Menu menuEquipe()
	{
		Menu menuEquipe = new Menu("Gerer les equipes", "1");
		//menuEquipe.add(afficherEquipe());
		//menuEquipe.add(ajouterEquipe());
		//menuEquipe.add(editerEquipe());
		menuEquipe.addBack("4");
		return menuEquipe;
	}
	
	private Menu menuPersonne()
	{
		Menu menuPersonne = new Menu("Gerer les personnes", "2");
		//menuPersonne.add(afficherPersonne());
		//menuPersonne.add(ajouterPersonne());
		//menuPersonne.add(editerPersonne());
		menuPersonne.addBack("4");
		return menuPersonne;
	}
	
	private boolean verifiePassword()
	{
		boolean ok = inscriptionSportive.getRoot().checkPassword(getString("Mot de passe : "));
		if (!ok)
			System.out.println("Mot de passe incorrect");
		return ok;
	}
	
	public static void main(String[] args)
	{
		DialogueUtilisateur console = new DialogueUtilisateur(ManageEmployees.getManageEmployees());
		if (console.verifiePassword())
		console.start();
	}
}
