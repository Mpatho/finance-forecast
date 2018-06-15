package psybergate.grad2018.javafnds.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import psybergate.grad2018.javafnds.finance.entity.Money;

public abstract class ForecastItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Money initialAmount;

	private BigDecimal rate;

	public ForecastItem(Money initialAmount, BigDecimal rate) {
		this.initialAmount = initialAmount;
		this.rate = rate;
	}

	public ForecastItem() {}

	public Money getInitialAmount() {
		return initialAmount;
	}

	public void setInitialAmount(Money initialAmount) {
		this.initialAmount = initialAmount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Money getMonthlyAmount() {
		return new Money(0);
	}
	public abstract Money getInterest();

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

	
	
}
