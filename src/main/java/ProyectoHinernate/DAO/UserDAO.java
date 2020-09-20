package ProyectoHinernate.DAO;
 
import java.util.List;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import ProyectoHibernate.ProyectoHibernate.User;
 
 
public interface UserDAO extends GenericDAO<User, String> {
	public boolean registre(String username, String contrasenya, String alias, String pathAvatar);
	public boolean login(String username, String contrasenya);
	public void getMissatges(String user1);
}