package hr.petkovic.incomeexpense.DTO;

import hr.petkovic.incomeexpense.entity.Company;
import hr.petkovic.incomeexpense.entity.Contract;
import hr.petkovic.incomeexpense.entity.FinancialTransaction;

public class FinancialTransactionDTO {

	private FinancialTransaction trans;

	private Company company;

	private Contract contract;

	public FinancialTransaction getTrans() {
		return trans;
	}

	public void setTrans(FinancialTransaction trans) {
		this.trans = trans;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public FinancialTransactionDTO(FinancialTransaction trans, Company company, Contract contract) {
		super();
		this.trans = trans;
		this.company = company;
		this.contract = contract;
	}

	public FinancialTransactionDTO() {
		this.trans = new FinancialTransaction();
		this.company = new Company();
		this.contract = new Contract();
	}
}
