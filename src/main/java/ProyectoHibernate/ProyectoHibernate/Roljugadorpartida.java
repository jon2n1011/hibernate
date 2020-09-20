package ProyectoHibernate.ProyectoHibernate;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
@Entity
@Table(name="Roljugadorpartida")
public class Roljugadorpartida {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id_rjp",updatable = false, nullable = false)
	int id;
	
	@ManyToOne
	@JoinColumn (name = "user")
	private User user;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	//relacion manytoone. Aqui declaramos la llave foranea que basicamente es un objeto del tipo referenciado
		//en nuestro caso, de clase curso. Referenciamos su llave primaria pero ponemos el objeto entero. El nombre de la JoinColumn
	    //equivale al MappedBy de la clase Curso
	@ManyToOne
	@JoinColumn (name = "rol_id")
	private Rol rol;
	
	@ManyToOne
	@JoinColumn (name = "id_partida")
	private Partida partida;

	@Type(type="numeric_boolean")
	@Column(name="viu")
	boolean activo;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Roljugadorpartida(User user, Rol rol, Partida partida, boolean activo) {
		super();
		this.user = user;
		this.rol = rol;
		this.partida = partida;
		this.activo = activo;
	}

	
public Roljugadorpartida() {
	
	
}

@Override
public String toString() {
	return "Roljugadorpartida [id=" + id + ", user=" + user + ", rol=" + rol + ", partida=" + partida + ", activo="
			+ activo + "]";
}

	
	

	
	
	
	
}
