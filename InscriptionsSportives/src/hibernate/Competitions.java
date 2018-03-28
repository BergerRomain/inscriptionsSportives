package hibernate;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Competitions")
public class Competitions
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "numCompetition")
	private int numCompetition;

	@Column(name = "nomCompetition")
	private String nomCompetition;

	@Column(name = "dateCloture")
	private LocalDate dateCloture;
	
	@Column(name = "enEquipe")
	private boolean enEquipe;

	public Competitions(String nom, LocalDate dateCloture, boolean enEquipe)
	{
		this.nomCompetition = nom;
		this.dateCloture = dateCloture;
		this.enEquipe = enEquipe;
	}
}

