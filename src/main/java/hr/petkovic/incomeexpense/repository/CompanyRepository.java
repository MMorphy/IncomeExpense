package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
