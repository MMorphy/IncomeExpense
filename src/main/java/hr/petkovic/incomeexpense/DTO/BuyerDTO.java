package hr.petkovic.incomeexpense.DTO;

import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Company;

public class BuyerDTO {

	private Buyer buyer;
	private Company company;

	public BuyerDTO(Buyer buyer, Company company) {
		super();
		this.buyer = buyer;
		this.company = company;
	}

	public BuyerDTO() {
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
