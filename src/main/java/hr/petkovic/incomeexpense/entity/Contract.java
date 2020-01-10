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
	private String code;

	@Column(nullable = false)
	private Float agreedAmount;
//
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "currency_id")
//	private Currency agreedCurrency;

	private Float currentAmount = new Float(0F);

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "contract_id")
	private Collection<FinancialTransaction> transactions;

	public Contract(Long id, String code, Float agreedAmount, Float currentAmount,
			Collection<FinancialTransaction> transactions) {
		super();
		this.id = id;
		this.code = code;
		this.agreedAmount = agreedAmount;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Float getAgreedAmount() {
		return agreedAmount;
	}

	public void setAgreedAmount(Float agreedAmount) {
		this.agreedAmount = agreedAmount;
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
			if (this.transactions.remove(transaction)) {
				if (transaction.getType().getName().equals("Income")) {
					this.currentAmount -= transaction.getAmount();
				} else if (transaction.getType().getName().equals("Expenses")) {
					this.currentAmount += transaction.getAmount();
				}
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
