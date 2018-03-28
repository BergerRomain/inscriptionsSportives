package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "Equipes")
	public class Equipes 
	{
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "numCandidat")
		private int numCandidat;

		@Column(name = "nomEquipes")
		private String nomEquipes;

		public Equipes(String nom)
		{
			this.nomEquipes = nom;
		}
	}

