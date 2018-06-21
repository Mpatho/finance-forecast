package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class BondForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	private Money repayment;

	protected BondForecastItem() {
		super();
	}

	public BondForecastItem(Money initialAmount, Double rate, Money deposit, Money withdrawal, Money repayment) {
		super(initialAmount, rate, deposit, withdrawal);
		this.repayment = repayment;
	}

	@Override
	public Money getInterest() {
		Double monthlyRate = getRate().doubleValue() / 12;
		Money sum = getInitialAmount();
		return sum.percentOf(monthlyRate);
	}

	@Override
	public Money getEndAmount() {
		Money sum = getInitialAmount().add(getInterest());
		Money repayment = getRepayment();
		
		Money netRepayment = netRepayment(repayment, getDeposit(), getWithdrawal());
		if (netRepayment.percentOf(110.0).compareTo(sum) < 0) {
			return sum.subtract(netRepayment);
		}
		setRepayment(sum);
		return sum.subtract(sum);
	}

	private Money netRepayment(Money repayment, Money deposit, Money withdrawal) {
		return repayment.add(deposit).subtract(withdrawal);
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
