package ProyectoHibernate.ProyectoHibernate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class Script {

	static User usuario;
	static Session session;
	static SessionFactory sessionFactory;
	static ServiceRegistry serviceRegistry;

	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {

			// exception handling omitted for brevityaa

			serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

			sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		}
		return sessionFactory;
	}

	public static void main(String[] args) {
		/// abrimos sesion. Eso se hace siempre
		try {
			session = getSessionFactory().openSession();
			session.beginTransaction();
			
			
			// Crear los 7 roles principales
			Rol rol1= new Rol("4", "/imgllop", "llops");
			Rol rol2= new Rol("0", "/imgvilatans", "vilatans");
			Rol rol3= new Rol("-1", "/vidents", "vidents");
			Rol rol4= new Rol("10", "/angel", "Àngel");
			Rol rol5= new Rol("-1", "/cazador", "Cazador");
			Rol rol6= new Rol("-1", "/Alcalde", "Alcalde");
			Rol rol7= new Rol("-1", "/Enamorados", "Enamorados");
			session.saveOrUpdate(rol1);
			session.saveOrUpdate(rol2);
			session.saveOrUpdate(rol3);
			session.saveOrUpdate(rol4);
			session.saveOrUpdate(rol5);
			session.saveOrUpdate(rol6);
			session.save(rol7);
	
			
			session.getTransaction().commit();
			session.clear();
			
			
			// Creacio usuaris
			session.beginTransaction();

			Random r = new Random();
			Date data = new Date();
			ArrayList<User> usuarios = new ArrayList();
			for (int i = 0; i < 1000; i++) {
				User user = new User();

				user.setUserName("Jugador " + (i));
				user.setPassword("jugador" + (i));
				user.setAlias("alias" + (i));
				user.setDataRegistre(data);
				user.setPercentatgeVictories(0);
				user.setPathAvatar("/img/imagen" + (i));
				usuarios.add(user);
				session.saveOrUpdate(user);
			}
			session.getTransaction().commit();
			session.clear();

			session.beginTransaction();
			for (int i = 0; i < 200; i++) {
				Message mensaje = new Message();
				mensaje.setType(tipus.video);
				mensaje.setDate(data);
				mensaje.setContent("Mensaje " + i);
				int random = r.nextInt(1000);
				mensaje.setReceiver(usuarios.get(random));
				random = r.nextInt(1000);
				mensaje.setSender(usuarios.get(random));

				session.saveOrUpdate(mensaje);

			}

			session.getTransaction().commit();

			// Crear partidas
			ArrayList<Integer> randoms = new ArrayList();
			int i = 0;
			Partida partida = new Partida();
			partida.setTorn(0);
			while (i < 14) {

				int random = r.nextInt(1000);

				if (!randoms.contains(random)) {
					randoms.add(random);
					i++;
				}
			}

			Set<User> users = new HashSet<User>();
			for (int x = 0; x < 14; x++) {

				users.add(usuarios.get(randoms.get(x)));

			}
			session.beginTransaction();
			partida.setUsers(users);
			session.saveOrUpdate(partida);
			session.getTransaction().commit();

		} catch (Exception sqlException) {
			sqlException.printStackTrace();
			if (null != session.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}
}