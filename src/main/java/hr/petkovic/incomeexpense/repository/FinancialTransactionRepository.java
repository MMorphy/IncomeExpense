package hr.petkovic.incomeexpense.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.petkovic.incomeexpense.entity.FinancialTransaction;

public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {

	List<FinancialTransaction> findByCreatedBy_Username(String username);

	@Query(value = "SELECT sum(amount) "
			+ "FROM financial_transactions "
			+ "JOIN trans_types ON financial_transactions.transaction_type_id = trans_types.id "
			+ "WHERE createDate >= ?1 "
			+ "AND createDate <= ?2 "
			+ "AND name = ?3 "
			+ "GROUP BY name", 
		  nativeQuery = true)
	Double findAllTransactionsSumByTypeLvl0AndInPeriod(String planStart, String planEnd, String typename);
}
