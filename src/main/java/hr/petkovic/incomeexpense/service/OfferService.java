package hr.petkovic.incomeexpense.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.petkovic.incomeexpense.entity.Offer;
import hr.petkovic.incomeexpense.repository.OfferRepository;

@Service
public class OfferService {

	@Autowired
	private OfferRepository offerRepo;

	public Offer findOfferById(Long id) {
		try {
			return offerRepo.findById(id).get();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Offer> findAllOffers() {
		return offerRepo.findAll();
	}

	public List<Offer> findAllOffersForUser(String username) {
		return offerRepo.findByUsername(username);
	}

	public List<Offer> findAllOffersForBuyerId(Long id) {
		return offerRepo.findByBuyer_id(id);
	}

	public Offer saveOffer(Offer o) {
		return offerRepo.save(o);
	}

	public Offer updateOffer(Long id, Offer offer) {
		Optional<Offer> optOffer = offerRepo.findById(id);
		if (optOffer.isPresent()) {
			Offer o = optOffer.get();
			o.setOfferNo(offer.getOfferNo());
			o.setAccepted(offer.getAccepted());
			o.setDeclined(offer.getDeclined());
			o.setDescription(offer.getDescription());

			o.setBrutoPrice(offer.getBrutoPrice());
			o.setCommisionFeeDealer(offer.getCommisionFeeDealer());
			o.setCommisionFeeSeller(offer.getCommisionFeeSeller());

			o.setDiscount(offer.getDiscount());
			o.setPoints(offer.getPoints());
			o.setDuration(offer.getDuration());
			o.setGMFee(offer.getGMFee());

			o.setMaterialsExpense(offer.getMaterialsExpense());
			o.setTotalManufactoringFee(offer.getTotalManufactoringFee());
			// Change buyers if the buyer ids don't match
			if (!o.getBuyer().getId().equals(offer.getBuyer().getId())) {
				offer.getBuyer().addOffer(o);
			}
			return saveOffer(o);
		} else {
			return saveOffer(offer);
		}
	}

	public void deleteOfferById(Long id) {
		offerRepo.deleteById(id);
	}

	public List<Offer> findByAcceptedOrDeclined(Boolean acceptedOrDeclined) {
		if (acceptedOrDeclined.booleanValue()) {
			return offerRepo.findByAccepted(true);
		} else
			return offerRepo.findByDeclined(true);
	}

	public List<Offer> findAllPendingOffers() {
		return offerRepo.findByAcceptedAndDeclined(false, false);
	}

	public List<Offer> findByAcceptedOrDeclinedForUsername(Boolean acceptedOrDeclined, String username) {
		if (acceptedOrDeclined.booleanValue()) {
			return offerRepo.findByAcceptedAndUsername(true, username);
		} else
			return offerRepo.findByDeclinedAndUsername(true, username);
	}

	public List<Offer> findAllPendingOffersForUser(String username) {
		return offerRepo.findByAcceptedAndDeclinedAndUsername(false, false, username);
	}
}
