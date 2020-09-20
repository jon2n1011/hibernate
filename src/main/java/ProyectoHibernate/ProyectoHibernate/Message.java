package ProyectoHibernate.ProyectoHibernate;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "Message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = true, nullable = false)
	int id;
	// Many to one receiveer
	@ManyToOne
	@JoinColumn(name="sender", nullable = false)
	private User sender;

	// Many to one sender
	@ManyToOne
	@JoinColumn(name="receiver", nullable = false)
	private User receiver;
	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	tipus type;

	@Column(name = "content")
	String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_registre")
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	




	public tipus getType() {
		return type;
	}

	public void setType(tipus type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Message() {
		
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", sender=" + sender.getUserName() + ", receiver=" + receiver.getUserName()+ ", type=" + type + ", content="
				+ content + ", date=" + date + "]";
	}



	// @OneToMany(mappedBy = "user")

	// private Token token;

	/*
	 * //oneToMany. ahi decimos de que queremos el set (que tabla) y dentro de esa
	 * tabla cual es la columna que contiene //la llave foranea, es decir, el curso.
	 * Cuidado con las mayusculas, no seas imbecil como yo
	 * 
	 * @OneToMany(mappedBy="curso") Set<Modulo> modulos = new HashSet<Modulo>();
	 * 
	 * //OneToOne, simplemente pones el objeto al que referencias
	 * 
	 * @JoinColumn(name="tutor", nullable=false)
	 * 
	 * @OneToOne(cascade = CascadeType.ALL)
	 */

}