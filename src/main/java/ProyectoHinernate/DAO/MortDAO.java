package ProyectoHinernate.DAO;

import java.util.List;

import ProyectoHibernate.ProyectoHibernate.Mort;
import ProyectoHibernate.ProyectoHibernate.Partida;
import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;

public interface MortDAO extends GenericDAO<Mort,Integer> {
	public List<Mort> findAllById(int id);
	public List<Mort> getMort(int id, int torn);
}
