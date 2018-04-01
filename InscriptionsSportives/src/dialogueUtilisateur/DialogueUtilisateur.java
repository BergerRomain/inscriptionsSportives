package dialogueUtilisateur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import commandLineMenus.*;
import commandLineMenus.rendering.examples.util.InOut;
import inscriptions.*;
import hibernate.*;

import static commandLineMenus.rendering.examples.util.InOut.*;

public class DialogueUtilisateur 
{
	private static final Exception IOException = null;
	private Inscriptions inscriptions;
	GestionBase gestionbase;
	
	public DialogueUtilisateur(Inscriptions inscriptions, GestionBase gestionbase)
	{
		this.inscriptions = inscriptions;
		this.gestionbase = gestionbase;
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
		Menu quitter = new Menu("Quitter", "q");
		quitter.add(quitterEtEnregistrer());
		quitter.add(quitterSansEnregistrer());
		quitter.addBack("q");
		return quitter;
	}
	
	private Option quitterEtEnregistrer()
	{
		return new Option("Quitter et enregistrer", "e", 
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
		return new Option("Quitter sans enregistrer", "s", Action.QUIT);
	}
	
	private Menu Inscription()
	{
		Menu Inscription = new Menu("Inscription", "i");
		Inscription.add(menuCompetition());
		Inscription.add(candidat());
		Inscription.addBack("q");
		return Inscription;
	}
	
	private Menu menuCompetition()
	{
		Menu menuCompetition = new Menu("Gerer les competitions", "c");
		menuCompetition.add(afficherCompetition());
		menuCompetition.add(ajouterCompetition());
		menuCompetition.add(selectionnerCompetition());
		menuCompetition.addBack("q");
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
		return new Option("Afficher les competitions", "a", () -> {System.out.println(inscriptions.getCompetitions());});
	}
	
	private Option ajouterCompetition()
	{
		return new Option("Ajouter une competition", "j", () -> inscriptions.createCompetition(getString("Nom : "), getLocalDate("Date : "), (boolean)getBoolean("En equipe (oui/non) ? ")));
	}
	
	private List<Competition> selectionnerCompetition()
	{
		return new List<Competition>("Selectionner une Competition", "s", 
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> editerCompetition(element)
				);
	}
	
	private Menu editerCompetition(Competition competition)
	{
		Menu menu = new Menu("Editer " + competition.getNom());
		menu.add(afficher(competition));
		menu.add(modifierCompetition(competition));
		menu.add(supprimer(competition));
		menu.add(modifierCandidat(competition));
		menu.addBack("q");
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
	
	private Menu modifierCompetition(Competition competition)
	{
		Menu menu = new Menu("Modifier la competition", "m");
		menu.add(modifierNom(competition));
		menu.add(modifierDateCloture(competition));
		menu.addBack("q");
		return menu;
	}
	
	private Option modifierNom(Competition competition)
	{
		return new Option("Modifier le nom", "n", 
				() -> {competition.setNom(getString("Nouveau nom : "));});
	}
	
	private Option modifierDateCloture(Competition competition)
	{
		return new Option("Modifier la date de cloture", "d", 
				() -> {competition.setDateCloture(getLocalDate("Nouvelle date de cloture : "));});
	}
	
	private Option supprimer(Competition competition)
	{
		return new Option("Supprimer", "s", () -> {competition.delete();});
	}
	
	private Menu modifierCandidat(Competition competition)
	{
		Menu menu = new Menu("Modifier le candidat", "o");
		menu.add(afficherCandidat(competition));
		menu.add(ajouterCandidat(competition));
		menu.add(supprimerCandidat(competition));
		menu.addBack("q");
		return menu;
	}
	
	private Option afficherCandidat(Competition competition)
	{
		return new Option("Afficher les candidats", "c", () -> {System.out.println(competition.getCandidats());});
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
		return new List<>("Ajouter une equipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private List<Personne> ajouterPersonne(final Competition competition)
	{
		return new List<>("Ajouter une personne", "e", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {competition.add(element);}
				);
	}
	
	private Menu compareDate(Competition competition)
	{
		Menu compareDate = new Menu("On ne peut plus s'inscrire !", "e");
		compareDate.addBack("q");
		return compareDate;
	}
	
	private List<Candidat> supprimerCandidat(final Competition competition)
	{
		return new List<>("Supprimer un candidat", "s", 
				() -> new ArrayList<>(inscriptions.getCandidats()),
				(index, element) -> {competition.remove(element);}
				);
	}

	private Menu candidat()
	{
		Menu candidat = new Menu("Gerer les candidats", "g");
		candidat.add(menuEquipe());
		candidat.add(menuPersonne());
		candidat.addBack("q");
		return candidat;
	}
	
	private Menu menuEquipe()
	{
		Menu menuEquipe = new Menu("Gerer les equipes", "e");
		menuEquipe.add(afficherEquipe());
		menuEquipe.add(ajouterEquipe());
		menuEquipe.add(selectionnerEquipe());
		menuEquipe.addBack("q");
		return menuEquipe;
	}
	
	private Option afficherEquipe()
	{
		return new Option("Afficher les equipes", "a", () -> {System.out.println(inscriptions.getEquipes());});
	}
	
	private Option ajouterEquipe()
	{
		return new Option("Ajouter une equipe", "e", () -> inscriptions.createEquipe(getString("Nom : ")));
	}
	
	private List<Equipe> selectionnerEquipe()
	{
		return new List<Equipe>("Selectionner une equipe", "s", 
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
		menu.add(modifierMembres(equipe));
		menu.add(afficherCompetition(equipe));
		menu.addBack("q");
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
	
	private Option modifierNom(Equipe equipe)
	{
		return new Option("Modifier le nom", "m", 
				() -> {equipe.setNom(getString("Nouveau nom : "));});
	}
	
	private Option supprimer(Equipe equipe)
	{
		return new Option("Supprimer", "s", () -> {equipe.delete();});
	}
	
	private Menu modifierMembres(Equipe equipe)
	{
		Menu menu = new Menu("Editer les membres", "e");
		menu.add(afficherMembre(equipe));
		menu.add(ajouterMembre(equipe));
		menu.add(supprimerMembre(equipe));
		menu.addBack("q");
		return menu;
	}
	
	private Option afficherMembre(Equipe equipe)
	{
		return new Option("Afficher les membres", "a", () -> {System.out.println(equipe.getMembres());});
	}
	
	private List<Personne> ajouterMembre(final Equipe equipe)
	{
		return new List<>("Ajouter un membre", "m", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(index, element) -> {equipe.add(element);}
				);
	}
	
	private List<Personne> supprimerMembre(final Equipe equipe)
	{
		return new List<>("Supprimer un membre", "s", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
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
		menuPersonne.add(selectionnerPersonne());
		menuPersonne.addBack("q");	
		return menuPersonne;
	}
	
	private Option afficherPersonne()
	{
		return new Option("Afficher les personnes", "a", () -> {
			for (Personnes personnes : gestionbase.getPersonnes())
				System.out.println(personnes);});
	}
	
	private Option ajouterPersonne()
	{
		return new Option("Ajouter une personne", "p", () -> 
		{
			gestionbase.sauvegarder(new Personnes(InOut.getString("Nom : "), 
					InOut.getString("Prenom : "), InOut.getString("Mail : ")));
		});
	}
	
	private List<Personne> selectionnerPersonne()
	{
		return new List<Personne>("Selectionner une personne", "s", 
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> editerPersonne(element)
				);
	}
	
	private Menu editerPersonne(Personne personne)
	{
		Menu menu = new Menu("Editer " + personne.getNom());
		menu.add(afficher(personne));
		menu.add(modifierPersonne(personne));
		menu.add(supprimer(personne));
		menu.add(modifierEquipes(personne));
		menu.add(afficherCompetition(personne));
		menu.addBack("q");
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
	
	private Menu modifierPersonne(Personne personne)
	{
		Menu menu = new Menu("Modifier " + personne.getNom(), "m");
		menu.add(modifierNom(personne));
		menu.add(modifierPrenom(personne));
		menu.add(modifierMail(personne));
		menu.addBack("q");
		return menu;
	}
	
	private Option modifierNom(Personne personne)
	{
		return new Option("Modifier le nom", "n", 
				() -> {personne.setNom(getString("Nouveau nom : "));});
	}
	
	private Option modifierPrenom(Personne personne)
	{
		return new Option("Modifier le prenom", "p", 
				() -> {personne.setPrenom(getString("Nouveau prenom : "));});
	}
	
	private Option modifierMail (Personne personne)
	{
		return new Option("Modifier le mail", "m", 
				() -> {personne.setMail(getString("Nouveau mail : "));});
	}
	
	private Option supprimer(Personne personne)
	{
		return new Option("Supprimer", "s", () -> {personne.delete();});
	}
	
	private Menu modifierEquipes(Personne personne)
	{
		Menu menu = new Menu("Modifier les equipes", "l");
		menu.add(afficherEquipe(personne));
		menu.add(ajouterEquipe(personne));
		menu.add(supprimerEquipe(personne));
		menu.addBack("q");
		return menu;
	}
	
	private Option afficherEquipe(Personne personne)
	{
		return new Option("Afficher les equipes", "a", () -> {System.out.println(personne.getEquipes());});
	}
	
	private List<Equipe> ajouterEquipe(final Personne personne)
	{
		return new List<>("Ajouter une equipe", "e", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {personne.add(element);}
				);
	}
	
	private List<Equipe> supprimerEquipe(final Personne personne)
	{
		return new List<>("Supprimer une equipe", "s", 
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(index, element) -> {personne.remove(element);}
				);
	}
	
	private Option afficherCompetition(Personne personne)
	{
		return new Option("Afficher les competitions", "c", () -> {System.out.println(personne.getCompetitions());});
	}
	
	public static void main(String[] args)
	{
		new DialogueUtilisateur(Inscriptions.getInscriptions(), GestionBase.getGestionBase());
	}
}
