package ProyectoHinernate.DAO;

import java.util.List;

import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;
import ProyectoHibernate.ProyectoHibernate.Vot;

public interface VotDAO extends GenericDAO<Vot,Integer> {

	public List<Vot> findByMax(int id,int torn);
	public void vota(String usernameSender, String usernameReceiver, int idPartida);
	public List<Vot> gethistorial(int id, int torn, String username);
	
}
