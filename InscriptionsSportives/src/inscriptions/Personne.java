package inscriptions;

import java.util.Collections;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

/**
 * Représente une personne physique pouvant s'inscrire à une compétition.
 */

@Entity
@Table(name = "personne")
public class Personne extends Candidat
{
	private static final long serialVersionUID = 4434646724271327254L;
	@Column(name = "prenomPersonne")
	private String prenom;
	@Column(name = "mailPersonne")
	private String mail;
	@ManyToMany(targetEntity=Equipe.class, mappedBy="membres", fetch=FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL })
	@SortNatural
	private Set<Equipe> equipes;

	Personne(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}
	
	public Personne(String nom, String prenom, String mail)
	{
		super(nom);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}

	/**
	 * Retourne le prénom de la personne.
	 * @return
	 */
	
	public String getPrenom()
	{
		return prenom;
	}

	/**
	 * Modifie le prénom de la personne.
	 * @param prenom
	 */
	
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Retourne l'adresse électronique de la personne.
	 * @return
	 */
	
	public String getMail()
	{
		return mail;
	}

	/**
	 * Modifie l'adresse électronique de la personne.
	 * @param mail
	 */
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retoure les équipes dont cette personne fait partie.
	 * @return
	 */
	
	public Set<Equipe> getEquipes()
	{
		return Collections.unmodifiableSet(equipes);
	}
	
	public boolean add(Equipe equipe)
	{
		return equipes.add(equipe);
	}

	public boolean remove(Equipe equipe)
	{
		return equipes.remove(equipe);
	}
	
	@Override
	public void delete()
	{
		super.delete();
		for (Equipe e : equipes)
			e.remove(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " membre de " + equipes.toString();
	}
}
