package hr.petkovic.incomeexpense.DTO;

import java.util.Date;

import hr.petkovic.incomeexpense.entity.TransactionType;

public class PlannedAggDTO {

	Date fromDate;

	Date toDate;

	double sum;

	TransactionType type;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public PlannedAggDTO(Date fromDate, Date toDate, double sum, TransactionType type) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.sum = sum;
		this.type = type;
	}

	public PlannedAggDTO(Date fromDate, Date toDate, double sum) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.sum = sum;
		this.type = new TransactionType();
	}

	public PlannedAggDTO() {
		this.type = new TransactionType();
	}
}
