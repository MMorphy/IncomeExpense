package hr.petkovic.incomeexpense.DTO;

import java.util.Date;

import hr.petkovic.incomeexpense.entity.TransactionType;

public class PlannedVsAchievedDTO {

	// TODO add currency
	private Date fromDate;

	private Date toDate;

	private Double plannedAmount;

	private Double achievedAmount;

	private TransactionType type;

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

	public Double getPlannedAmount() {
		return plannedAmount;
	}

	public void setPlannedAmount(Double plannedAmount) {
		this.plannedAmount = plannedAmount;
	}

	public Double getAchievedAmount() {
		return achievedAmount;
	}

	public void setAchievedAmount(Double achievedAmount) {
		this.achievedAmount = achievedAmount;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public PlannedVsAchievedDTO(Date fromDate, Date toDate, Double plannedAmount, Double achievedAmount,
			TransactionType type) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.plannedAmount = plannedAmount;
		this.achievedAmount = achievedAmount;
		this.type = type;
	}

	public PlannedVsAchievedDTO(Date fromDate, Date toDate, Double plannedAmount, TransactionType type) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.plannedAmount = plannedAmount;
		this.achievedAmount = new Double(0D);
		this.type = type;
	}

	public PlannedVsAchievedDTO() {
		this.type = new TransactionType();
	}

	public PlannedVsAchievedDTO(PlannedAggDTO agg, Double achievedAmount) {
		this.achievedAmount = achievedAmount;
		this.fromDate = agg.getFromDate();
		this.toDate = agg.getToDate();
		this.type = agg.getType();
		this.plannedAmount = agg.getSum();
	}
}
