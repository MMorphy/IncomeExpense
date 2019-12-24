package hr.petkovic.incomeexpense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
