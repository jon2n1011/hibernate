package ProyectoHinernate.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ProyectoHibernate.ProyectoHibernate.Message;
import ProyectoHibernate.ProyectoHibernate.User;

public class UserDAOImplHibernate extends GenericDAOImplHibernate<User, String> implements UserDAO {

	public boolean registre(String username, String contrasenya, String alias, String pathAvatar) {

		User usuario1 = this.findById(username);

		if (usuario1 == null) {

			Session session = sessionFactory.getCurrentSession();
			try {

				User usuario = new User(username, contrasenya, alias, pathAvatar);
				session.beginTransaction();
				session.save(usuario);
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
			System.out.println("Usuario existente");
			return false;
		}

	}

	public boolean login(String username, String contrasenya) {

		User usuario1 = this.findById(username);
		if (usuario1 == null) {

			System.out.println("Usuario No correcto");
			return false;
		}

		else {
			Session session = sessionFactory.getCurrentSession();

			try {

				if (usuario1.getPassword().equals(contrasenya)) {

					System.out.println("Login correcto");

					return true;

				} else {
					System.out.println("Contraseña no correcta");

					return false;
				}

			} catch (HibernateException e) {

				if (session != null && session.getTransaction() != null) {
					System.out.println("\n.......Transaction Is Being Rolled Back.......");
					session.getTransaction().rollback();
				}

				return false;

			}

			// Poner finally puede estar bien, para hacer el commit , si no puede surgir el
			// problema de ALREADY ACTIVE
		}

	}


	public void getMissatges(String user1) {
		UserDAO usuarios = new UserDAOImplHibernate();
		User x = usuarios.findById(user1);
		if (x != null) {
			MessageDAO mensaje = new MessageDAOImplHibernate();
			ArrayList<Message> mensajes = (ArrayList<Message>) mensaje.findAll();
			for (Message mensaje1 : mensajes) {

				if (x.getUserName().equals(mensaje1.getSender().getUserName())
						|| x.getUserName().equals(mensaje1.getReceiver().getUserName())) {

					System.out.println(mensaje1.toString());
				}

			}
		}

		else {
			System.out.println("Error, usuario inexistente");
		}
	}

}
