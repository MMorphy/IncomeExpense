package hr.petkovic.incomeexpense.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

	public Optional<Contract> findByTransactions_Id(Long id);
}
