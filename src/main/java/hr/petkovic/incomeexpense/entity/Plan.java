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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "plans")
public class Plan {

	// TODO add currency
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date toDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transaction_type_id")
	private TransactionType type;

	@Column(nullable = false)
	private Float amount;

	public Plan(Long id, Date fromDate, Date toDate, TransactionType type, Float amount) {
		super();
		this.id = id;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.type = type;
		this.amount = amount;
	}

	public Plan() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

}
