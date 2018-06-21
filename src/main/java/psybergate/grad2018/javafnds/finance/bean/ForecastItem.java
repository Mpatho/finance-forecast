package psybergate.grad2018.javafnds.finance.bean;

import java.io.Serializable;

import psybergate.grad2018.javafnds.finance.entity.Money;

public abstract class ForecastItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Money initialAmount;

	private Double rate;

	private Money deposit;

	private Money withdrawal;

	public ForecastItem() {
		this.deposit = new Money(0.0);
		this.withdrawal = new Money(0.0);
	}

	public ForecastItem(Money initialAmount, Double rate, Money deposit, Money withdrawal) {
		this.initialAmount = initialAmount;
		this.rate = rate;
		this.deposit = deposit;
		this.withdrawal = withdrawal;
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


	public Money getInterest() {
		Double monthlyRate = getRate().doubleValue() / 12;
		Money sum = getInitialAmount().add(getDeposit());
		sum = sum.subtract(getWithdrawal());
		return sum.percentOf(monthlyRate);
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

	public abstract Money getEndAmount();

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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForecastItem other = (ForecastItem) obj;
		if (initialAmount == null) {
			if (other.initialAmount != null)
				return false;
		}
		else if (!initialAmount.equals(other.initialAmount))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		}
		else if (!rate.equals(other.rate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ForecastItem [initialAmount=" + initialAmount + ", rate=" + rate + ", deposit=" + deposit + ", withdrawal="
				+ withdrawal + "]";
	}

	
}
