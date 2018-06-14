package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

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

	private BigDecimal initialAmount;

	private Integer months;

	private BigDecimal rate;

	protected Investment() {}

	public Investment(String name, BigDecimal initialAmount, Integer months, BigDecimal rate) {
		this.name = name;
		this.initialAmount = initialAmount;
		this.months = months;
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getInitailAmount() {
		return initialAmount;
	}

	public void setInitailAmount(BigDecimal initailAmount) {
		this.initialAmount = initailAmount;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Long getId() {
		return id;
	}

}
