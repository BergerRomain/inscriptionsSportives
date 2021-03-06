package dialogueUtilisateur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commandLineMenus.*;
import hibernate.GestionBase;
import inscriptions.*;

import static commandLineMenus.rendering.examples.util.InOut.*;

public class DialogueUtilisateur
{
	private static final Exception IOException = null;
	private GestionBase inscriptions;
	
	public DialogueUtilisateur(GestionBase inscriptions)
	{
		this.inscriptions = inscriptions;
		inscriptionMenu().start();
	}
	
	private Menu inscriptionMenu()
	{
		Menu inscriptionMenu = new Menu("Inscriptions sportives");
		inscriptionMenu.add(Inscription());
		inscriptionMenu.add(quitter());
		return inscriptionMenu;
	}
	
	private Option quitter()
	{
		return new Option("Quitter", "q", Action.QUIT);
	}
	
	private Menu Inscription()
	{
		Menu Inscription = new Menu("Inscription", "i");
		Inscription.add(menuCompetition());
		Inscription.add(candidat());
		Inscription.addBack("Retour", "r");
		return Inscription;
	}
	
	private Menu menuCompetition()
	{
		Menu menuCompetition = new Menu("Gerer les competitions", "c");
		menuCompetition.add(afficherCompetition());
		menuCompetition.add(ajouterCompetition());
		menuCompetition.add(modifierCompetition());
		menuCompetition.add(supprimerCompetition());
		menuCompetition.add(selectionnerCompetition());
		menuCompetition.addBack("Retour", "r");
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
		return IOException;
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
		return new Option("Afficher les competitions", "a", () -> {System.out.println(inscriptions.getCompetition());});
	}
	
	private Option ajouterCompetition()
	{
		return new Option("Ajouter une competition", "j", () -> { 			
			inscriptions.sauvegarde(new Competition(getString("Nom : "), getLocalDate("Date de cloture : "), (boolean)getBoolean("En �quipes (oui/non) : ")));});
	}
	
	private Option modifierCompetition()
	{
		return new List<>("Modifier une competition", "m",
				() -> inscriptions.getCompetition(),
				(indice, element) -> 
				{
					element.setNom(getString("Nom : "));
					element.setDateCloture(getLocalDate("Date de cloture : "));
					inscriptions.sauvegarde(element);
				});
	}
	
