package hr.petkovic.incomeexpense.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trans_types")
public class TransactionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String subtypeOne;

	public TransactionType(Long id, String name, String subtypeOne) {
		super();
		this.id = id;
		this.name = name;
		this.subtypeOne = subtypeOne;
	}

	public TransactionType() {
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

	public String getSubtypeOne() {
		return subtypeOne;
	}

	public void setSubtypeOne(String subtypeOne) {
		this.subtypeOne = subtypeOne;
	}


}
