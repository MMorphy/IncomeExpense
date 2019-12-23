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
@Table(name = "trans_subtypes_1")
public class TransactionSubtypeOne {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "transaction_subtype_one_id")
	@Column(nullable = true)
	private Collection<TransactionSubTypeTwo> subtypesTwo;
}
