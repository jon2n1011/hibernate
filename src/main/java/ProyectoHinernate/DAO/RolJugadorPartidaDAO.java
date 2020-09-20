package ProyectoHinernate.DAO;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;


public interface RolJugadorPartidaDAO extends GenericDAO<Roljugadorpartida,Integer> {
	

	public Roljugadorpartida findByIdUser(int id,String user);
	public List<Roljugadorpartida> findAll(int id,String user);

	
	
}
