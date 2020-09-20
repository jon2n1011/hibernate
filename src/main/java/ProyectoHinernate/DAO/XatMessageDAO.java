package ProyectoHinernate.DAO;

import java.util.List;

import ProyectoHibernate.ProyectoHibernate.Vot;
import ProyectoHibernate.ProyectoHibernate.xatMessage;

public interface XatMessageDAO extends GenericDAO<xatMessage,Integer> {
	public List<xatMessage> getXat(int id);
	public void escriuMissatge(String contenido, int idp, String username);
	public List<xatMessage> getXatLlops(int idPartida, String username); 
	public void escriuMissatgeLlop(int idPartida, String username,String contenido);
}
