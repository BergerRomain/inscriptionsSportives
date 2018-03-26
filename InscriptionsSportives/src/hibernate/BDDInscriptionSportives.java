package hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

	@Entity
	@Table(name = "personne")
	class Personne
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

		public Personne(String prenom, String nom, String mail)
		{
			this.nomPersonne = nom;
			this.prenomPersonne = prenom;
			this.mailPersonne = mail;
		}
	}

public class BDDInscriptionSportives 
{
	private static Session getSession() throws HibernateException
	{
		Configuration configuration = new Configuration()
				.configure("hibernate/BDDInscriptionSportives.cfg.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		return sessionFactory.openSession();
	}

	public static void main(String[] args)
	{
		try
		{
			Session s = getSession();
			Personne Billy = new Personne("Billy", "Bob", "Billy-Bob@gmail.com");
			Transaction t = s.beginTransaction();
			s.persist(Billy);
			t.commit();
			s.close();
		}
		catch (HibernateException ex)
		{
			throw new RuntimeException("Probleme de configuration : "
					+ ex.getMessage(), ex);
		}
	}
}
