package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class BondForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	private Money repayment;

	protected BondForecastItem() {
		super();
	}

	public BondForecastItem(Money initialAmount, Double rate) {
		super(initialAmount, rate);
	}

	@Override
	public Money getInterest() {
		Double monthlyRate = getRate().doubleValue() / 12;
		Money sum = getInitialAmount();
		return sum.percentOf(monthlyRate);
	}

	@Override
	public Money getEndAmount() {
		Money sum = getInitialAmount().add(getInterest()).add(getWithdrawal()).subtract(getDeposit());
		Money repayment = getRepayment();
		if (repayment.percentOf(110.0).compareTo(sum) < 0) return sum.subtract(repayment);
		setRepayment(sum);
		return sum.subtract(sum);
	}

	public Money getRepayment() {
		return repayment;
	}

	public void setRepayment(Money repayment) {
		this.repayment = repayment;
	}

	@Override
	public String toString() {
		return "BondForecastItem [repayment=" + repayment + ", getInitialAmount()=" + getInitialAmount() + ", getRate()="
				+ getRate() + ", getInterest()=" + getInterest() + "]";
	}

}
