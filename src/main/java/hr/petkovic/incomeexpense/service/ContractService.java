package hr.petkovic.incomeexpense.service;

import java.util.List;
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
	@Autowired
	private BuyerService buyerService;

	public ContractService(ContractRepository contractRepo, BuyerService buyerService) {
		this.contractRepo = contractRepo;
		this.buyerService = buyerService;
	}

	public Contract findContractById(Long id) {
		try {
			return contractRepo.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<Contract> findAllContractsForBuyerId(Long id) {
		return (List<Contract>) buyerService.findBuyerById(id).getContracts();
	}

	public List<Contract> findAllContracts() {
		return contractRepo.findAll();
	}

	public Contract saveContract(Contract contr) {
		return contractRepo.save(contr);
	}

	public Contract updateContract(Long id, Contract contract) {
		Optional<Contract> optContract = contractRepo.findById(id);
		if (optContract.isPresent()) {
			Contract con = optContract.get();
			con.setAgreedAmount(contract.getAgreedAmount());
			con.setAgreedCurrency(contract.getAgreedCurrency());
			con.setCode(contract.getCode());
			con.setCurrentAmount(contract.getCurrentAmount());
			con.setTransactions(contract.getTransactions());
			return contractRepo.save(con);
		} else {
			return contractRepo.save(contract);
		}
	}

	public Contract findContractByTransaction(FinancialTransaction trans) {
		Optional<Contract> optCotract = contractRepo.findByTransactions_Id(trans.getId());
		if (optCotract.isPresent()) {
			return optCotract.get();
		} else
			return null;
	}

	public void deleteContractById(Long id) {
		contractRepo.deleteById(id);
	}
}
