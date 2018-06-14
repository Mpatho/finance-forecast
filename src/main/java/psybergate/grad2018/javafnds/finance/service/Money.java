package psybergate.grad2018.javafnds.finance.service;

import java.math.BigDecimal;

@SuppressWarnings("rawtypes")
public final class Money implements Comparable {

	/**
	 * The number of rands, decimal point indicate cents
	 */

	private final BigDecimal rands;

	public Money(double rands) {
		if (!isValid(rands)) throw new IllegalArgumentException();
		this.rands = new BigDecimal(rands);
	}

	private static boolean isValid(double rands) {
		return (rands >= 0);
	}

	public Money add(Money money) {
		BigDecimal rands = this.rands.add(money.rands);
		double doubleValue = ((int) rands.doubleValue() * 100) / 100.0;
		return new Money(doubleValue);
	}

	public Money percentOf(double percent) {
		double doubleValue = ((int) rands.doubleValue() * percent) / 100.0;
		return new Money(doubleValue);
	}

	@Override
	public int compareTo(Object o) {
		if (o == null) { throw new IllegalArgumentException(); }
		if (!o.getClass().equals(this.getClass())) throw new IllegalArgumentException();
		Money money = (Money) o;
		return this.rands.compareTo(money.rands);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!obj.getClass().equals(this.getClass())) return false;
		Money money = (Money) obj;
		return money.rands.equals(this.rands);
	}

	public double doubleValue() {
		return rands.doubleValue();
	}

}
