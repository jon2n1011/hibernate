package ProyectoHinernate.DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;
import ProyectoHibernate.ProyectoHibernate.User;

public class GenericDAOImplHibernate<T, ID extends Serializable> implements GenericDAO<T, ID> {
	static SessionFactory sessionFactory;
	static ServiceRegistry serviceRegistry;
	// private final static Logger LOGGER =
	// Logger.getLogger(GenericDao.class.getName());

	public GenericDAOImplHibernate() {
		sessionFactory = getSessionFactory();
	}

	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {

			// exception handling omitted for brevityaa

			serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

			sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		}
		return sessionFactory;
	}

	@Override
	// Podemos crear metodos con genericos ya que todos los saveorupdate seran
	// iguales
	public void saveOrUpdate(T entity) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(entity);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			e.printStackTrace();

		}

	}

	@Override
	public T findById(ID id) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			T entity = (T) session.get(getEntityClass(), id);
			session.getTransaction().commit();
			return entity;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;

		}
	}

	@Override
	public void delete(ID id) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			T entity = (T) session.get(getEntityClass(), id);
			session.delete(entity);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			e.printStackTrace();

		}
	}

	@Override
	public List<T> findAll() {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("SELECT e FROM " + getEntityClass().getName() + " e");
			List<T> entities = query.list();
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
	
	
	private Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void persist(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(T entity) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			session.save(entity);
			session.getTransaction().commit();

		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			e.printStackTrace();

		}
		
	}

}
