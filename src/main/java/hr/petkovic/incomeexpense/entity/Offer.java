package hr.petkovic.incomeexpense.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "offers")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.DATE)
	private Date createDate;

	private String offerNo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buyer_id")
	private Buyer buyer;

	private Integer points;

	private Float brutoPrice;

	private Float discount;

	private Float materialsExpense;

	private Float commisionFeeSeller;

	private Float commisionFeeDealer;

	private Float duration;

	private Float totalManufactoringFee;

	private Float GMFee;

	private Boolean accepted = false;

	private Boolean declined = false;

	private String description;

	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOfferNo() {
		return offerNo;
	}

	public void setOfferNo(String offerNo) {
		this.offerNo = offerNo;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Float getBrutoPrice() {
		return brutoPrice;
	}

	public void setBrutoPrice(Float brutoPrice) {
		this.brutoPrice = brutoPrice;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getMaterialsExpense() {
		return materialsExpense;
	}

	public void setMaterialsExpense(Float materialsExpense) {
		this.materialsExpense = materialsExpense;
	}

	public Float getCommisionFeeSeller() {
		return commisionFeeSeller;
	}

	public void setCommisionFeeSeller(Float commisionFeeSeller) {
		this.commisionFeeSeller = commisionFeeSeller;
	}

	public Float getCommisionFeeDealer() {
		return commisionFeeDealer;
	}

	public void setCommisionFeeDealer(Float commisionFeeDealer) {
		this.commisionFeeDealer = commisionFeeDealer;
	}

	public Float getDuration() {
		return duration;
	}

	public void setDuration(Float duration) {
		this.duration = duration;
	}

	public Float getTotalManufactoringFee() {
		return totalManufactoringFee;
	}

	public void setTotalManufactoringFee(Float totalManufactoringFee) {
		this.totalManufactoringFee = totalManufactoringFee;
	}

	public Float getGMFee() {
		return GMFee;
	}

	public void setGMFee(Float gMFee) {
		GMFee = gMFee;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public Boolean getDeclined() {
		return declined;
	}

	public void setDeclined(Boolean declined) {
		this.declined = declined;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Offer))
			return false;
		return id != null && id.equals(((Offer) o).getId());
	}

	@Override
	public int hashCode() {
		return 100;
	}

}
