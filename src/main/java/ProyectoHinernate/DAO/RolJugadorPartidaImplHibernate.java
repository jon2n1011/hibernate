package ProyectoHinernate.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;
import ProyectoHibernate.ProyectoHibernate.User;

public class RolJugadorPartidaImplHibernate extends GenericDAOImplHibernate<Roljugadorpartida, Integer>
		implements RolJugadorPartidaDAO {

	@Override
	public Roljugadorpartida findByIdUser(int id, String user) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(
					"SELECT r FROM Roljugadorpartida r where id_partida=" + id + " AND user LIKE '" + user + "'");
			Roljugadorpartida entities = (Roljugadorpartida) query.getSingleResult();
			session.getTransaction().commit();
			return entities;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}

		}
		return null;

	}

	// Aun por concretar
	public List<Roljugadorpartida> findAll(int id_partida, String user) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("SELECT r FROM Roljugadorpartida r where id_partida=" + id_partida
					+ " AND user LIKE '" + user + "'");
			List<Roljugadorpartida> entities = query.list();
			session.getTransaction().commit();
			return entities;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			return null;

		}
	}
	


}
