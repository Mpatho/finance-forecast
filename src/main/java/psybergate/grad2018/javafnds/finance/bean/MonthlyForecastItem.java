package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class MonthlyForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	private Money monthlyAmount;

	public MonthlyForecastItem() {
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
		Money sum = getInitialAmount().add(getDeposit());
		sum = sum.subtract(getWithdrawal());
		sum = sum.add(getMonthlyAmount());
		return sum.percentOf(monthlyRate);
	}
	
	@Override
	public Money getEndAmount() {
		Money endAmount = getInitialAmount().add(getDeposit());
		endAmount = endAmount.subtract(getWithdrawal());
		endAmount = endAmount.add(getInterest());
		endAmount = endAmount.add(getMonthlyAmount());
		return endAmount;
	}

	@Override
	public void setDeposit(Money deposit) {
		super.setDeposit(deposit);
	}
}
