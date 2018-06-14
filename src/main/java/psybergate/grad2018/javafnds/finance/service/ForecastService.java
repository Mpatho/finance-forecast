package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateful;

@Stateful
public abstract class ForecastService {

	private BigDecimal initialAmount;

	private BigDecimal rate;

	private Integer months;

	public abstract List<ForecastItem> getForecastItems();

	public BigDecimal getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(BigDecimal initialAmount) {
		this.initialAmount = initialAmount;
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

}
