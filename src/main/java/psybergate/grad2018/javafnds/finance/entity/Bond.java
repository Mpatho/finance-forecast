package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Bond {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	private Money price;

	@OneToOne(cascade = CascadeType.ALL)
	private Money deposit;

	private BigDecimal rate;

	private Integer months;
	
	private String name;

	protected Bond() {}

	public Bond(Money price, Money deposit, BigDecimal rate, Integer months, String name) {
		super();
		this.price = price;
		this.deposit = deposit;
		this.rate = rate;
		this.months = months;
		this.name = name;
	}

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public Money getDeposit() {
		return deposit;
	}

	public void setDeposit(Money deposit) {
		this.deposit = deposit;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getMonths() {
		return months;
	}

	public void setMonths(Integer months) {
		this.months = months;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

}
