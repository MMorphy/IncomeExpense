package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.TransactionSubtypeOne;

public interface TransactionSubtypeOneRepository extends JpaRepository<TransactionSubtypeOne, Long> {

}
