package psybergate.grad2018.javafnds.finance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Investment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String name;

	private Double initailAmount;

	private Integer months;

	private Double rate;

	protected Investment() {}

	public Investment(Double initailAmount, Integer months, Double rate) {
		super();
		this.initailAmount = initailAmount;
		this.months = months;
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getInitailAmount() {
		return initailAmount;
	}

	public void setInitailAmount(Double initailAmount) {
		this.initailAmount = initailAmount;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getId() {
		return id;
	}

}
