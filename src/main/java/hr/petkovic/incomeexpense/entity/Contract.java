package hr.petkovic.incomeexpense.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "contracts")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable =false)
	private String number;

	@Column(nullable = false)
	private Float agreedAmount;

	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "currency_id")
	private Currency agreedCurrency;

	private Float currentAmount;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "contract_id")
	private Collection<FinancialTransaction> transactions;
}
