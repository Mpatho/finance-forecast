package psybergate.grad2018.javafnds.finance.bean;

import java.io.Serializable;

import psybergate.grad2018.javafnds.finance.entity.Event;
import psybergate.grad2018.javafnds.finance.entity.Money;

public abstract class ForecastItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Money initialAmount;

	private Double rate;

	private int months;

	private Money deposit;

	private Money withdrawal;

	private Money fixedRepayment;

	private boolean changeRepayment = false;

	public ForecastItem(Money initialAmount, Double rate, int months) {
		this.initialAmount = initialAmount;
		this.rate = rate;
		this.months = months;
		this.deposit = new Money(0.0);
		this.withdrawal = new Money(0.0);
	}

	public Money getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Money initialAmount) {
		this.initialAmount = initialAmount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Money getDeposit() {
		return deposit;
	}

	public void setDeposit(Money deposit) {
		this.deposit = deposit;
	}

	public Money getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(Money withdrawal) {
		this.withdrawal = withdrawal;
	}

	public void setFixedRepayment(Money repayment) {
		this.fixedRepayment = repayment;
	}

	public boolean isChangeRepayment() {
		return changeRepayment;
	}

	public void addEvent(Event event) {
		processEvent(event);
	}

	public Money getFixedRepayment() {
		return fixedRepayment;
	}

	public int getRemainingMonths() {
		return months;
	}

	public abstract Money getInterest();

	public abstract Money getEndAmount();

	@Override
	public String toString() {
		return "ForecastItem [initialAmount=" + initialAmount + ", rate=" + rate + ", months=" + months + ", deposit="
				+ deposit + ", withdrawal=" + withdrawal + ", fixedRepayment=" + fixedRepayment + ", changeRepayment="
				+ changeRepayment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((initialAmount == null) ? 0 : initialAmount.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ForecastItem other = (ForecastItem) obj;
		if (initialAmount == null) {
			if (other.initialAmount != null) return false;
		}
		else if (!initialAmount.equals(other.initialAmount)) return false;
		if (rate == null) {
			if (other.rate != null) return false;
		}
		else if (!rate.equals(other.rate)) return false;
		return true;
	}

	private void processEvent(Event event) {
		switch (event.getType()) {
			case Event.DEPOSIT:
				Money deposit = new Money(event.getValue().doubleValue());
				setDeposit(deposit);
				break;
			case Event.WITHDRAW:
				Money withdrawal = new Money(event.getValue().doubleValue());
				setWithdrawal(withdrawal);
				break;
			case Event.RATE_CHANGE:
				Double rate = event.getValue().doubleValue();
				setRate(rate);
				break;
			case Event.AMOUNT_CHANGE:
				Money repayment = new Money(event.getValue().doubleValue());
				changeRepayment = true;
				setFixedRepayment(repayment);
		}
	}
}
