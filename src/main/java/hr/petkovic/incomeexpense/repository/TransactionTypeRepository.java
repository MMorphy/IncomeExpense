package hr.petkovic.incomeexpense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

	List<TransactionType> findAllByName(String name);

	List<TransactionType> findAllByNameAndSubtypeOne(String name, String subtypeOne);

//	List<TransactionType> findAllByNameAndSubtypeOneAndSubtypeTwo(String name, String subtypeOne, String subtypeTwo);
}
