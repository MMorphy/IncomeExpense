package hr.petkovic.incomeexpense.entity;

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
}
