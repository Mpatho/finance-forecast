package psybergate.grad2018.javafnds.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import psybergate.grad2018.javafnds.finance.entity.Money;

public abstract class ForecastItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private Money initialAmount;

	private BigDecimal rate;

	public ForecastItem(Money initialAmount, BigDecimal rate) {
		this.initialAmount = initialAmount;
		this.rate = rate;
	}

	public ForecastItem() {}

	public Money getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Money initialAmount) {
		this.initialAmount = initialAmount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract Money getInterest();

	public abstract Money getEndAmount();

}
