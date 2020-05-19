package hr.petkovic.incomeexpense.DTO;

public class BankDTO {

	private Integer year;
	private Integer month;
	private Double sum;

	public BankDTO(Integer year, Integer month, Double sum) {
		super();
		this.year = year;
		this.month = month;
		this.sum = sum;
	}

	public BankDTO() {
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
}
