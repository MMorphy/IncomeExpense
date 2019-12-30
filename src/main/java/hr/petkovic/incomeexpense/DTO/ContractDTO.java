package hr.petkovic.incomeexpense.DTO;

import hr.petkovic.incomeexpense.entity.Buyer;
import hr.petkovic.incomeexpense.entity.Contract;

public class ContractDTO {

	private Buyer buyer;
	private Contract contract;

	public ContractDTO() {
		this.buyer = new Buyer();
		this.contract = new Contract();
	}

	public ContractDTO(Buyer buyer, Contract contract) {
		super();
		this.buyer = buyer;
		this.contract = contract;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
}
