package hibernate.inscriptionSportives;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name = "personne")
	public class Personnes
	{
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "numCandidat")
		private int numCandidat;

		@Column(name = "nomPersonne")
		private String nomPersonne;

		@Column(name = "prenomPersonne")
		private String prenomPersonne;
		
		@Column(name = "mailPersonne")
		private String mailPersonne;

		public Personnes(String prenom, String nom, String mail)
		{
			this.nomPersonne = nom;
			this.prenomPersonne = prenom;
			this.mailPersonne = mail;
		}
	}

