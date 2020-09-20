package ProyectoHinernate.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import ProyectoHibernate.ProyectoHibernate.Message;
import ProyectoHibernate.ProyectoHibernate.Partida;
import ProyectoHibernate.ProyectoHibernate.Rol;
import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;
import ProyectoHibernate.ProyectoHibernate.User;
import ProyectoHibernate.ProyectoHibernate.Vot;

public class VotDAOImplHibernate extends GenericDAOImplHibernate<Vot, Integer> implements VotDAO {

	public void vota(String usernameSender, String usernameReceiver, int idPartida) {
		UserDAO usuario = new UserDAOImplHibernate();
		User usuari1 = usuario.findById(usernameSender);
		User usuari2 = usuario.findById(usernameReceiver);
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(idPartida);

		if (usuari1 != null && usuari2 != null && partidote != null) {
			// Sacamos la lista de todos los datos de roljugadorpartida, para sacar los
			// roles de los jugadores. Cambiando las sentencias sql o overrid el metodo
			// findall pasandole la id de partida tambien serviria.
			RolJugadorPartidaDAO roljugador = new RolJugadorPartidaImplHibernate();
			ArrayList<Roljugadorpartida> rollista = (ArrayList<Roljugadorpartida>) roljugador.findAll(partidote.getId(),
					usuari1.getUserName());
			// Tendre un objeto rolpartida, para sacar el rol mas adelante
			Roljugadorpartida rolusuari = new Roljugadorpartida();
			for (Roljugadorpartida rolj : rollista) {
				rolusuari = rolj;

			}

			/*
			 * Segunda opcion, solo returna un objeto, por ahora no la usaremos debido a los
			 * posibles fallos Roljugadorpartida rol=
			 * roljugador.findByIdUser(partidote.getId(), usuari1.getUserName());
			 * 
			 * System.out.println(rol.toString());
			 */

			// Ahora tengo que sacar el ROL, entonces creo un rol.

			Rol rol = rolusuari.getRol();

			// Compruebo ahora el turno, si es par o impar y si es LOBO
			// Roles= 1 lobos, 2 vilatanos,3 videntes

			Session session = sessionFactory.getCurrentSession();
			try {
				// Noche solo votan los lobosm,
				if (partidote.getTorn() % 2 == 0) {

					if (rol.getId() == 1) {
						Vot voto = new Vot(usuari1, usuari2, partidote, partidote.getTorn());
						session.beginTransaction();
						session.save(voto);
						session.getTransaction().commit();
						System.out.println("El usuario "+usernameSender+" ha vota a "+usernameReceiver);
					}

				}
				// Es de dia, votan todos
				else {
					Vot voto = new Vot(usuari1, usuari2, partidote, partidote.getTorn());
					session.beginTransaction();
					session.save(voto);
					session.getTransaction().commit();
					System.out.println("El usuario "+usernameSender+" ha vota a "+usernameReceiver);
				}

			} catch (HibernateException e) {

				if (session != null && session.getTransaction() != null) {
					System.out.println("\n.......Transaction Is Being Rolled Back.......");
					session.getTransaction().rollback();
				}

			}
		}

		else {

			System.out.println("Usuarios incorrectos o partida inexistente");

		}

	}

	public List<Vot> gethistorial(int id, int torn, String username) {

		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(id);
		UserDAO usuarios = new UserDAOImplHibernate();
		User usuari = usuarios.findById(username);
		if (partidote != null) {
			if (partidote.getTorn() % 2 != 0) {
				List<Vot> votos = findByMax(id, torn);
				return votos;
			}

			else {

				RolJugadorPartidaDAO rol = new RolJugadorPartidaImplHibernate();

				List<Roljugadorpartida> rolj = rol.findAll(id, username);

				if (rolj.size() != 0) {
					// Si es lobo puede ver los votos cuando el turno es de noche
					if (rolj.get(0).getRol().getId() == 1) {

						List<Vot> votos = findByMax(id, torn);
						return votos;

					}
				}

				else {

					System.out.println("El jugador no se encuentra en la partida");

				}

			}

		} else

		{

			System.out.println("Partida inexistente");
			return null;
		}
		return null;

	}

	@Override
	public List<Vot> findByMax(int id, int torn) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("SELECT r  FROM Vot r where partida=" + id + " AND torn=" + torn
					+ " group by partida,receiver ORDER BY count(receiver) desc");

			List<Vot> entities = query.list();
			session.getTransaction().commit();
			return entities;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (session != null && session.getTransaction() != null) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}

		}
		return null;

	}

}
