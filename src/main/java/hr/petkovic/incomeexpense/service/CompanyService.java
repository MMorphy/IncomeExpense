package hr.petkovic.incomeexpense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Company;
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

	public List<Company> findAllCompanies(){
		return this.companyRepo.findAll();
	}
}
