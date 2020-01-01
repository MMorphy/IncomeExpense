package hr.petkovic.incomeexpense.service;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.TransactionType;
import hr.petkovic.incomeexpense.repository.TransactionTypeRepository;

@Service
public class TransactionTypeService {

	@Autowired
	private TransactionTypeRepository typeRepo;

	public TransactionTypeService(TransactionTypeRepository typeRepo) {
		this.typeRepo = typeRepo;
	}

	public TransactionType findTransactionTypeById(Long id) {
		try {
			return typeRepo.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	public List<TransactionType> findAllTransactionTypes() {
		return typeRepo.findAll();
	}

	public List<TransactionType> findAllIncomeTransactionTypes() {
		return typeRepo.findAllByName("Income");
	}

	public List<TransactionType> findAllExpensesTransactionTypes() {
		return typeRepo.findAllByName("Expenses");
	}

	public List<TransactionType> findAllTransactionTypesByNameAndSubtypeOne(String name, String subtypeOne) {
		return typeRepo.findAllByNameAndSubtypeOne(name, subtypeOne);
	}

	public List<TransactionType> findAllTransactionTypesByNameAndSubtypes(String name, String subtypeOne,
			String subtypeTwo) {
		return typeRepo.findAllByNameAndSubtypeOneAndSubtypeTwo(name, subtypeOne, subtypeTwo);
	}

	public TransactionType updateTransactionType(Long id, TransactionType type) {
		Optional<TransactionType> optType = typeRepo.findById(id);
		if (optType.isPresent()) {
			TransactionType t = optType.get();
			t.setName(type.getName());
			t.setSubtypeOne(type.getSubtypeOne());
			t.setSubtypeTwo(type.getSubtypeTwo());
			return typeRepo.save(t);
		} else {
			return typeRepo.save(type);
		}
	}

	public void deleteTransactionTypeById(Long id) {
		typeRepo.deleteById(id);
	}
}
