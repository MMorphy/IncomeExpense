package hr.petkovic.incomeexpense.entity;

import java.util.Date;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "plans")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date fromDate;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date toDate;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "transaction_type_id")
	private TransactionType type;

	@Column(nullable = false)
	private Float amount;
}
