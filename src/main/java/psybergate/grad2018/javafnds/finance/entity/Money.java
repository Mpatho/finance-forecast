package psybergate.grad2018.javafnds.finance.entity;

import java.text.DecimalFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class Money implements Comparable<Money> {

	private static final DecimalFormat FORMAT = new DecimalFormat("#,##0.00");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long cents;

	protected Money() {}

	public Money(Double rands) {
		if (!isValid(rands)) throw new IllegalArgumentException();
		this.cents = Math.round(rands * 100);
	}

	public Money(String rands) {
		this(Double.valueOf(rands));
	}

	public Money(Long cents) {
		if (!isValid(cents.doubleValue())) throw new IllegalArgumentException();
		this.cents = cents; 
	}

	private static boolean isValid(double rands) {
		return (rands >= 0);
	}

	public Money add(Money money) {
		Long cents = this.cents + money.cents;
		return new Money(cents);
//		BigDecimal moneyValue = money.rands.add(this.rands);
//		return new Money(moneyValue);
	}

	public Money subtract(Money money) {
		Long cents = this.cents - money.cents;
		return new Money(cents);
	}

	public Money multiply(Double value) {
		long cents = Math.round(this.cents * value);
		return new Money(cents);
	}

	public Money percentOf(Double percent) {
		long cents = Math.round(this.cents * percent / 100.00);
		return new Money(cents);
	}

	@Override
	public int compareTo(Money money) {
		if (money == null) throw new IllegalArgumentException();
		return this.cents.compareTo(money.cents);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!obj.getClass().equals(this.getClass())) return false;
		Money money = (Money) obj;
		return money.stringValue().equals(this.stringValue());
	}

	public double doubleValue() {
		return cents / 100.0;
	}

	public String stringValue() {
		return FORMAT.format(cents / 100.0);
	}

	@Override
	public String toString() {
		return stringValue();
	}
}
