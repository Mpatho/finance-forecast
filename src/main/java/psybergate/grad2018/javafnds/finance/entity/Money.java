package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public final class Money implements Comparable<Money> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The number of rands, decimal point indicate cents
	 */
	@Column(precision=8,scale=2)
	private BigDecimal rands;

	protected Money() {}

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
	public int compareTo(Money money) {
		if (money == null) throw new IllegalArgumentException();
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
