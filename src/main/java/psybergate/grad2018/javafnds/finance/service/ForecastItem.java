package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;

public abstract class ForecastItem {

	private BigDecimal beginMonthAmount;

	private Double rate;

	public ForecastItem(BigDecimal beginMonthAmount, Double rate) {
		this.beginMonthAmount = beginMonthAmount;
		this.rate = rate;
	}

	public BigDecimal getBeginMonthAmount() {
		return beginMonthAmount;
	}

	public void setBeginMonthAmount(BigDecimal beginMonthAmount) {
		this.beginMonthAmount = beginMonthAmount;
	}

	public abstract BigDecimal getEndMonthBalance();

	public abstract BigDecimal getInterest();

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

}
