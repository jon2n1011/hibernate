package ProyectoHinernate.DAO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import ProyectoHibernate.ProyectoHibernate.Message;
import ProyectoHibernate.ProyectoHibernate.Mort;
import ProyectoHibernate.ProyectoHibernate.Partida;
import ProyectoHibernate.ProyectoHibernate.Rol;
import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;
import ProyectoHibernate.ProyectoHibernate.User;
import ProyectoHibernate.ProyectoHibernate.Vot;
import ProyectoHibernate.ProyectoHibernate.tipus;

public class PartidaDAOImplHibernate extends GenericDAOImplHibernate<Partida, Integer> implements PartidaDAO {
	// Metodo con control de errores
	public void unirse(String usuari, int idpartida) {
		UserDAO usuario = new UserDAOImplHibernate();
		User usuari1 = usuario.findById(usuari);

		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(idpartida);

		if (usuari1 != null && partidote != null) {

			if (partidote.getTorn() < 1) {

				boolean existe = false;
				Set<User> usuarios = new HashSet();
				usuarios = partidote.getUsers();

				for (User usuariox : usuarios) {

					if (usuariox.getUserName().equals(usuari1.getUserName())) {

						existe = true;
						System.out.println(usuari1.toString());
					}

				}
				System.out.println(usuarios);
				if (!existe) {

					usuarios.add(usuari1);
					partidote.setUsers(usuarios);
					Session session = sessionFactory.getCurrentSession();

					try {
						session.beginTransaction();
						session.saveOrUpdate(partidote);
						session.getTransaction().commit();

						System.out.println("Usuario añadido a la partida con exito");
					} catch (HibernateException e) {

						if (session != null && session.getTransaction() != null) {
							System.out.println("\n.......Transaction Is Being Rolled Back.......");
							session.getTransaction().rollback();
						}

					}
				}

				else {

					System.out.println("El usuario ya esta dentro de esta partida");
				}

			}

			else {
				System.out.println("Partida ya empezada");
			}
		}

		else {
			System.out.println("Usuario o partida inexistente");

		}

	}

// Inicio de la partida, algoritmo con numeros random
	public void inici(int idpartida) {
		// Cogemos la partida, generamos un dao
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = new Partida();
		partidote = partida.findById(idpartida);

		if (partidote != null && partidote.getTorn() == 0) {
			// DAO GENERICO DE ROLJUGADORPARTIDA
			RolJugadorPartidaDAO roles = new RolJugadorPartidaImplHibernate();
			// Set de los usuarios que recorreremos despues
			Set<User> usuarios = new HashSet();
			usuarios = partidote.getUsers();
			Random random = new Random();
			// Sacar la frequencia de los lobos de la partida
			int llops = usuarios.size() / 4;
			ArrayList<Integer> randomsllops = new ArrayList();
			int i = 0;
			// Generamos numeros randoms.
			while (i < llops) {
				int rd = random.nextInt(usuarios.size());
				if (!randomsllops.contains(random)) {

					randomsllops.add(rd);
					i++;
				}

			}
			// Generamos el numero random del vidente
			i = 0;
			int videnternd = 0;
			while (i < 1) {
				videnternd = random.nextInt(usuarios.size());
				if (!randomsllops.contains(videnternd)) {
					i++;
				}

			}

			// Ahora iniciaremos la partida, cogemos el set de jugadores dentro de esta
			// partida y lo recorremos

			i = 0;

			// Recorremos el set incrementando la i, para ir poniendo los roles

			for (User usuario : usuarios) {
				// Cogemos los 3 roles, vidente, llop y vilatans
				RolDAO rol = new RolDAOImplHibernate();

				Rol rol1 = (Rol) rol.findById(1); // Llops
				Rol rol2 = (Rol) rol.findById(2); // Vilatans
				Rol rol3 = (Rol) rol.findById(3); // Vident

				Roljugadorpartida roljugador = new Roljugadorpartida(usuario, null, partidote, true);
				if (randomsllops.contains(i)) {

					roljugador.setRol(rol1);
				} else if (i == videnternd) {
					roljugador.setRol(rol3);
				}

				else {
					roljugador.setRol(rol2);
				}

				roles.saveOrUpdate(roljugador);
				i++;
			}
			// Ponemos el turno de la partida a uno
			partidote.setTorn(1);
			partida.saveOrUpdate(partidote);

		} else {
			System.out.println("No existe la partida, o la partida esta empezada");
		}

	}


