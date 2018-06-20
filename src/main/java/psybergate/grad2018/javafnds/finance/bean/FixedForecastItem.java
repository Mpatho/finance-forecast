package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class FixedForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	public FixedForecastItem(Money initialAmount, Double rate) {
		super(initialAmount, rate);
	}

	public FixedForecastItem() {
	}

	@Override
	public Money getEndAmount() {
		Money endAmount = getInitialAmount().add(getDeposit());
		endAmount = endAmount.subtract(getWithdrawal());
		endAmount = endAmount.add(getInterest());

		return endAmount;
	}

}
