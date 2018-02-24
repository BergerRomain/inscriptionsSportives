package dialogueUtilisateur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commandLineMenus.*;
import inscriptions.*;
import static commandLineMenus.rendering.examples.util.InOut.*;

public class DialogueUtilisateur 
{
	private Inscriptions inscriptions;
	
	public DialogueUtilisateur(Inscriptions inscriptions)
	{
		this.inscriptions = inscriptions;
	}
	
	public void start()
	{
		inscriptionMenu().start();
	}
	
	private Menu inscriptionMenu()
	{
		Menu inscriptionMenu = new Menu("Inscription sportive");
		inscriptionMenu.add(Inscription());
		inscriptionMenu.add(quitter());
		return inscriptionMenu;
	}
	
	private Menu quitter()
	{
		Menu quitter = new Menu("Quitter", "2");
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
						inscriptions.sauvegarder();
						Action.QUIT.optionSelected();
					} 
					catch (IOException e) {
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
		menuCompetition.add(afficherCompetition());
		menuCompetition.add(ajouterCompetition());
		menuCompetition.add(selectionnerCompetition());
		menuCompetition.addBack("4");
		return menuCompetition;
	}
	
	private static LocalDate getLocalDate() throws IOException
	{
		final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final LocalDate localDate = LocalDate.parse(getString(), DATE_FORMAT);
		return localDate;
	}
	
	public static LocalDate getLocalDate(String message)
	{
		do
		{
			System.out.print(message);
			try
			{
				return getLocalDate();
			}
			catch(Exception e)
			{
				System.out.println("Erreur de saisie !");
			}
		}
		while(true);
	}
	
	public static Object getBoolean() throws IOException
	{
		final String bool = getString();
		
		if(bool.equals("oui"))
			return true;
		else if(bool.equals("non"))
			return false;
		return bool;
	}
	
	public static boolean getBoolean(String message)
	{
		do
		{
			System.out.print(message);
			try
			{			
				return (boolean)getBoolean();
			}
			catch(Exception e)
			{
				System.out.println("Veuillez saisir 'oui' ou 'non'!");
			}
		}
		while(true);
	}
	
	private Option afficherCompetition()
	{
		return new Option("Afficher les competitions", "1", () -> {System.out.println(inscriptions.getCompetitions());});
	}
	
