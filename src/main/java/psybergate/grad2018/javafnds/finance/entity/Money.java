package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.persistence.Column;
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

	/**
	 * The number of rands, decimal point indicate cents
	 */
	@Column(columnDefinition = "DECIMAL(10,2)")
	private BigDecimal rands;

	protected Money() {}

	public Money(double rands) {
		if (!isValid(rands)) throw new IllegalArgumentException();
		this.rands = new BigDecimal(rands);
	}

	public Money(BigDecimal rands) {
		if (!isValid(rands.doubleValue())) throw new IllegalArgumentException();
		this.rands = rands;
	}

	private static boolean isValid(double rands) {
		return (rands >= 0);
	}

	public Money add(Money money) {
		BigDecimal moneyValue = money.rands.add(this.rands);
		return new Money(moneyValue);
	}

	public Money subtract(Money money) {
		BigDecimal moneyValue = this.rands.subtract(money.rands);
		return new Money(moneyValue);
	}

	public Money multiply(BigDecimal value) {
		BigDecimal moneyValue = rands.multiply(value);
		return new Money(moneyValue);
	}

	public Money percentOf(double percent) {
		BigDecimal value = rands.multiply(new BigDecimal(percent)).divide(new BigDecimal(100));
		return new Money(value);
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
		return money.stringValue().equals(this.stringValue());
	}

	public double doubleValue() {
		return rands.doubleValue();
	}

	public String stringValue() {
		return FORMAT.format(rands);
	}

	@Override
	public String toString() {
		return stringValue();
	}
}
