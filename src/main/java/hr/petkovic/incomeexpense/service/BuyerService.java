package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.repository.BuyerRepository;

@Service
public class BuyerService {

	@Autowired
	private BuyerRepository buyerRepo;

	public BuyerService(BuyerRepository buyerRepo) {
		this.buyerRepo = buyerRepo;
	}

	public Buyer findBuyerById(Long id) {
		try {
			return buyerRepo.findById(id).get();
		} catch (Exception e) {
			return null;
		}
	}

	public Buyer updateBuyer(Long id, Buyer buyer) {
		Optional<Buyer> optBuyer = buyerRepo.findById(id);
		if (optBuyer.isPresent()) {
			Buyer buy = optBuyer.get();
			buy.setCode(buyer.getCode());
			buy.setName(buyer.getName());
			buy.setDescription(buyer.getDescription());
			buy.setContracts(buyer.getContracts());
			return buyerRepo.save(buy);
		} else {
			return buyerRepo.save(buyer);
		}
	}

	public List<Buyer> findAllBuyers() {
		return buyerRepo.findAll();
	}

	public Buyer saveBuyer(Buyer buyer) {
		return buyerRepo.save(buyer);
	}

	public void deleteBuyerById(Long id) {
		buyerRepo.deleteById(id);
	}
}
