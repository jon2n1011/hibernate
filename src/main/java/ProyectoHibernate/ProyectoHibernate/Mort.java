package ProyectoHibernate.ProyectoHibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Mort")
public class Mort {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",updatable = false, nullable = false)
	int id;
	// Relacion many to one a user
	@ManyToOne
	@JoinColumn(name="user")
	private User user;
	//Relacion
	@ManyToOne
	@JoinColumn(name="partida")
	private Partida partida;
	//
	@Column(name = "torn",nullable = false)
	int torn;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Partida getPartida() {
		return partida;
	}
	public void setPartida(Partida partida) {
		this.partida = partida;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getTorn() {
		return torn;
	}
	public void setTorn(int torn) {
		this.torn = torn;
	}
	public Mort(int id, int torn) {
		super();
		this.id = id;
		this.torn = torn;
	}
	public Mort() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Mort [id=" + id + ", user=" + user + ", partida=" + partida + ", torn=" + torn + "]";
	}
	
}
