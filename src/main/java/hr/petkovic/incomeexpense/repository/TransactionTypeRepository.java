package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

}
