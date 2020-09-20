package ProyectoHinernate.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ProyectoHibernate.ProyectoHibernate.Mort;
import ProyectoHibernate.ProyectoHibernate.Partida;
import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;

public class MortDAOImplHibernate extends GenericDAOImplHibernate<Mort, Integer> implements MortDAO {

	@Override
	public List<Mort> findAllById(int id) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("SELECT r FROM Mort r where partida=" + id);
			List<Mort> entities = query.list();
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

	public List<Mort> getMort(int id, int torn) {
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(id);

		if (partidote != null) {

			List<Mort> morts = findAllById(id);
			List<Mort> mortreturn = new ArrayList();

			for (Mort muertox : morts) {

				if (muertox.getTorn() == torn) {

					mortreturn.add(muertox);

				}

			}

			return mortreturn;

		}

		else {
			System.out.println("Partida inexistente");
		}
		return null;

	}

}
