package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

public class FixedInvestment extends Investment {

	protected FixedInvestment() {}

	public FixedInvestment(String name, Money initialAmount, Integer months, BigDecimal rate) {
		super(name, initialAmount, months, rate);
	}

}
