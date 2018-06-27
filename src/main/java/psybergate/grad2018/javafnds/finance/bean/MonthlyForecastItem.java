package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class MonthlyForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	public MonthlyForecastItem(Money currentAmount, Double rate, Integer months, int month) {
		super(currentAmount, rate, months);
	}

	public Money getMonthlyAmount() {
		return getFixedRepayment();
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
}
