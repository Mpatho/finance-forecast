package psybergate.grad2018.javafnds.finance.bean;

import java.math.BigDecimal;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class FixedForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	public FixedForecastItem(Money initialAmount, BigDecimal rate) {
		super(initialAmount, rate);
	}

	public FixedForecastItem() {
	}

	@Override
	public Money getInterest() {
		Double monthlyRate = getRate().doubleValue() / 12;
		return getInitialAmount().percentOf(monthlyRate);
	}

	@Override
	public Money getEndAmount() {
		return getInitialAmount().add(getInterest());
	}

}
