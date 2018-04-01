package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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

	static void delete(Personnes personne)
	{
		Transaction tx = session.beginTransaction();
		session.delete(personne);
		tx.commit();
	}

	static void save(Personnes personne)
	{
		Transaction tx = session.beginTransaction();
		session.save(personne);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	static java.util.List<Personnes> refreshListPersonnes()
	{
		Query query = session.createQuery("from Personnes");
		return query.list();
	}
	
	
	static void delete(Competitions competition)
	{
		Transaction tx = session.beginTransaction();
		session.delete(competition);
		tx.commit();
	}

	static void save(Competitions competition)
	{
		Transaction tx = session.beginTransaction();
		session.save(competition);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	static java.util.List<Competitions> refreshListCompetitions()
	{
		Query query = session.createQuery("from Personnes");
		return query.list();
	}
	
		
	static void delete(Equipes equipe)
	{
		Transaction tx = session.beginTransaction();
		session.delete(equipe);
		tx.commit();
	}

	static void save(Equipes equipe)
	{
		Transaction tx = session.beginTransaction();
		session.save(equipe);
		tx.commit();
	}

	@SuppressWarnings("unchecked")
	static java.util.List<Equipes> refreshListEquipes()
	{
		Query query = session.createQuery("from Personnes");
		return query.list();
	}
}

