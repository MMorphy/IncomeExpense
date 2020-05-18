package hr.petkovic.incomeexpense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.petkovic.incomeexpense.entity.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {

	public List<Offer> findByBuyer_id(Long id);

	public List<Offer> findByUsername(String username);

	public List<Offer> findByAccepted(Boolean accepted);

	public List<Offer> findByAcceptedAndUsername(Boolean accepted, String username);

	public List<Offer> findByDeclined(Boolean declined);

	public List<Offer> findByDeclinedAndUsername(Boolean declined, String username);

	public List<Offer> findByAcceptedAndDeclined(Boolean accepted, Boolean declined);

	public List<Offer> findByAcceptedAndDeclinedAndUsername(Boolean accepted, Boolean declined, String username);

}
