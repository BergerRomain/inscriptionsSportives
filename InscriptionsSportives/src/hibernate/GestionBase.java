package hibernate;

import java.util.List;

public class GestionBase
{
	private static GestionBase gestionbase = null;
	
	public List<Personnes> getPersonnes()
	{
		return BDDInscriptionSportives.refreshList();
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
}

