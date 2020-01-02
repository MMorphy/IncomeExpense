package hr.petkovic.incomeexpense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.petkovic.incomeexpense.DTO.TimeAggDTO;
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

	@Query(value = "SELECT sum(amount) "
			+ "FROM financial_transactions "
			+ "JOIN trans_types ON financial_transactions.transaction_type_id = trans_types.id "
			+ "WHERE createDate >= ?1 "
			+ "AND createDate <= ?2 "
			+ "AND name = ?3 "
			+ "AND subtypeOne = ?4 "
			+ "GROUP BY name, subtypeOne", 
		  nativeQuery = true)
	Double findAllTransactionsSumByTypeLvl0AndTypeLvl1AndInPeriod(String planStart, String planEnd, String typename, String subtypeOne);

	@Query(value = "SELECT sum(amount) "
			+ "FROM financial_transactions "
			+ "JOIN trans_types ON financial_transactions.transaction_type_id = trans_types.id "
			+ "WHERE createDate >= ?1 "
			+ "AND createDate <= ?2 "
			+ "AND name = ?3 "
			+ "AND subtypeOne = ?4 "
			+ "AND subtypeTwo = ?5 "
			+ "GROUP BY name, subtypeOne, subtypeTwo", 
		  nativeQuery = true)
	Double findAllTransactionsSumByTypeLvl0AndTypeLvl1AndTypeLvl2AndInPeriod(String planStart, String planEnd, String typename, String subtypeOne, String subtypeTwo);

	@Query("SELECT "
			+ "new hr.petkovic.incomeexpense.DTO.TimeAggDTO(YEAR(ft.createDate), MONTH(ft.createDate), SUM(amount)) "
		+ "FROM "
			+ "FinancialTransaction ft "
		+ "WHERE "
			+ " ft.type.name='Income' "
		+ "GROUP BY YEAR(ft.createDate), MONTH(ft.createDate)")
	List<TimeAggDTO> findAllIncomeTransactionSumsByYearAndMonth();

	@Query("SELECT "
			+ "new hr.petkovic.incomeexpense.DTO.TimeAggDTO(YEAR(ft.createDate), MONTH(ft.createDate), SUM(amount)) "
		+ "FROM "
			+ "FinancialTransaction ft "
		+ "WHERE "
			+ " ft.type.name='Expenses' "
		+ "GROUP BY YEAR(ft.createDate), MONTH(ft.createDate)")
	List<TimeAggDTO> findAllExpensesTransactionSumsByYearAndMonth();
	
	
	
	
	@Query("SELECT "
			+ "new hr.petkovic.incomeexpense.DTO.TimeAggDTO(YEAR(ft.createDate), SUM(amount)) "
		+ "FROM "
			+ "FinancialTransaction ft "
		+ "WHERE "
			+ " ft.type.name='Income' "
		+ "GROUP BY YEAR(ft.createDate)")
	List<TimeAggDTO> findAllIncomeTransactionSumsByYear();

	@Query("SELECT "
			+ "new hr.petkovic.incomeexpense.DTO.TimeAggDTO(YEAR(ft.createDate), SUM(amount)) "
		+ "FROM "
			+ "FinancialTransaction ft "
		+ "WHERE "
			+ " ft.type.name='Expenses' "
		+ "GROUP BY YEAR(ft.createDate)")
	List<TimeAggDTO> findAllExpensesTransactionSumsByYear();
}
