package psybergate.grad2018.javafnds.finance.entity;

import java.math.BigDecimal;

public class FixedInvestment extends Investment {

	public FixedInvestment() {}

	public FixedInvestment(String name, BigDecimal initialAmount, Integer months, BigDecimal rate) {
		super(name, initialAmount, months, rate);
	}

}
