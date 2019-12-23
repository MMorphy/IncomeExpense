package hr.petkovic.incomeexpense.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trans_subtypes_2")
public class TransactionSubTypeTwo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
}
