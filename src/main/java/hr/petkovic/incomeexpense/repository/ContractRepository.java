package hr.petkovic.incomeexpense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.petkovic.incomeexpense.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

	public Optional<Contract> findByTransactions_Id(Long id);

	public List<Contract> findByUsername(String username);

	@Query("SELECT c FROM Contract c where c.currentAmount < c.agreedAmount")
	public List<Contract> findUnpaid();

	@Query("SELECT c FROM Contract c where c.currentAmount < c.agreedAmount AND c.username = ?1")
	public List<Contract> findUnpaidForUser(String username);
}
