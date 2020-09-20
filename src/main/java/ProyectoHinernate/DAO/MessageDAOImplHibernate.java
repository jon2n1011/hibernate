package ProyectoHinernate.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ProyectoHibernate.ProyectoHibernate.Message;
import ProyectoHibernate.ProyectoHibernate.User;
import ProyectoHibernate.ProyectoHibernate.tipus;

public class MessageDAOImplHibernate extends GenericDAOImplHibernate<Message, Integer> implements MessageDAO {

	public boolean enviaMissatge(String usernameSender, String usernameReceiver, tipus type, String content) {
		UserDAO usuario = new UserDAOImplHibernate();
		User usuari = usuario.findById(usernameSender);
		User usuari2 = usuario.findById(usernameReceiver);

		if (usuari != null && usuari2 != null) {
			Message mensaje = new Message();
			mensaje.setType(type);
			mensaje.setContent(content);
			mensaje.setSender(usuari);
			mensaje.setReceiver(usuari2);
			Session session = sessionFactory.getCurrentSession();
			try {
				
				session.beginTransaction();
				session.save(mensaje);
				session.getTransaction().commit();
				return true;

			} catch (HibernateException e) {

				if (session != null && session.getTransaction() != null) {
					System.out.println("\n.......Transaction Is Being Rolled Back.......");
					session.getTransaction().rollback();
				}

				return false;

			}
		}

		else {
			System.out.println("Usuario inexistente");
			return false;
		}

	}
	
	

}
