package hr.petkovic.incomeexpense.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String location;

	@Column(nullable = false)
	private Float currentCash;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "company_id")
	private Collection<FinancialTransaction> transactions;

	public Company(Long id, String name, String location, Float currentCash,
			Collection<FinancialTransaction> transactions) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.currentCash = currentCash;
		this.transactions = transactions;
	}

	public Company() {
		this.transactions = new ArrayList<FinancialTransaction>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getCurrentCash() {
		return currentCash;
	}

	public void setCurrentCash(Float currentCash) {
		this.currentCash = currentCash;
	}

	public Collection<FinancialTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Collection<FinancialTransaction> transactions) {
		this.transactions = transactions;
	}

	public boolean addTransaction(FinancialTransaction trans) {
		this.transactions.add(trans);
		if (trans.getType().getName().equals("Income")) {
			this.currentCash += trans.getAmount();
		} else if (trans.getType().getName().equals("Expenses")) {
			this.currentCash -= trans.getAmount();
		}
		return true;
	}

	public boolean removeTransaction(FinancialTransaction trans) {
		this.transactions.remove(trans);
		if (trans.getType().getName().equals("Income")) {
			this.currentCash -= trans.getAmount();
		} else if (trans.getType().getName().equals("Expenses")) {
			this.currentCash += trans.getAmount();
		}
		return true;
	}

}