	private Option ajouterCompetition()
	{
		return new Option("Ajouter une competition", "2", () -> inscriptions.createCompetition(getString("Nom : "), getLocalDate("Date : "), (boolean)getBoolean("En equipe (oui/non) ? ")));
	}
	
	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("Selectionner une Competition", "3", 
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> editerCompetition(element)
				);
	}
	
	private Menu editerCompetition(Competition competition)
	{
		Menu menu = new Menu("Editer " + competition.getNom());
		menu.add(afficher(competition));
		menu.add(modifierNom(competition));
		menu.add(modifierDateCloture(competition));
		menu.add(supprimer(competition));
		menu.add(afficherCandidat(competition));
		menu.add(ajouterCandidat(competition));
		menu.add(supprimerCandidat(competition));
		menu.addBack("8");
		return menu;
	}
	
	private Option afficher(Competition competition)
	{
		return new Option("Afficher la competition", "1", 
				() -> 
				{
					System.out.println("Nom : " + competition.getNom());
					System.out.println("Date de cloture : " + competition.getDateCloture());
					System.out.println("en equipe : : " + competition.estEnEquipe());
				}
		);
	}
	
	private Option modifierNom(Competition competition)
	{
		return new Option("Modifier le nom", "2", 
				() -> {competition.setNom(getString("Nouveau nom : "));});
	}
	
	private Option modifierDateCloture(Competition competition)
	{
		return new Option("Modifier la date de cloture", "3", 
				() -> {competition.setDateCloture(getLocalDate("Nouvelle date de cloture : "));});
	}
	
	private Option supprimer(Competition competition)
	{
		return new Option("Supprimer", "4", () -> {competition.delete();});
	}
	
	private Option afficherCandidat(Competition competition)
	{
		return new Option("Afficher les candidats", "5", () -> {System.out.println(competition.getCandidats());});
	}
	
	private Option ajouterCandidat(final Competition competition)
	{	
		LocalDate dateSysteme = LocalDate.now();
		
		if(competition.estEnEquipe() == true && dateSysteme.isBefore(competition.getDateCloture()))
			return ajouterEquipe(competition);
		else if(competition.estEnEquipe() == false && dateSysteme.isBefore(competition.getDateCloture()))
			return ajouterPersonne(competition);
		else
			return compareDate(competition);
	}
	
	private List<Equipe> ajouterEquipe(final Competition competition)
	{
		return new List<>("Ajouter une equipe", "6", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private List<Personne> ajouterPersonne(final Competition competition)
	{
		return new List<>("Ajouter une personne", "6", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private Menu compareDate(Competition competition)
	{
		Menu compareDate = new Menu("On ne peut plus s'inscrire !", "6");
		compareDate.addBack("1");
		return compareDate;
	}
	
	private List<Candidat> supprimerCandidat(final Competition competition)
	{
		return new List<>("Supprimer un membre", "7", 
				() -> new ArrayList<>(inscriptions.getCandidats()),
				(index, element) -> {competition.remove(element);}
				);
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
		menuEquipe.add(afficherEquipe());
		menuEquipe.add(ajouterEquipe());
		menuEquipe.add(selectionnerEquipe());
		menuEquipe.addBack("4");
		return menuEquipe;
	}
	
	private Option afficherEquipe()
	{
		return new Option("Afficher les equipes", "1", () -> {System.out.println(inscriptions.getEquipes());});
	}
	
	private Option ajouterEquipe()
	{
		return new Option("Ajouter une equipe", "2", () -> inscriptions.createEquipe(getString("Nom : ")));
	}
	
	private List<Equipe> selectionnerEquipe()
	{
		return new List<Equipe>("Selectionner une equipe", "3", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> editerEquipe(element)
				);
	}
	
	private Menu editerEquipe(Equipe equipe)
	{
		Menu menu = new Menu("Editer " + equipe.getNom());
		menu.add(afficher(equipe));
		menu.add(modifierNom(equipe));
		menu.add(supprimer(equipe));
		menu.add(afficherMembre(equipe));
		menu.add(ajouterMembre(equipe));
		menu.add(supprimerMembre(equipe));
		menu.add(afficherCompetition(equipe));
		menu.addBack("8");
		return menu;
	}
	
	private Option afficher(Equipe equipe)
	{
		return new Option("Afficher l'equipe", "1", 
				() -> 
				{
					System.out.println("Nom : " + equipe.getNom());
				}
		);
	}
	
	private Option modifierNom(Equipe equipe)
	{
		return new Option("Modifier le nom", "2", 
				() -> {equipe.setNom(getString("Nouveau nom : "));});
	}
	
	private Option supprimer(Equipe equipe)
	{
		return new Option("Supprimer", "3", () -> {equipe.delete();});
	}
	
	private Option afficherMembre(Equipe equipe)
	{
		return new Option("Afficher les membres", "4", () -> {System.out.println(equipe.getMembres());});
	}
	
	private List<Personne> ajouterMembre(final Equipe equipe)
	{
		return new List<>("Ajouter un membre", "5", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {equipe.add(element);}
				);
	}
	
	private List<Personne> supprimerMembre(final Equipe equipe)
	{
		return new List<>("Supprimer un membre", "6", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {equipe.remove(element);}
				);
	}
	
	private Option afficherCompetition(Equipe equipe)
	{
		return new Option("Afficher les competitions", "7", () -> {System.out.println(equipe.getCompetitions());});
	}
	
	
	private Menu menuPersonne()
	{
		Menu menuPersonne = new Menu("Gerer les personnes", "2");
		menuPersonne.add(afficherPersonne());
		menuPersonne.add(ajouterPersonne());
		menuPersonne.add(selectionnerPersonne());
		menuPersonne.addBack("4");	
		return menuPersonne;
	}
	
	private Option afficherPersonne()
	{
		return new Option("Afficher les personnes", "1", () -> {System.out.println(inscriptions.getPersonnes());});
	}
	
	private Option ajouterPersonne()
	{
		return new Option("Ajouter une personne", "2", () -> inscriptions.createPersonne(getString("Nom : "), getString("Prenom : "), getString("Mail : ")));
	}
	
	private List<Personne> selectionnerPersonne()
	{
		return new List<Personne>("Selectionner une personne", "3", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> editerPersonne(element)
				);
	}
	
	private Menu editerPersonne(Personne personne)
	{
		Menu menu = new Menu("Editer " + personne.getNom());
		menu.add(afficher(personne));
		menu.add(modifierNom(personne));
		menu.add(modifierPrenom(personne));
		menu.add(modifierMail(personne));
		menu.add(supprimer(personne));
		menu.add(afficherEquipe(personne));
		menu.add(ajouterEquipe(personne));
		menu.add(supprimerEquipe(personne));
		menu.add(afficherCompetition(personne));
		menu.addBack("10");
		return menu;
	}
	
	private Option afficher(Personne personne)
	{
		return new Option("Afficher la personne", "1", 
				() -> 
				{
					System.out.println("Nom : " + personne.getNom());
					System.out.println("Prenom : " + personne.getPrenom());
					System.out.println("Mail : " + personne.getMail());
				}
		);
	}
	
	private Option modifierNom(Personne personne)
	{
		return new Option("Modifier le nom", "2", 
				() -> {personne.setNom(getString("Nouveau nom : "));});
	}
	
	private Option modifierPrenom(Personne personne)
	{
		return new Option("Modifier le prenom", "3", 
				() -> {personne.setPrenom(getString("Nouveau prenom : "));});
	}
	
	private Option modifierMail (Personne personne)
	{
		return new Option("Modifier le mail", "4", 
				() -> {personne.setMail(getString("Nouveau mail : "));});
	}
	
	private Option supprimer(Personne personne)
	{
		return new Option("Supprimer", "5", () -> {personne.delete();});
	}
	
	private Option afficherEquipe(Personne personne)
	{
		return new Option("Afficher les equipes", "6", () -> {System.out.println(personne.getEquipes());});
	}
	
	private List<Equipe> ajouterEquipe(final Personne personne)
	{
		return new List<>("Ajouter une equipe", "7", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {personne.add(element);}
				);
	}
	
	private List<Equipe> supprimerEquipe(final Personne personne)
	{
		return new List<>("Supprimer une quipe", "8", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {personne.remove(element);}
				);
	}
	
	private Option afficherCompetition(Personne personne)
	{
		return new Option("Afficher les competitions", "9", () -> {System.out.println(personne.getCompetitions());});
	}
	
	public static void main(String[] args)
	{
		DialogueUtilisateur console = new DialogueUtilisateur(Inscriptions.getInscriptions());
		console.start();
	}
}
