package psybergate.grad2018.javafnds.finance.bean;

import java.math.BigDecimal;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class BondForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	private Money repayment;

	protected BondForecastItem() {
		super();
	}

	public BondForecastItem(Money initialAmount, BigDecimal rate, Money repayment) {
		super(initialAmount, rate);
		this.repayment = repayment;
	}

	@Override
	public Money getEndAmount() {
		Money sum = getInitialAmount().add(getInterest());
		Money repayment = getRepayment();
		if (repayment.doubleValue() < sum.doubleValue())
			return sum.subtract(repayment);
		return sum;
	}

	public Money getRepayment() {
		return repayment;
	}

	public void setRepayment(Money repayment) {
		this.repayment = repayment;
	}

}
