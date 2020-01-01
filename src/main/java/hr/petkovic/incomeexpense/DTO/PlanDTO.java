package hr.petkovic.incomeexpense.DTO;

import org.springframework.format.annotation.DateTimeFormat;

import hr.petkovic.incomeexpense.entity.Plan;

public class PlanDTO {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String fromDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String toDate;

	private Plan plan;

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public PlanDTO(String fromDate, String toDate, Plan plan) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.plan = plan;
	}

	public PlanDTO() {
		this.plan = new Plan();
	}
}
