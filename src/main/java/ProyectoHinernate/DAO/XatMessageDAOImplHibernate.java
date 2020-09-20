package ProyectoHinernate.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import ProyectoHibernate.ProyectoHibernate.Mort;
import ProyectoHibernate.ProyectoHibernate.Partida;
import ProyectoHibernate.ProyectoHibernate.Roljugadorpartida;
import ProyectoHibernate.ProyectoHibernate.User;
import ProyectoHibernate.ProyectoHibernate.Vot;
import ProyectoHibernate.ProyectoHibernate.xatMessage;

public class XatMessageDAOImplHibernate extends GenericDAOImplHibernate<xatMessage, Integer> implements XatMessageDAO {

	public List<xatMessage> getXat(int id) {

		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(id);
		if (partidote != null) {

			XatMessageDAO xat = new XatMessageDAOImplHibernate();

			List<xatMessage> mensajes = xat.findAll();
			List<xatMessage> mensajefiltrado = new ArrayList();

			for (xatMessage mensaje : mensajes) {

				if (mensaje.getPartida().getId() == id) {
					mensajefiltrado.add(mensaje);

				}

			}

			return mensajefiltrado;
		}

		else {

			return null;

		}

	}

	public void escriuMissatge(String contenido, int idp, String username) {
		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(idp);
		UserDAO usuarios = new UserDAOImplHibernate();
		User usuari = usuarios.findById(username);
		if (partidote != null && usuari != null) {
			// Numero impar, significa dia

			RolJugadorPartidaDAO rol = new RolJugadorPartidaImplHibernate();

			List<Roljugadorpartida> rolj = rol.findAll(idp, username);

			if (rolj.size() != 0) {
				if (partidote.getTorn() % 2 != 0) {

					Session session = sessionFactory.getCurrentSession();

					try {
						session.beginTransaction();
						xatMessage xatmensaje = new xatMessage();
						xatmensaje.setContent(contenido);
						xatmensaje.setPartida(partidote);
						xatmensaje.setSender(usuari);
						session.saveOrUpdate(xatmensaje);

						session.getTransaction().commit();

						System.out.println("Mensaje enviado");
					} catch (HibernateException e) {

						if (session != null && session.getTransaction() != null) {
							System.out.println("\n.......Transaction Is Being Rolled Back.......");
							session.getTransaction().rollback();
						}

					}

				}

				else {

					System.out.println("La partida se encuentra en modo nocturno");
				}
			}

			else {

				System.out.println("El jugador no se encuentra dentro de la partida");
			}
		} else {

			System.out.println("No existe la partida o usuario incorrecto");
		}
	}

	public List<xatMessage> getXatLlops(int idPartida, String username) {

		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(idPartida);
		UserDAO usuarios = new UserDAOImplHibernate();
		User usuari = usuarios.findById(username);
		if (partidote != null && usuari != null) {
			// Numero par, significa noche

			RolJugadorPartidaDAO rol = new RolJugadorPartidaImplHibernate();

			List<Roljugadorpartida> rolj = rol.findAll(idPartida, username);

			if (partidote.getTorn() % 2 == 0) {
				// El id de llops es 1, cambiar en caso contrario!
				if (rolj.size() != 0) {
					if (rolj.get(0).getRol().getId() == 1) {
						XatMessageDAO xat = new XatMessageDAOImplHibernate();

						// Sacamos todos los usuarios lobos, con tal de filtrar despues
						ArrayList<User> usuarioslobos = new ArrayList();
						List<Roljugadorpartida> listalobos = rol.findAll();
						for (Roljugadorpartida rolx : listalobos) {

							// Cambiar 1 por el numero de rol de LOBOS!
							if (rolx.getRol().getId() == 1) {
								usuarioslobos.add(rolx.getUser());
							}
						}

						List<xatMessage> mensajes = xat.findAll();
						List<xatMessage> mensajefiltrado = new ArrayList();

						for (xatMessage mensaje : mensajes) {

							if (mensaje.getPartida().getId() == idPartida) {

								// Aqui recorreremos la lista de usuarioslobos, con tal de ver si es un lobo
								for (User userlobo : usuarioslobos) {

									if (mensaje.getSender().getUserName().equals(userlobo.getUserName())) {

										mensajefiltrado.add(mensaje);

									}

								}

							}

						}

						return mensajefiltrado;

					}

				}
			}

		}

		else {

			return null;

		}
		return null;

	}

	public void escriuMissatgeLlop(int idPartida, String username,String contenido) {

		PartidaDAO partida = new PartidaDAOImplHibernate();
		Partida partidote = partida.findById(idPartida);
		UserDAO usuarios = new UserDAOImplHibernate();
		User usuari = usuarios.findById(username);
		if (partidote != null && usuari != null) {
			// Numero par, significa noche

			RolJugadorPartidaDAO rol = new RolJugadorPartidaImplHibernate();

			List<Roljugadorpartida> rolj = rol.findAll(idPartida, username);

			if (partidote.getTorn() % 2 == 0) {
				// El id de llops es 1, cambiar en caso contrario!
				if (rolj.size() != 0) {
					if (rolj.get(0).getRol().getId() == 1) {
						XatMessageDAO xat = new XatMessageDAOImplHibernate();
						Session session = sessionFactory.getCurrentSession();

						try {
							session.beginTransaction();
							xatMessage xatmensaje = new xatMessage();
							xatmensaje.setContent(contenido);
							xatmensaje.setPartida(partidote);
							xatmensaje.setSender(usuari);
							session.saveOrUpdate(xatmensaje);

							session.getTransaction().commit();

							System.out.println("Mensaje enviado");
						} catch (HibernateException e) {

							if (session != null && session.getTransaction() != null) {
								System.out.println("\n.......Transaction Is Being Rolled Back.......");
								session.getTransaction().rollback();
							}

						}
					}

					else {

						System.out.println("El usuario no es un lobo");

					}
				} else {

					System.out.println("El usuario no se encuntra en la partida");
				}
			}

		}

		else {

			System.out.println("Usuario o partida invalidas");
		}

	}

}
