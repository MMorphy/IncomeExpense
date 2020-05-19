package hr.petkovic.incomeexpense.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "buyers")
public class Buyer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	private String code;

	private String description;

	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Offer> offers;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinColumn(name = "buyer_id")
	private Collection<Contract> contracts;

	public Buyer(Long id, String name, String code, String description, List<Offer> offers,
			Collection<Contract> contracts) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.offers = offers;
		this.contracts = contracts;
	}

	public Buyer() {
		this.contracts = new ArrayList<Contract>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(Collection<Contract> contracts) {
		this.contracts = contracts;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	// TODO stavi bidirectional i implementiraj add/remove s obe strane
	// (this.contracts.add(contract); contract.setBuyer(this));
	public boolean addContract(Contract contract) {
		try {
			this.contracts.add(contract);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean removeContract(Contract contract) {
		try {
			this.contracts.remove(contract);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void addOffer(Offer offer) {
		offers.add(offer);
		offer.setBuyer(this);
	}

	public void removeOffer(Offer offer) {
		offers.remove(offer);
		if (offer.getBuyer().equals(this)) {
			offer.setBuyer(null);
		}
	}

	public boolean findBuyerWithContract(Contract cont) {
		for (Contract c : contracts) {
			if (c.equals(cont) || c.getId().equals(cont.getId())) {
				return true;
			}
		}
		return false;
	}
}
