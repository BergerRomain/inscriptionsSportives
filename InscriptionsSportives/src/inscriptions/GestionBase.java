package inscriptions;

import java.util.List;

public class GestionBase
{
	private static GestionBase inscriptions = null;
	
	public List<Personne> getPersonnes()
	{
		return BDDInscriptionSportives.refreshListPersonnes();
	}
	
	public List<Equipe> getEquipes()
	{
		return BDDInscriptionSportives.refreshListEquipes();
	}
	
	public List<Competition> getCompetitions()
	{
		return BDDInscriptionSportives.refreshListCompetitions();
	}
	
	public List<Candidat> getCandidats()
	{
		return BDDInscriptionSportives.refreshListCandidats();
	}
	
	public static GestionBase getGestionBase()
	{
		if (inscriptions == null)
			inscriptions = new GestionBase();
		return inscriptions;
	}
	
	public void sauvegarder(Personne personne)
	{
		BDDInscriptionSportives.save(personne);
	}

	public void supprimer(Personne personne)
	{
		BDDInscriptionSportives.delete(personne);
	}
	
	public void sauvegarder(Competition competition)
	{
		BDDInscriptionSportives.save(competition);
	}

	public void supprimer(Competition competition)
	{
		BDDInscriptionSportives.delete(competition);
	}
	
	public void sauvegarder(Equipe equipe)
	{
		BDDInscriptionSportives.save(equipe);
	}

	public void supprimer(Equipe equipe)
	{
		BDDInscriptionSportives.delete(equipe);
	}
}

