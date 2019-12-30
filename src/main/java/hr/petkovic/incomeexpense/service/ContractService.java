package hr.petkovic.incomeexpense.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;
import hr.petkovic.incomeexpense.repository.ContractRepository;

@Service
public class ContractService {

	@Autowired
	private ContractRepository contractRepo;

	public ContractService(ContractRepository contractRepo) {
		this.contractRepo = contractRepo;
	}

	public Contract saveContract(Contract contr) {
		return this.contractRepo.save(contr);
	}

	public Contract findContractByTransaction(FinancialTransaction trans) {
		Optional<Contract> optCotract = this.contractRepo.findByTransactions_Id(trans.getId());
		if (optCotract.isPresent()) {
			return optCotract.get();
		} else
			return null;
	}
}
