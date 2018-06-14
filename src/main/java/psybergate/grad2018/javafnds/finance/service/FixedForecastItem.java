package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;

public class FixedForecastItem extends ForecastItem {

	public FixedForecastItem(BigDecimal beginMonthAmount, Double rate) {
		super(beginMonthAmount, rate);
	}

	@Override
	public BigDecimal getInterest() {
		BigDecimal monthlyRate = new BigDecimal(getRate() / 12);
		return getBeginMonthAmount().multiply(monthlyRate);
	}

	@Override
	public BigDecimal getEndMonthBalance() {
		return getBeginMonthAmount().add(getInterest());
	}

}
