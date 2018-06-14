package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;

import psybergate.grad2018.javafnds.finance.entity.Money;

public abstract class ForecastItem {

	private Money initialAmount;

	private BigDecimal rate;

	public ForecastItem(Money initialAmount, BigDecimal rate) {
		super();
		this.initialAmount = initialAmount;
		this.rate = rate;
	}

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

	public abstract Money getInterest();

	public abstract Money getEndAmount();

	

}
