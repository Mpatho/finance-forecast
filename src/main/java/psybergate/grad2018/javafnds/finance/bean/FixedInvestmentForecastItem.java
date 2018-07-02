package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class FixedInvestmentForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	public FixedInvestmentForecastItem(Money currentAmount, Double rate, Integer months) {
		super(currentAmount, rate, months);
	}

	@Override
	public Money getEndAmount() {
		Money endAmount = getInitialAmount().add(getDeposit());
		endAmount = endAmount.subtract(getWithdrawal());
		endAmount = endAmount.add(getInterest());
		return endAmount;
	}

	@Override
	public Money getInterest() {
		Double monthlyRate = getRate().doubleValue() / 12;
		Money sum = getInitialAmount().add(getDeposit());
		sum = sum.subtract(getWithdrawal());
		return sum.percentOf(monthlyRate);
	}

}
