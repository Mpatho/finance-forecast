package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateful;

import psybergate.grad2018.javafnds.finance.entity.Money;

@Stateful
public abstract class AbstractForecastService implements ForecastService {

	private Money initialAmount;

	private BigDecimal rate;

	private Integer months;

	@Override
	public abstract List<ForecastItem> getForecastItems();

	@Override
	public abstract List<ForecastItem> getForecastItems(String name);

	@Override
	public Money getInitialAmount() {
		return initialAmount;
	}

	@Override
	public void setInitialAmount(Money initialAmount) {
		this.initialAmount = initialAmount;
	}

	@Override
	public BigDecimal getRate() {
		return rate;
	}

	@Override
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
