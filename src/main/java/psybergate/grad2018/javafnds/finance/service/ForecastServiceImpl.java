package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateful;

@Stateful
public abstract class ForecastServiceImpl implements ForecastService {

	private BigDecimal initialAmount;

	private BigDecimal rate;

	private Integer months;

	@Override
	public abstract List<ForecastItem> getForecastItems();

	@Override
	public BigDecimal getInitialAmount() {
		return initialAmount;
	}

	@Override
	public void setInitialAmount(BigDecimal initialAmount) {
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

	@Override
	public Integer getMonths() {
		return months;
	}

	@Override
	public void setMonths(Integer months) {
		this.months = months;
	}

}
