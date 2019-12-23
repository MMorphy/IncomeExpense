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

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "financial_transactions")
public class FinancialTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private Date createDate;

	@Column(nullable = false)
	private Float amount;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@Column(nullable = true)
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "transaction_type_id")
	private TransactionType type;
}
