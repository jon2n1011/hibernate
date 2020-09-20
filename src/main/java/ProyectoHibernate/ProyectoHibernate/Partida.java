package ProyectoHibernate.ProyectoHibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="Partida")
public class Partida {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "partida_id",updatable = false, nullable = false)
	int id;
	// Many to many
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="partida_user",joinColumns= {@JoinColumn(name="partida_id")},inverseJoinColumns= {@JoinColumn(name="user_name")})
	private Set<User> users=new HashSet();
	
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	//
	@Column(name = "torn",nullable = false)
	int torn;

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

	@Override
	public String toString() {
		return "Partida [id=" + id + ", users=" + users + ", torn=" + torn + "]";
	}



	
	
	
	
}
