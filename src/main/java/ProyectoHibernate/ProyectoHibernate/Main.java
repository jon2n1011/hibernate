package ProyectoHibernate.ProyectoHibernate;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import ProyectoHinernate.DAO.GenericDAO;
import ProyectoHinernate.DAO.GenericDAOImplHibernate;
import ProyectoHinernate.DAO.MessageDAO;
import ProyectoHinernate.DAO.MessageDAOImplHibernate;
import ProyectoHinernate.DAO.MortDAO;
import ProyectoHinernate.DAO.MortDAOImplHibernate;
import ProyectoHinernate.DAO.PartidaDAO;
import ProyectoHinernate.DAO.PartidaDAOImplHibernate;
import ProyectoHinernate.DAO.RolJugadorPartidaDAO;
import ProyectoHinernate.DAO.RolJugadorPartidaImplHibernate;
import ProyectoHinernate.DAO.UserDAO;
import ProyectoHinernate.DAO.UserDAOImplHibernate;
import ProyectoHinernate.DAO.VotDAO;
import ProyectoHinernate.DAO.VotDAOImplHibernate;
import ProyectoHinernate.DAO.XatMessageDAO;
import ProyectoHinernate.DAO.XatMessageDAOImplHibernate;

public class Main {

	public static void main(String[] args) {

		// Cambiar parametros acorde a la generacion aleatoria de el script!

		// Classe USERDAO, implementa los metodos: registre,login,getmissatges.
		System.out.println("Metodo Registre USERDAO");
		System.out.println("---------------------------------------------------------------------------");
		UserDAO usuarios = new UserDAOImplHibernate();
		// Metode registre

		usuarios.registre("Jonprueba", "Jon123", "elmejor", "/img/Jonprueba");
		System.out.println("Metodo Login USERDAO");
		System.out.println("---------------------------------------------------------------------------");
		// Metode Login
		usuarios.login("Jonprueba", "Jon123");

		// Metode getmissatges
		System.out.println("Metodo GetMissatges USERDAO");
		System.out.println("---------------------------------------------------------------------------");
		System.out.println("Mensajes de jugador a elegir");
		usuarios.getMissatges("Jugador 657");

		
		
		// Classe PARTIDADAO, implementa los metodos: unirse, fitorn, inici,descubrirrol, jugadorsvius
		PartidaDAO partidas= new PartidaDAOImplHibernate();
		// Creamos una partida para unir a los jugadores:
		Partida partida= new Partida();
		partida.setTorn(0);
		partidas.saveOrUpdate(partida);
		
		
		System.out.println("Metodo Unirse PARTIDADAO");
		System.out.println("---------------------------------------------------------------------------");
		//Simularemos una partida, con el script anterior hay jugadores del Jugador 1 al Jugador 999
		partidas.unirse("Jugador 10", 2); // Si el usuario ya esta dentro de la partida, lo dira.
		partidas.unirse("Jugador 23", 2);
		partidas.unirse("Jugador 211", 2);
		partidas.unirse("Jugador 102", 2);
		partidas.unirse("Jugador 17",	2);
		partidas.unirse("Jugador 222", 2);
		partidas.unirse("Jugador 242", 2);
		partidas.unirse("Jugador 876", 2);
		partidas.unirse("Jugador 76", 2);
		partidas.unirse("Jugador 87", 2);
		partidas.unirse("Jugador 689", 2);
		partidas.unirse("Jugador 576", 2);
		
		System.out.println("Metodo Iniciar PARTIDADAO");
		System.out.println("---------------------------------------------------------------------------");
		//Iniciar una partida
		 partidas.inici(2);
		 
		 
		 // CLASSE VOTDAO,implement los metodos: vota, get historial
		 System.out.println("Metodo vota VOTDAO");
			System.out.println("---------------------------------------------------------------------------");
		 VotDAO vot= new VotDAOImplHibernate();
		 vot.vota("Jugador 576", "Jugador 689", 2);
		 
		 System.out.println();
		 
		 
		 //Fi torn
		 
		 System.out.println("Metodo Fitorn VOTDAO");
			System.out.println("---------------------------------------------------------------------------");
		 
		 partidas.fiTorn(2);
		 
		 System.out.println();
		 // Descobrirrol, el primer nombre pasado por parametro es el vidente que haya salido random en el apartado iniciarpartida, el segundo el usuario al que se le desvelara el rol.
		 System.out.println("Metodo Descobirrol PartidaDAO");
			System.out.println("---------------------------------------------------------------------------");
		 partidas.descubrirRol(2, "Jugador 576", "Jugador 689");

		 System.out.println();
		 //Jugadors vius, enseña  la lista de jugadores vivos en una partida
		 System.out.println("Metodo Jugadorsvius PartidaDAO");
			System.out.println("---------------------------------------------------------------------------");
		 partidas.jugadorsvius(2);
		 
		 System.out.println();
		 //Roles vivos, enseña los roles vivos
		 
		 System.out.println("Metodo Rolsvius PartidaDAO");
			System.out.println("---------------------------------------------------------------------------");
		 partidas.rolsvius(2);
		 

		 // CLASSE XAT MESSAGE
		 XatMessageDAO xat=new XatMessageDAOImplHibernate();
		 //Metodo escriumissatge, si se encuentra de noche avisara de ello!
		 System.out.println("Metodo escriuMissatge xatMessage");
			System.out.println("---------------------------------------------------------------------------");
		 xat.escriuMissatge("Hola", 2, "Jugador 576");
		 		 
		 System.out.println();
		 // Si no hay mensajes de xat no los mostrara

		 System.out.println("Metodo getxat xatMessage");
			System.out.println("---------------------------------------------------------------------------");
		
		List<xatMessage> xats = xat.getXat(2);
		for (xatMessage xatx : xats) {
			System.out.println(xatx.toString());
		}
		 
		 System.out.println();
		 // Si eres lobo te dejara escribir, si es de dia no dejara escribir
		 System.out.println("Metodo Esciumissatgellop xatMessage");
			System.out.println("---------------------------------------------------------------------------");
		 xat.escriuMissatgeLlop(2, "Jugador 76", "Hola llop");
		 System.out.println();
		 
		 
		 // Getxatllops, si eres lobo te dejara leer el xat, filtrando solo los mensajes de los lobos
		 System.out.println("Metodo getxatllops xatMessage");
			System.out.println("---------------------------------------------------------------------------");
		
		xats = xat.getXatLlops(2, "Jugador 76");
		for (xatMessage xatx : xats) {
			System.out.println(xatx.toString());
		}
		 System.out.println();
		 
		 // Gethisorial, de la classe VOTDAO recibira los votos en un turno determinado, filtrando si eres lobo o no.
		
		 System.out.println("Metodo gethistorial VOTDAO");
			System.out.println("---------------------------------------------------------------------------");

			
			List<Vot>votos=vot.gethistorial(2, 1, "Jugador 76");
			for (Vot votx:votos) {
				
				
				System.out.println(votx.toString());
				
			}
			
			
			System.out.println();
		// Classe MortDAOImplHibernate
		// Metodo que returna una lista de muertos, para una partida en un turno
		// determinado
		 
		 System.out.println("Metodo getmorts PartidaDAO");
			System.out.println("---------------------------------------------------------------------------");
		
		MortDAO mort = new MortDAOImplHibernate();
		List<Mort> morts = mort.getMort(1, 1);
		for (Mort muertox : morts) {
			System.out.println(muertox.toString());
		}

	}

}
