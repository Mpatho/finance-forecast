package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class MonthlyForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	private Money monthlyAmount;
	
	public MonthlyForecastItem() {
		super();
	}

	public MonthlyForecastItem(Money initialAmount, Double rate, Money monthlyAmount) {
		super(initialAmount, rate);
		this.monthlyAmount = monthlyAmount;
	}
	

	public Money getMonthlyAmount() {
		return monthlyAmount;
	}

	@Override
	public Money getInterest() {
		Double monthlyRate = getRate().doubleValue() / 12;
		return (getInitialAmount().add(getMonthlyAmount())).percentOf(monthlyRate);
	}

	@Override
	public Money getEndAmount() {
		return getInitialAmount().add(getInterest()).add(getMonthlyAmount());
	}

}
