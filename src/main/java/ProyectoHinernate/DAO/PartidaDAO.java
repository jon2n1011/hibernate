package ProyectoHinernate.DAO;

import ProyectoHibernate.ProyectoHibernate.Message;
import ProyectoHibernate.ProyectoHibernate.Partida;

public interface PartidaDAO extends GenericDAO<Partida,Integer> {
	public void unirse(String usuari, int idpartida);
	public void inici(int idpartida);
	public void fiTorn(int idpartida);
	public void descubrirRol(int idpartida, String username, String votante); 
	public void jugadorsvius(int idpartida);
	public void rolsvius(int id);
}