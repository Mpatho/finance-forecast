package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class FixedForecastItem extends ForecastItem {

	

	public FixedForecastItem(Money initialAmount, BigDecimal rate) {
		super(initialAmount, rate);
	}

	@Override
	public Money getInterest() {
		BigDecimal monthlyRate = getRate().divide(new BigDecimal(12));
		return getInitialAmount().percentOf(monthlyRate.doubleValue());
	}

	@Override
	public Money getEndAmount() {
		return getInitialAmount().add(getInterest());
	}

}
