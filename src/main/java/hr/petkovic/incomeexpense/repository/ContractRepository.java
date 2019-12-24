package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
