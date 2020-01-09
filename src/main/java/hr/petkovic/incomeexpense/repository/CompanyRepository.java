package hr.petkovic.incomeexpense.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	Optional<Company> findByName(String name);

	List<Company> findByNameNot(String name);

	Optional<Company> findByTransactions_Id(Long id);

	Optional<Company> findByBuyers_Contracts_Transactions_Id(Long id);

	Optional<Company> findByBuyers_Id(Long id);
}
