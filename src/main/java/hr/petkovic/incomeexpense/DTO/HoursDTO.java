package hr.petkovic.incomeexpense.DTO;

public class HoursDTO {

	private Integer month;

	private Integer year;

	private Float expectedHours;

	private Float actualHours;

	private Float productivity;

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Float getExpectedHours() {
		return expectedHours;
	}

	public void setExpectedHours(Float expectedHours) {
		this.expectedHours = expectedHours;
	}

	public Float getActualHours() {
		return actualHours;
	}

	public void setActualHours(Float actualHours) {
		this.actualHours = actualHours;
	}

	public Float getProductivity() {
		return productivity;
	}

	public void setProductivity(Float productivity) {
		this.productivity = productivity;
	}

}
