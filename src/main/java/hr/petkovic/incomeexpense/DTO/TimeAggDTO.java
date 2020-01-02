package hr.petkovic.incomeexpense.DTO;

public class TimeAggDTO {

	private Integer year;
	private Integer month;
	private Double sum;
	private Double difference;

	public TimeAggDTO(Integer year, Integer month, Double sum) {
		super();
		this.year = year;
		this.month = month;
		this.sum = sum;
		this.difference = 0D;
	}

	public TimeAggDTO() {
	}

	public TimeAggDTO(Integer year, Double sum) {
		super();
		this.year = year;
		this.sum = sum;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getDifference() {
		return difference;
	}

	public void setDifference(Double difference) {
		this.difference = difference;
	}

}
