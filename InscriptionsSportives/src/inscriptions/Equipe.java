package inscriptions;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Représente une Equipe. C'est-à-dire un ensemble de personnes pouvant 
 * s'inscrire à une compétition.
 * 
 */

@Entity
@Table(name = "Equipe")
public class Equipe extends Candidat
{
	private static final long serialVersionUID = 4147819927233466035L;
	@OneToMany(targetEntity=Equipe.class, mappedBy="membres", fetch=FetchType.EAGER)
	@javax.persistence.OrderBy("sort")
	private SortedSet<Personne> membres = new TreeSet<>();
	
	@Column(name = "membres")
	@OneToMany(targetEntity=Equipe.class, mappedBy="membres", fetch=FetchType.EAGER)
	@javax.persistence.OrderBy("sort")
	private SortedSet<Personne> membresEquipe;
	
	Equipe(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
		membresEquipe = new TreeSet<>();
	}
	
	public Equipe(String nomEquipes)
	{
		super(nomEquipes);
	}

	/**
	 * Retourne l'ensemble des personnes formant l'équipe.
	 */
	
	public SortedSet<Personne> getMembres()
	{
		return Collections.unmodifiableSortedSet(membres);
	}
	
	/**
	 * Ajoute une personne dans l'équipe.
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre)
	{
		membre.add(this);
		return membres.add(membre);
	}

	/**
	 * Supprime une personne de l'équipe. 
	 * @param membre
	 * @return
	 */
	
	public boolean remove(Personne membre)
	{
		membre.remove(this);
		return membres.remove(membre);
	}

	@Override
	public void delete()
	{
		super.delete();
	}
	
	@Override
	public String toString()
	{
		return "Equipe " + super.toString();
	}
}
