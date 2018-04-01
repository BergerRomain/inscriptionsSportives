package hibernate;

import java.util.List;

public class GestionBase
{
	private static GestionBase gestionbase = null;
	
	public List<Personnes> getPersonnes()
	{
		return BDDInscriptionSportives.refreshListPersonnes();
	}
	
	public List<Equipes> getEquipes()
	{
		return BDDInscriptionSportives.refreshListEquipes();
	}
	
	public List<Competitions> getCompetitions()
	{
		return BDDInscriptionSportives.refreshListCompetitions();
	}
	
	public static GestionBase getGestionBase()
	{
		if (gestionbase == null)
			gestionbase = new GestionBase();
		return gestionbase;
	}
	
	public void sauvegarder(Personnes personne)
	{
		BDDInscriptionSportives.save(personne);
	}

	public void supprimer(Personnes personne)
	{
		BDDInscriptionSportives.delete(personne);
	}
	
	public void sauvegarder(Competitions competition)
	{
		BDDInscriptionSportives.save(competition);
	}

	public void supprimer(Competitions competition)
	{
		BDDInscriptionSportives.delete(competition);
	}
	
	public void sauvegarder(Equipes equipe)
	{
		BDDInscriptionSportives.save(equipe);
	}

	public void supprimer(Equipes equipe)
	{
		BDDInscriptionSportives.delete(equipe);
	}
}

