package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Personne;

abstract class BDDInscriptionSportives
{
	private static Session session = null;

	static
	{
		SessionFactory sessionFactory = null;
		try
		{
			Configuration configuration = new Configuration()
					.configure("hibernate/BDDInscriptionSportives.cfg.xml");
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			session = sessionFactory.openSession();
		}
		catch (HibernateException ex)
		{
			throw new RuntimeException("Probleme de configuration : "
					+ ex.getMessage(), ex);
		}
	}

	static void delete(Personne personne)
	{
		Transaction tx = session.beginTransaction();
		session.delete(personne);
		tx.commit();
	}

	static void save(Personne personne)
	{
		Transaction tx = session.beginTransaction();
		session.save(personne);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	static java.util.List<Personne> refreshListPersonnes()
	{
		Query query = session.createQuery("from Personne");
		return query.list();
	}
	
	
	static void delete(Competition competition)
	{
		Transaction tx = session.beginTransaction();
		session.delete(competition);
		tx.commit();
	}

	static void save(Competition competition)
	{
		Transaction tx = session.beginTransaction();
		session.save(competition);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	static java.util.List<Competition> refreshListCompetitions()
	{
		Query query = session.createQuery("from Competition");
		return query.list();
	}
	
		
	static void delete(Equipe equipe)
	{
		Transaction tx = session.beginTransaction();
		session.delete(equipe);
		tx.commit();
	}

	static void save(Equipe equipe)
	{
		Transaction tx = session.beginTransaction();
		session.save(equipe);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	static java.util.List<Equipe> refreshListEquipes()
	{
		Query query = session.createQuery("from Equipe");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	static java.util.List<Candidat> refreshListCandidats()
	{
		Query query = session.createQuery("from Candidat");
		return query.list();
	}
	
	static void delete(Candidat candidat)
	{
		Transaction tx = session.beginTransaction();
		session.delete(candidat);
		tx.commit();
	}
}

