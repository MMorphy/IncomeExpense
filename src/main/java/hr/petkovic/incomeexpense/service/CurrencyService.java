package hr.petkovic.incomeexpense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Currency;
import hr.petkovic.incomeexpense.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	CurrencyRepository currencyRepo;

	public CurrencyService(CurrencyRepository currencyRepo) {
		this.currencyRepo = currencyRepo;
	}

	public List<Currency> findAllCurrencies() {
		return this.currencyRepo.findAll();
	}
}
