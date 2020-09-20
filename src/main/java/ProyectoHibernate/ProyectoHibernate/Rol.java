package ProyectoHibernate.ProyectoHibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Rol")
public class Rol {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "rol_id", updatable = false, nullable = false)
	int id;

	//No hace falta, ya que solo heredamos de la tabla a la que sirve
	//@OneToMany(mappedBy="rol",cascade=CascadeType.ALL)
	//Set<Roljugadorpartida> roles= new HashSet<Roljugadorpartida>();
	
	@Column (name = "freq")
	private String freq;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column (name = "pathimg")
	private String pathimg;
	
	@Column (name = "descripcio", length=20)
	private String descripcio;
	

	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getPathimg() {
		return pathimg;
	}
	public void setPathimg(String pathimg) {
		this.pathimg = pathimg;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	public Rol(String freq, String pathimg, String descripcio) {
		super();
		this.freq = freq;
		this.pathimg = pathimg;
		this.descripcio = descripcio;
	}
	public Rol() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Rol [id=" + id + ", freq=" + freq + ", pathimg=" + pathimg + ", descripcio=" + descripcio + "]";
	}

	
	

	
}
