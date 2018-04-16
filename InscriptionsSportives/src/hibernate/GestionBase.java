package hibernate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;

public class GestionBase
{
	private static GestionBase inscriptions = null;
	
	public List<Personne> getPersonne()
	{
		return BDDInscriptionSportives.refreshListPersonnes();
	}
	
	public List<Equipe> getEquipe()
	{
		return BDDInscriptionSportives.refreshListEquipes();
	}
	
	public List<Competition> getCompetition()
	{
		return BDDInscriptionSportives.refreshListCompetitions();
	}
	
	public List<Candidat> getCandidat()
	{
		return BDDInscriptionSportives.refreshListCandidats();
	}
	
	public static GestionBase getGestionBase()
	{
		if (inscriptions == null)
			inscriptions = new GestionBase();
		return inscriptions;
	}
	
	public void sauvegarde(Personne personne)
	{
		BDDInscriptionSportives.save(personne);
	}

	public void supprime(Personne personne)
	{
		BDDInscriptionSportives.delete(personne);
	}
	
	public void sauvegarde(Competition competition)
	{
		BDDInscriptionSportives.save(competition);
	}

	public void supprime(Competition competition)
	{
		BDDInscriptionSportives.delete(competition);
	}
	
	public void sauvegarde(Equipe equipe)
	{
		BDDInscriptionSportives.save(equipe);
	}

	public void supprime(Equipe equipe)
	{
		BDDInscriptionSportives.delete(equipe);
	}
	
	public void sauvegarder() throws IOException
	{
		final String FILE_NAME = "Inscriptions.srz";
		ObjectOutputStream oos = null;
		try
		{
			FileOutputStream fis = new FileOutputStream(FILE_NAME);
			oos = new ObjectOutputStream(fis);
			oos.writeObject(this);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			try
			{
				if (oos != null)
					oos.close();
			} 
			catch (IOException e){}
		}
	}
}