	public void fiTorn(int idpartida) {
		// Cogemos la partida, generamos un dao
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = new Partida();
		partidote = partida.findById(idpartida);
		if (partidote != null && partidote.getTorn() > 0) {

			VotDAO vots = new VotDAOImplHibernate();

			List<Vot> votos = vots.findByMax(idpartida, partidote.getTorn());
			
			
			if (votos.size()>0) {
			Vot votox = votos.get(0);
			
			System.out.println(votox.getReceiver().toString());

			Session session = sessionFactory.getCurrentSession();

			try {
				session.beginTransaction();
				Mort mort = new Mort();
				mort.setTorn(partidote.getTorn());
				mort.setPartida(partidote);
				mort.setUser(votox.getReceiver());
				session.saveOrUpdate(mort);
				partidote.setTorn(partidote.getTorn() + 1);
				session.saveOrUpdate(partidote);
				session.getTransaction().commit();

				System.out.println("Ha muerto el usuario " + votox.getReceiver().toString());
			} catch (HibernateException e) {

				if (session != null && session.getTransaction() != null) {
					System.out.println("\n.......Transaction Is Being Rolled Back.......");
					session.getTransaction().rollback();
				}

			}
			}
		}

		else {

			System.out.println("Partida no iniciada");
		}
	}

	public void descubrirRol(int idpartida, String username, String votante) {
		UserDAO usuario = new UserDAOImplHibernate();
		User usuari1 = usuario.findById(username);
		User usuari2 = usuario.findById(votante);
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(idpartida);
		if (usuari1 != null && partidote != null && usuari2 != null) {
			// La noche
			RolJugadorPartidaDAO roljugador = new RolJugadorPartidaImplHibernate();
			Roljugadorpartida rol = roljugador.findByIdUser(partidote.getId(), usuari1.getUserName());
			Roljugadorpartida rol1 = roljugador.findByIdUser(partidote.getId(), usuari2.getUserName());
			Rol rolusuari1 = rol.getRol();
			Rol rolusuari2 = rol1.getRol();
			if (partidote.getTorn() % 2 == 0) {
				// If rol1.getid== rol id de vidente
				if (rolusuari1.getId() == 3) {

					if (usuari1.getUserName().equals(usuari2.getUserName())) {

						System.out.println("Los dos usuarios introducidos son iguales error");

					}

					else {

						System.out.println("El rol es " + rolusuari2.getDescripcio());

					}

				}

			}

		}

		else {
			System.out.println("Usuario o partida inexistente");

		}
	}

	public void jugadorsvius(int idpartida) {
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(idpartida);

		if (partidote != null) {
			MortDAO mort = new MortDAOImplHibernate();

			// Cogemos el set de todos los usuarios de la partida
			ArrayList<User> usuariosvivos = new ArrayList();
			Set<User> usuarios = new HashSet();
			usuarios = partidote.getUsers();
			List<Mort> muertos = mort.findAllById(idpartida);
			// Boolean para saber si esta en la lista de muertos
			boolean muerto = false;
			for (User usuarix : usuarios) {

				for (Mort muertox : muertos) {

					if (usuarix.getUserName().equals(muertox.getUser().getUserName())) {
						muerto = true;

					}

				}

				if (!muerto) {
					usuariosvivos.add(usuarix);
				}
				muerto = false;

			}

			for (User usuarix : usuariosvivos) {

				System.out.println(usuarix.toString());
			}

		}

		else {

			System.out.println("No existe la partida");

		}

	}

	public void rolsvius(int id) {
		int llops=0;
		int vilatans=0;
		int videntes=0;
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(id);
		if (partidote != null) {
			MortDAO mort = new MortDAOImplHibernate();

			// Cogemos el set de todos los usuarios de la partida
			ArrayList<User> usuariosvivos = new ArrayList();
			Set<User> usuarios = new HashSet();
			usuarios = partidote.getUsers();
			List<Mort> muertos = mort.findAllById(id);			
			// Boolean para saber si esta en la lista de muertos
			boolean muerto = false;
			for (User usuarix : usuarios) {

				for (Mort muertox : muertos) {

					if (usuarix.getUserName().equals(muertox.getUser().getUserName())) {
						muerto = true;
					}

				}

				if (!muerto) {
					usuariosvivos.add(usuarix);

				}
				muerto = false;

			}
// DAO de roljugadorpartidas, para saber que roles estan vivos y no aprovechando arraylist anterior de usuariosvivos
			RolJugadorPartidaDAO roljugador = new RolJugadorPartidaImplHibernate();
			List<Roljugadorpartida> roljugadorpartida = roljugador.findAll();
			for (Roljugadorpartida rjpartida : roljugadorpartida) {

				if (rjpartida.getPartida().getId() == id) {

					// Si coincide la partida cuento los roles de los que han muerto
					for (User usuarix : usuariosvivos) {

						if (rjpartida.getUser().getUserName().equals(usuarix.getUserName())) {

							if(rjpartida.getRol().getId()==1) {
								
								llops++;
							}
							
							else if (rjpartida.getRol().getId()==2) {
								vilatans++;
							}
							else {
								videntes++;
							}
						}

					}

				}

			}
			System.out.println("Lobos vivos: "+llops+", vilatanos vivos: "+vilatans+", videntes vivos: "+videntes);

		}

		else {

			System.out.println("No existe la partida");
		}

	}

}