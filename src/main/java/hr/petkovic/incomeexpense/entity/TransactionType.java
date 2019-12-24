package hr.petkovic.incomeexpense.entity;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "trans_types")
public class TransactionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_type_id")
	@Column(nullable = false)
	private Collection<TransactionSubtypeOne> subtypesOne;

	public TransactionType(Long id, String name, Collection<TransactionSubtypeOne> subtypesOne) {
		super();
		this.id = id;
		this.name = name;
		this.subtypesOne = subtypesOne;
	}

	public TransactionType() {
		this.subtypesOne = new ArrayList<TransactionSubtypeOne>();
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

	public Collection<TransactionSubtypeOne> getSubtypesOne() {
		return subtypesOne;
	}

	public void setSubtypesOne(Collection<TransactionSubtypeOne> subtypesOne) {
		this.subtypesOne = subtypesOne;
	}

}
