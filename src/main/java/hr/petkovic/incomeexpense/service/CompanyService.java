package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepo;

	public CompanyService(CompanyRepository companyRepo) {
		this.companyRepo = companyRepo;
	}

	public Company findCompanyById(Long id) {
		try {
			return companyRepo.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Company> findAllCompanies() {
		return companyRepo.findAll();
	}

	public Company saveCompany(Company comp) {
		return companyRepo.save(comp);
	}

	public void deleteCompanyById(Long id) {
		companyRepo.deleteById(id);
	}

	public Company updateCompany(Long id, Company comp) {
		Optional<Company> optComp = companyRepo.findById(id);
		if (optComp.isPresent()) {
			Company c = optComp.get();
			c.setCurrentCash(comp.getCurrentCash());
			c.setLocation(comp.getLocation());
			c.setName(comp.getName());
			c.setTransactions(comp.getTransactions());
			c.setBuyers(comp.getBuyers());
			return companyRepo.save(c);
		} else {
			return companyRepo.save(comp);
		}
	}

	public Company findCompanyByTransaction(FinancialTransaction trans) {
		Optional<Company> optCompany = companyRepo.findByTransactions_Id(trans.getId());
		if (optCompany.isPresent()) {
			return optCompany.get();
		} else
			return null;
	}

	public Company findCompanyByTransactionInBuyer(FinancialTransaction trans) {
		Optional<Company> optCompany = companyRepo.findByBuyers_Contracts_Transactions_Id(trans.getId());
		if (optCompany.isPresent()) {
			return optCompany.get();
		} else
			return null;
	}

	public Company findCompanyByBuyerId(Long buyerId) {
		Optional<Company> optBuyer = companyRepo.findByBuyers_Id(buyerId);
		if (optBuyer.isPresent()) {
			return optBuyer.get();
		} else
			return null;
	}
}
