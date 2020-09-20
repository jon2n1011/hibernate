package ProyectoHibernate.ProyectoHibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "xat_message")
public class xatMessage {

	public Date getDataRegistre() {
		return dataRegistre;
	}

	public void setDataRegistre(Date dataRegistre) {
		this.dataRegistre = dataRegistre;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	int id;
	// Many to one
	@ManyToOne
	@JoinColumn(name = "sender")
	private User sender;
	// Many to one
	@ManyToOne
	@JoinColumn(name = "partida")
	private Partida partida;

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	// Lob por que puede ser un mensaje muy largo
	@Lob
	@Column(name = "content")
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	@Column (name = "date")
	private Date dataRegistre;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public xatMessage(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	@Override
	public String toString() {
		return "xatMessage [id=" + id + ", sender=" + sender + ", partida=" + partida + ", content=" + content + "]";
	}

	public xatMessage() {
	}

}