	private Option supprimerCompetition()
	{
		return new List<>("Supprimer une competition", "s",
				() -> inscriptions.getCompetition(),
				(indice, element) -> 
					{
						inscriptions.supprime(element);
					}
				);
	}
	
	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("Selectionner une competition", "c", 
				() -> new ArrayList<>(inscriptions.getCompetition()),
				(element) -> editerCompetition(element)
				);
	}
	
	private Menu editerCompetition(Competition competition)
	{
		Menu menu = new Menu("Editer " + competition.getNom());
		menu.add(afficher(competition));
		menu.add(modifierCandidat(competition));
		menu.addBack("Retour", "r");
		return menu;
	}
	
	private Option afficher(Competition competition)
	{
		return new Option("Afficher la competition", "a", 
				() -> 
				{
					System.out.println("Nom : " + competition.getNom());
					System.out.println("Date de cloture : " + competition.getDateCloture());
					System.out.println("en equipe : : " + competition.estEnEquipe());
				}
		);
	}
	
	private Menu modifierCandidat(Competition competition)
	{
		Menu menu = new Menu("Modifier les candidats", "m");
		menu.add(afficherCandidat(competition));
		menu.add(ajouterCandidat(competition));
		menu.add(supprimerCandidat(competition));
		menu.addBack("Retour", "r");
		return menu;
	}
	
	private Option afficherCandidat(Competition competition)
	{
		return new Option("Afficher les candidats", "a", () -> {System.out.println(competition.getCandidats());});
	}
	
	private Option ajouterCandidat(final Competition competition)
	{	
		LocalDate dateSysteme = LocalDate.now();
		
		if(competition.estEnEquipe() && dateSysteme.isBefore(competition.getDateCloture()))
			return ajouterEquipe(competition);
		else if(!competition.estEnEquipe() && dateSysteme.isBefore(competition.getDateCloture()))
			return ajouterPersonne(competition);
		else
			return compareDate(competition);
	}
	
	private List<Equipe> ajouterEquipe(final Competition competition)
	{
		return new List<>("Ajouter une equipe", "j", 
				() -> new ArrayList<>(inscriptions.getEquipe()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private List<Personne> ajouterPersonne(final Competition competition)
	{
		return new List<>("Ajouter une personne", "j", 
				() -> new ArrayList<>(inscriptions.getPersonne()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private Menu compareDate(Competition competition)
	{
		Menu compareDate = new Menu("On ne peut plus s'inscrire !", "j");
		compareDate.addBack("Retour", "r");
		return compareDate;
	}
	
	private List<Candidat> supprimerCandidat(final Competition competition)
	{
		return new List<>("Supprimer un candidat", "s", 
				() -> new ArrayList<>(inscriptions.getCandidat()),
				(index, element) -> {competition.remove(element);}
				);
	}

	private Menu candidat()
	{
		Menu candidat = new Menu("Gerer les candidats", "g");
		candidat.add(menuEquipe());
		candidat.add(menuPersonne());
		candidat.addBack("Retour", "r");
		return candidat;
	}
	
	private Menu menuEquipe()
	{
		Menu menuEquipe = new Menu("Gerer les equipes", "e");
		menuEquipe.add(afficherEquipe());
		menuEquipe.add(ajouterEquipe());
		menuEquipe.add(modifierEquipe());
		menuEquipe.add(supprimerEquipe());
		menuEquipe.add(selectionnerEquipe());
		menuEquipe.addBack("Retour", "r");
		return menuEquipe;
	}
	
	private Option afficherEquipe()
	{
		return new Option("Afficher les equipes", "a", () -> {System.out.println(inscriptions.getEquipe());});
	}
	
	private Option ajouterEquipe()
	{
		return new Option("Ajouter une equipe", "j", () -> { 			
			inscriptions.sauvegarde(new Equipe(getString("Nom : ")));});
	}
	
	private Option modifierEquipe()
	{
		return new List<>("Modifier", "m",
				() -> inscriptions.getEquipe(),
				(indice, element) -> 
				{
					element.setNom(getString("Nom : "));
					inscriptions.sauvegarde(element);
				}
			);
	}
	
	private Option supprimerEquipe()
	{
		return new List<>("Supprimer une equipe", "s",
				() -> inscriptions.getEquipe(),
				(indice, element) -> 
					{
						inscriptions.supprime(element);
					}
				);
	}
	
	private List<Equipe> selectionnerEquipe()
	{
		return new List<Equipe>("Selectionner une equipe", "c", 
				() -> new ArrayList<>(inscriptions.getEquipe()),
				(element) -> editerEquipe(element)
				);
	}
	
	private Menu editerEquipe(Equipe equipe)
	{
		Menu menu = new Menu("Editer " + equipe.getNom());
		menu.add(afficher(equipe));
		menu.add(modifierMembres(equipe));
		menu.add(afficherCompetition(equipe));
		menu.addBack("Retour", "r");
		return menu;
	}
	
	private Option afficher(Equipe equipe)
	{
		return new Option("Afficher l'equipe", "a", 
				() -> 
				{
					System.out.println("Nom : " + equipe.getNom());
				}
		);
	}
	
	private Menu modifierMembres(Equipe equipe)
	{
		Menu menu = new Menu("Modifier les membres", "m");
		menu.add(afficherMembre(equipe));
		menu.add(ajouterMembre(equipe));
		menu.add(supprimerMembre(equipe));
		menu.addBack("Retour", "r");
		return menu;
	}
	
	private Option afficherMembre(Equipe equipe)
	{
		return new Option("Afficher les membres", "a", () -> {System.out.println(equipe.getMembres());});
	}
	
	private List<Personne> ajouterMembre(final Equipe equipe)
	{
		return new List<>("Ajouter un membre", "j", 
				() -> new ArrayList<>(inscriptions.getPersonne()),
				(index, element) -> {equipe.add(element);}
				);
	}
	
	private List<Personne> supprimerMembre(final Equipe equipe)
	{
		return new List<>("Supprimer un membre", "s", 
				() -> new ArrayList<>(inscriptions.getPersonne()),
				(index, element) -> {equipe.remove(element);}
				);
	}
	
	private Option afficherCompetition(Equipe equipe)
	{
		return new Option("Afficher les competitions", "c", () -> {System.out.println(equipe.getCompetitions());});
	}
	
	
	private Menu menuPersonne()
	{
		Menu menuPersonne = new Menu("Gerer les personnes", "p");
		menuPersonne.add(afficherPersonne());
		menuPersonne.add(ajouterPersonne());
		menuPersonne.add(modifierPersonne());
		menuPersonne.add(supprimerPersonne());
		menuPersonne.add(selectionnerPersonne());
		menuPersonne.addBack("Retour", "r");	
		return menuPersonne;
	}
	
	private Option afficherPersonne()
	{
		return new Option("Afficher les personnes", "a", () -> {System.out.println(inscriptions.getPersonne());});
	}
	
	private Option ajouterPersonne()
	{
		return new Option("Ajouter une personne", "j", () -> 
		{
			inscriptions.sauvegarde(new Personne(getString("Nom : "), 
					getString("Prenom : "), getString("Mail : ")));
		});
	}
	
	private Option modifierPersonne()
	{
		return new List<>("Modifier", "m",
				() -> inscriptions.getPersonne(),
				(indice, element) -> 
					{
						element.setNom(getString("Nom : "));
						element.setPrenom(getString("Prenom : "));
						element.setPrenom(getString("Mail : "));
						inscriptions.sauvegarde(element);
				}
			);
	}
	
	private Option supprimerPersonne()
	{
		return new List<>("Supprimer une personne", "s",
				() -> inscriptions.getPersonne(),
				(indice, element) -> 
					{
						inscriptions.supprime(element);
					}
				);
	}
	
	private List<Personne> selectionnerPersonne()
	{
		return new List<Personne>("Selectionner une personne", "c", 
				() -> new ArrayList<>(inscriptions.getPersonne()),
				(element) -> editerPersonne(element)
				);
	}
	
	private Menu editerPersonne(Personne element)
	{
		Menu menu = new Menu("Editer " + element.getNom());
		menu.add(afficher(element));
		menu.add(modifierEquipes(element));
		menu.add(afficherCompetition(element));
		menu.addBack("Retour", "r");
		return menu;
	}
	
	private Option afficher(Personne personne)
	{
		return new Option("Afficher la personne", "a", 
				() -> 
				{
					System.out.println("Nom : " + personne.getNom());
					System.out.println("Prenom : " + personne.getPrenom());
					System.out.println("Mail : " + personne.getMail());
				}
		);
	}
	
	private Menu modifierEquipes(Personne personne)
	{
		Menu menu = new Menu("Modifier les equipes", "m");
		menu.add(afficherEquipe(personne));
		menu.add(ajouterEquipe(personne));
		menu.add(supprimerEquipe(personne));
		menu.addBack("Retour", "r");
		return menu;
	}
	
	private Option afficherEquipe(Personne personne)
	{
		return new Option("Afficher les equipes", "a", () -> {System.out.println(personne.getEquipes());});
	}
	
	private List<Equipe> ajouterEquipe(final Personne personne)
	{
		return new List<>("Ajouter une equipe", "j", 
				() -> new ArrayList<>(inscriptions.getEquipe()),
				(index, element) -> {personne.add(element);}
				);
	}
	
	private List<Equipe> supprimerEquipe(final Personne personne)
	{
		return new List<>("Supprimer une equipe", "s", 
				() -> new ArrayList<>(inscriptions.getEquipe()),
				(index, element) -> {personne.remove(element);}
				);
	}
	
	private Option afficherCompetition(Personne personne)
	{
		return new Option("Afficher les competitions", "c", () -> {System.out.println(personne.getCompetitions());});
	}
	
	public static void main(String[] args)
	{
		new DialogueUtilisateur(GestionBase.getGestionBase());
	}
}
