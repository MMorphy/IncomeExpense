package hr.petkovic.incomeexpense.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	@Column(nullable = false)
	private String number;

	@Column(nullable = false)
	private Float agreedAmount;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@JoinColumn(name = "currency_id")
	private Currency agreedCurrency;

	private Float currentAmount = new Float(0F);

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "contract_id")
	private Collection<FinancialTransaction> transactions;

	public Contract(Long id, String number, Float agreedAmount, Currency agreedCurrency, Float currentAmount,
			Collection<FinancialTransaction> transactions) {
		super();
		this.id = id;
		this.number = number;
		this.agreedAmount = agreedAmount;
		this.agreedCurrency = agreedCurrency;
		this.currentAmount = currentAmount;
		this.transactions = transactions;
	}

	public Contract() {
		this.transactions = new ArrayList<FinancialTransaction>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Float getAgreedAmount() {
		return agreedAmount;
	}

	public void setAgreedAmount(Float agreedAmount) {
		this.agreedAmount = agreedAmount;
	}

	public Currency getAgreedCurrency() {
		return agreedCurrency;
	}

	public void setAgreedCurrency(Currency agreedCurrency) {
		this.agreedCurrency = agreedCurrency;
	}

	public Float getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Float currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Collection<FinancialTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Collection<FinancialTransaction> transactions) {
		this.transactions = transactions;
	}

	public boolean addTransaction(FinancialTransaction transaction) {
		try {
			this.transactions.add(transaction);
			if (transaction.getType().getName().equals("Income")) {
				this.currentAmount += transaction.getAmount();
			} else if (transaction.getType().getName().equals("Expenses")) {
				this.currentAmount -= transaction.getAmount();
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public boolean removeTransaction(FinancialTransaction transaction) {
		try {
			this.transactions.remove(transaction);
			if (transaction.getType().getName().equals("Income")) {
				this.currentAmount -= transaction.getAmount();
			} else if (transaction.getType().getName().equals("Expenses")) {
				this.currentAmount += transaction.getAmount();
			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
