package hr.petkovic.incomeexpense.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "from_user_id")
	private User fromUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "to_user_id")
	private User toUser;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private Date createdAt;

	@Temporal(TemporalType.DATE)
	@Column(name = "due_date", nullable = false)
	private Date dueDate;

	@Column(nullable = false)
	private String description;

	public Task() {
		this.fromUser = new User();
		this.toUser = new User();
	}

	public Task(Long id, User fromUser, User toUser, Date createdAt, Date dueDate, String description) {
		super();
		this.id = id;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.createdAt = createdAt;
		this.dueDate = dueDate;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFromUser() {
		return fromUser;
	}

	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}

	public User getToUser() {
		return toUser;
	}

	public void setToUser(User toUser) {
		this.toUser = toUser;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
