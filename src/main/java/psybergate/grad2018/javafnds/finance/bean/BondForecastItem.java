package psybergate.grad2018.javafnds.finance.bean;

import psybergate.grad2018.javafnds.finance.entity.Money;

public class BondForecastItem extends ForecastItem {

	private static final long serialVersionUID = 1L;

	public BondForecastItem(Money initialAmount, Double rate, int months) {
		super(initialAmount, rate, months);
	}

	@Override
	public Money getInterest() {
		Double monthlyRate = getRate().doubleValue() / 12;
		Money sum = getInitialAmount().add(getWithdrawal()).subtract(getDeposit());
		return sum.percentOf(monthlyRate);
	}

	@Override
	public Money getEndAmount() {
		Money sum = getInitialAmount().add(getInterest()).add(getWithdrawal()).subtract(getDeposit());
		Money repayment = getRepayment();
		if (repayment.percentOf(110.0).compareTo(sum) < 0) return sum.subtract(repayment);
		return sum.subtract(sum);
	}

	public Money getRepayment() {
		Money amount = getInitialAmount().add(getWithdrawal()).subtract(getDeposit());
		Double rate = getRate() / 1200;
		if (getFixedRepayment() != null && getRemainingMonths() > 1) {
			double log1 = Math.log(1 - amount.multiply(rate).doubleValue() / getFixedRepayment().doubleValue());
			double log2 = Math.log(1 + rate);
			int months = (int) Math.ceil(-1 * log1 / log2);
			setRemainingMonths(months);
			return getFixedRepayment();
		}
		Double one = 1.00;
		Double fraction = Math.pow((one / (one + rate)), getRemainingMonths());
		Double factor = rate / (one - (fraction));
		return amount.multiply(factor);
	}

}
